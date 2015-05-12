/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
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

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.util.Joiners;
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
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.AbstractMethodExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.MethodDeclarationExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.ast.extensions.MethodInvocationExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;

/**
 * A builder participant that is responsible to display warnings for hotspot procedures.
 */
public class HotspotsBuilderParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  private static final String AVG_EXECUTION_TIME = "avgExecutionTime";
  private static final String NAME = "name";
  private static final String KIND = "kind";
  private static final String HOTSPOT = "hotspot";
  private static final String MESSAGE_PATTERN = "Hotspot %s: Average Execution Time of %s is %s.";
  private static final String METHOD = "Method";
  private static final String CONSTRUCTOR = "Constructor";
  private static final int DECIMAL_PLACES = 3;

  private FeedbackHandlerEclipseClient feedbackHandlerClient;
  private TemplateHandler templateHandler;

  public HotspotsBuilderParticipant() {
    this.feedbackHandlerClient = PerformancePluginActivator.instance(FeedbackHandlerEclipseClient.class);
    this.templateHandler = PerformancePluginActivator.instance(TemplateHandler.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void buildFile(final FeedbackJavaProject project, final FeedbackJavaFile javaFile, final CompilationUnit astRoot) {
    // System.out.println("HotspotsBuilderParticipant buildfile");
    astRoot.accept(new ASTVisitor() {
      private AggregatedProcedureMetricsDto[] hotspots;

      @Override
      public boolean visit(final MethodDeclaration methodDeclaration) {
        visit(new MethodDeclarationExtension(methodDeclaration));
        return true;
      }

      @Override
      public boolean visit(final MethodInvocation methodInvocation) {
        visit(new MethodInvocationExtension(methodInvocation));
        return true;
      }

      private void visit(final AbstractMethodExtension<?> methodExt) {
        for (final AggregatedProcedureMetricsDto hotspot : getHotspots()) {
          if (methodExt.correlatesWith(hotspot.getProcedure())) {
            final int startPosition = methodExt.getStartPosition();
            final int line = astRoot.getLineNumber(startPosition);
            addMarker(javaFile, createMarkerSpecification(new MarkerPosition(line, startPosition, methodExt.getEndPosition()), hotspot));
          }
        }
      }

      private AggregatedProcedureMetricsDto[] getHotspots() {
        if (hotspots == null) {
          hotspots = feedbackHandlerClient.hotspots(project);
        }
        return hotspots;
      }
    });
  }

  private MarkerSpecification createMarkerSpecification(final MarkerPosition markerPosition, final AggregatedProcedureMetricsDto hotspot) {
    final Procedure procedure = hotspot.getProcedure();
    final String valueAsText = TimeValues.toText(hotspot.getAverageExecutionTime(), DECIMAL_PLACES);
    final String kind = procedure.getKind().equals(ProcedureKind.CONSTRUCTOR) ? CONSTRUCTOR : METHOD;
    final String message = String.format(MESSAGE_PATTERN, kind, procedure.getName(), valueAsText);
    final Map<String, Object> context = Maps.newHashMap();
    context.put(KIND, kind);
    context.put(NAME, procedure.getName());
    context.put(AVG_EXECUTION_TIME, valueAsText);
    final String description = templateHandler.getContent(HOTSPOT, context);

    return MarkerSpecification.of(Ids.PERFORMANCE_MARKER, markerPosition, IMarker.SEVERITY_WARNING, PerformanceMarkerTypes.HOTSPOT, message).and(MarkerAttributes.DESCRIPTION, description)
        .and(MarkerAttributes.CLASS_NAME, procedure.getClassName()).and(MarkerAttributes.PROCEDURE_NAME, procedure.getName())
        .and(MarkerAttributes.ARGUMENTS, Joiners.onComma(procedure.getArguments().toArray()));
  }
}
