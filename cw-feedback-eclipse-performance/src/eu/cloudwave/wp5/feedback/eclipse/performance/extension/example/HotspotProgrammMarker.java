package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.util.Joiners;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.properties.PerformanceFeedbackProperties;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;
import eu.cloudwave.wp5.feedback.eclipse.performance.infrastructure.config.PerformanceConfigs;

public class HotspotProgrammMarker implements ProgrammMarker{
	
	  private static final String AVG_EXECUTION_TIME = "avgExecutionTime";
	  private static final String NAME = "name";
	  private static final String KIND = "kind";
	  private static final String HOTSPOT = "hotspot";
	  private static final String MESSAGE_PATTERN = "Hotspot %s: Average Execution Time of %s is %s.";
	  private static final int DECIMAL_PLACES = 3;
	  private static final String AVG_EXEC_TIME_TAG = "AvgExcecutionTime";


	  @Override
	  public List<String> getRequiredTags() {
		  	return Collections.singletonList(AVG_EXEC_TIME_TAG);
	  }
	  
	  
	@Override
	public ProgrammMarkerVisitor createFileVisitor(final ProgrammMarkerContext rootContext) {
		return new ProgrammMarkerVisitor() {

			@Override
			public ProgrammMarkerVisitor visit(MethodOccurence method) {
				 final double threshold = rootContext.getProject().getFeedbackProperties().getDouble(PerformanceFeedbackProperties.TRESHOLD__HOTSPOTS, PerformanceConfigs.DEFAULT_THRESHOLD_HOTSPOTS);
				 for (double avgExecutionTime :method.getDoubleTags(AVG_EXEC_TIME_TAG)) {
					 if(avgExecutionTime < threshold) continue;
					 //Todo: Not nice at all look what we can evacuate
					 
					 final MethodLocator loc = method.createCorrespondingMethodLocation();
					 final String valueAsText = TimeValues.toText(avgExecutionTime, DECIMAL_PLACES);
					 final String kind = method.getMethodKind();
					 final String message = String.format(MESSAGE_PATTERN, kind, loc.methodName, valueAsText);
					 final Map<String, Object> context = Maps.newHashMap();
				     context.put(KIND, kind);
				     context.put(NAME,loc.methodName);
				     context.put(AVG_EXECUTION_TIME, valueAsText);
				     final String description = rootContext.getTemplateHandler().getContent(HOTSPOT, context);
					 final Map<String, Object> additionalAttributes = Maps.newHashMap();
					 additionalAttributes.put(MarkerAttributes.DESCRIPTION, description);
					 additionalAttributes.put(MarkerAttributes.CLASS_NAME, loc.className);
					 additionalAttributes.put(MarkerAttributes.PROCEDURE_NAME, loc.methodName);
					 additionalAttributes.put(MarkerAttributes.ARGUMENTS, Joiners.onComma(loc.argumentTypes));
					 method.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.HOTSPOT,message, additionalAttributes);
			    }
				return CONTINUE;
			}
			
		};
		
	}

}
