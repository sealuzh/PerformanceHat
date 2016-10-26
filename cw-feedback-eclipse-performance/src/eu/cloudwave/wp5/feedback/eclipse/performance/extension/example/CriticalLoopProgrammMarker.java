package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.util.Numbers;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.participants.CriticalLoopBuilderParticipant.ProcedureExecutionData;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement.CollectionSource;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

public class CriticalLoopProgrammMarker implements ProgrammMarker{
	

	  private static final String PROCEDURE_EXECUTIONS = "procedureExecutions";
	  private static final String AVG_TIME_PER_ITERATION = "avgTimePerIteration";
	  private static final String AVG_INTERATIONS = "avgInterations";
	  private static final String AVG_TOTAL = "avgTotal";
	  private static final String LOOP = "loop";
	  private static final int DECIMAL_PLACES = 3;
	  private static final String MESSAGE_PATTERN = "Critical Loop: Average Total Time is %s (Average Iterations: %s).";


	  @Override
	  public ProgrammMarkerVisitor createFileVisitor(final ProgrammMarkerContext rootContext) {
		  return new ProgrammMarkerVisitor() {
				@Override
				public ProgrammMarkerVisitor visit(LoopStatement forEach) {
					  final Optional<CollectionSource> collectionSource = forEach.getSourceCollection();
					  if (collectionSource.isPresent()) {
			        	//Todo: eliminate explicit Feedback client as soon as Tagging works
				         final Procedure procedure = collectionSource.get().getProcedure();
				         final String[] arguments = procedure.getArguments().toArray(new String[procedure.getArguments().size()]);
				         final Double averageSize = rootContext.getFeedBackClient().collectionSize(rootContext.getProject(), procedure.getClassName(), procedure.getName(), arguments, collectionSource.get().getPosition());
				          
				         //todo: if we make seperate class we can easealy do nested looping after reforming
				         return new ProgrammMarkerVisitor() {
			        	//Todo: collect tags directly here instead of procedures  
				        	private List<Procedure> procedures = Lists.newArrayList();
				        	
							@Override
							public ProgrammMarkerVisitor visit(Invocation invocation) {
								procedures.add(invocation.createCorrelatingProcedure());
								return CONTINUE;
							}
							
							private Double getAverageExecutionTime(final Map<Procedure, Double> procedures) {
						        double averageLoopTime = 0;
						        for (final Entry<Procedure, Double> entry : procedures.entrySet()) {
						          averageLoopTime += entry.getValue();
						        }
						        return averageLoopTime;
						    }

							private List<ProcedureExecutionData> getProcedureExecutionData(final Map<Procedure, Double> procedureExecutionTimes) {
						        final List<ProcedureExecutionData> data = Lists.newArrayList();
						        for (final Map.Entry<Procedure, Double> entry : procedureExecutionTimes.entrySet()) {
						          data.add(ProcedureExecutionData.of(entry.getKey(), entry.getValue()));
						        }
						        return data;
						    }
							 
							@Override
							public void finish() {
								final Map<Procedure, Double> procedureExecutionTimes = Maps.newHashMap();
							     for (final Procedure procedure : procedures) {
							    	 final String[] arguments = procedure.getArguments().toArray(new String[procedure.getArguments().size()]);
							    	 //todo: eliminate and collect tags directly
							    	 final Double avgExecTimeResponse = rootContext.getFeedBackClient().avgExecTime(rootContext.getProject(), procedure.getClassName(), procedure.getName(), arguments);
							    	 final double avgExecTime = avgExecTimeResponse != null ? avgExecTimeResponse : 0;
							    	 procedureExecutionTimes.put(procedure, avgExecTime);
						         }
							     final Double avgExecTimePerIteration = getAverageExecutionTime(procedureExecutionTimes);
						          if (averageSize != null && avgExecTimePerIteration != null) {
						            final Double avgTotalExecTime = averageSize * avgExecTimePerIteration;
						            final double threshold = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__LOOPS, PerformanceConfigs.DEFAULT_THRESHOLD_LOOPS);
						            if (avgTotalExecTime >= threshold) {
						              final String avgIterationsText = new Double(Numbers.round(averageSize, DECIMAL_PLACES)).toString();
						              final String avgExecTimePerIterationText = TimeValues.toText(avgExecTimePerIteration, DECIMAL_PLACES);
						              final String avgTotalExecTimeText = TimeValues.toText(avgTotalExecTime, DECIMAL_PLACES);
						              final String msg = String.format(MESSAGE_PATTERN, avgTotalExecTimeText, avgIterationsText);
						              final Map<String, Object> context = Maps.newHashMap();
						              context.put(AVG_TOTAL, avgTotalExecTimeText);
						              context.put(AVG_INTERATIONS, avgIterationsText);
						              context.put(AVG_TIME_PER_ITERATION, avgExecTimePerIterationText);
						              context.put(PROCEDURE_EXECUTIONS, getProcedureExecutionData(procedureExecutionTimes));
						              final String desc = rootContext.getTemplateHandler().getContent(LOOP, context);
						              final Map<String, Object> additionalAttributes = Maps.newHashMap();
									  additionalAttributes.put(MarkerAttributes.DESCRIPTION, desc);
									  forEach.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.COLLECTION_SIZE,msg, additionalAttributes);
						            }
						          }
							}
			        	
					  };
			     }
				 return CONTINUE;
			 }
		  };
	}
}
