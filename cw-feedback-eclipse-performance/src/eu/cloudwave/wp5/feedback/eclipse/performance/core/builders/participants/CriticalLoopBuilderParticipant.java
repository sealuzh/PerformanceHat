/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.util.Numbers;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.AbstractFeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.CollectionSourceDetector;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.CollectionSourceDetector.CollectionSource;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.ForLoopBodyVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

/**
 * A builder participant that is responsible to show warnings related to collection sizes / loops.
 */
public class CriticalLoopBuilderParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
  private static final String AVG_TIME_PER_ITERATION = "avgTimePerIteration";
  private static final String AVG_INTERATIONS = "avgInterations";
  private static final String AVG_TOTAL = "avgTotal";
  private static final String LOOP = "loop";
  private static final int DECIMAL_PLACES = 3;
  private static final String MESSAGE_PATTERN = "Critical Loop: Average Total Time is %s (Average Iterations: %s).";

  private FeedbackHandlerEclipseClient feedbackHandlerClient;
  private TemplateHandler templateHandler;

  public CriticalLoopBuilderParticipant() {
    this.feedbackHandlerClient = PerformancePluginActivator.instance(FeedbackHandlerEclipseClient.class);
    this.templateHandler = PerformancePluginActivator.instance(TemplateHandler.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void buildFile(final FeedbackJavaProject project, final FeedbackJavaFile javaFile, final CompilationUnit astRoot) {
    astRoot.accept(new ASTVisitor() {

      private MethodDeclaration currentMethodDeclaration;

      @Override
      public boolean visit(final MethodDeclaration node) {
        currentMethodDeclaration = node;
        return true;
      }

      @Override
      public boolean visit(final EnhancedForStatement foreachStatement) {
        final Optional<CollectionSource> collectionSource = new CollectionSourceDetector().getSource(foreachStatement, currentMethodDeclaration);
        if (collectionSource.isPresent()) {
          final Procedure procedure = collectionSource.get().getProcedure();
          final String[] arguments = procedure.getArguments().toArray(new String[procedure.getArguments().size()]);
          final Double averageSize = feedbackHandlerClient.collectionSize(project, procedure.getClassName(), procedure.getName(), arguments, collectionSource.get().getPosition());
          final Map<Procedure, Double> procedureExecutionTimes = getProcedureExecutionTimes(foreachStatement);
          final Double avgExecTimePerIteration = getAverageExecutionTime(procedureExecutionTimes);
          if (averageSize != null && avgExecTimePerIteration != null) {
            final Double avgTotalExecTime = averageSize * avgExecTimePerIteration;
            final double threshold = project.getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
            if (avgTotalExecTime >= threshold) {
              final int startPosition = foreachStatement.getParameter().getStartPosition();
              final Expression expression = foreachStatement.getExpression();
              final int endPosition = expression.getStartPosition() + expression.getLength();
              final int line = astRoot.getLineNumber(startPosition);
              final String avgIterationsText = new Double(Numbers.round(averageSize, DECIMAL_PLACES)).toString();
              final String avgExecTimePerIterationText = TimeValues.toText(avgExecTimePerIteration, DECIMAL_PLACES);
              final String avgTotalExecTimeText = TimeValues.toText(avgTotalExecTime, DECIMAL_PLACES);
              final String msg = String.format(MESSAGE_PATTERN, avgTotalExecTimeText, avgIterationsText);
              final Map<String, Object> context = Maps.newHashMap();
              context.put(AVG_TOTAL, avgTotalExecTimeText);
              context.put(AVG_INTERATIONS, avgIterationsText);
              context.put(AVG_TIME_PER_ITERATION, avgExecTimePerIterationText);
              context.put(PROCEDURE_EXECUTIONS, getProcedureExecutionData(procedureExecutionTimes));
              final String desc = templateHandler.getContent(LOOP, context);
              final MarkerSpecification markerSpecification = MarkerSpecification.of(Ids.PERFORMANCE_MARKER, new MarkerPosition(line, startPosition, endPosition), IMarker.SEVERITY_WARNING,
                  PerformanceMarkerTypes.COLLECTION_SIZE, msg).and(MarkerAttributes.DESCRIPTION, desc);
              addMarker(javaFile, markerSpecification);
            }
          }
        }
        return super.visit(foreachStatement);
      }

      @Override
      public boolean visit(final ForStatement forStatement) {
        // currently only foreach-loops are supported (see method above). To also support for-loops implement this
        // method as the one above. The important properties of a for loop are:
        // - node.getExpression()
        // - node.initializers()
        // - node.updaters()
        return super.visit(forStatement);
      }

      private Map<Procedure, Double> getProcedureExecutionTimes(final EnhancedForStatement foreachStatement) {
        final List<Procedure> procedures = new ForLoopBodyVisitor().getProcedureInvocations(foreachStatement);
        final Map<Procedure, Double> procedureExecutionTimes = Maps.newHashMap();
        for (final Procedure procedure : procedures) {
          final String[] arguments = procedure.getArguments().toArray(new String[procedure.getArguments().size()]);
          final Double avgExecTimeResponse = feedbackHandlerClient.avgExecTime(project, procedure.getClassName(), procedure.getName(), arguments);
          final double avgExecTime = avgExecTimeResponse != null ? avgExecTimeResponse : 0;
          procedureExecutionTimes.put(procedure, avgExecTime);
        }
        return procedureExecutionTimes;
      }

      private List<ProcedureExecutionData> getProcedureExecutionData(final Map<Procedure, Double> procedureExecutionTimes) {
        final List<ProcedureExecutionData> data = Lists.newArrayList();
        for (final Map.Entry<Procedure, Double> entry : procedureExecutionTimes.entrySet()) {
          data.add(ProcedureExecutionData.of(entry.getKey(), entry.getValue()));
        }
        return data;
      }

      private Double getAverageExecutionTime(final Map<Procedure, Double> procedures) {
        double averageLoopTime = 0;
        for (final Entry<Procedure, Double> entry : procedures.entrySet()) {
          averageLoopTime += entry.getValue();
        }
        return averageLoopTime;
      }
    });

  }

  public static class ProcedureExecutionData {
    private static final String POINT = ".";
    private String className;
    private String name;
    private String executionTime;

    private ProcedureExecutionData(final String className, final String name, final String executionTime) {
      this.className = className;
      this.name = name;
      this.executionTime = executionTime;
    }

    public String getClassName() {
      return className;
    }

    public String getName() {
      return name;
    }

    public String getExecutionTime() {
      return executionTime;
    }

    public static ProcedureExecutionData of(final Procedure procedure, final Double executionTime) {
      final String simpleClassName = procedure.getClassName().substring(procedure.getClassName().lastIndexOf(POINT) + 1);
      return new ProcedureExecutionData(simpleClassName, procedure.getName(), TimeValues.toText(executionTime, DECIMAL_PLACES));
    }
  }
}
