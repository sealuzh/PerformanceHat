package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.Map;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.util.Joiners;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.markers.PerformanceMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarker;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

public class HotspotProgrammMarker implements ProgrammMarker{
	
	  private static final String AVG_EXECUTION_TIME = "avgExecutionTime";
	  private static final String NAME = "name";
	  private static final String KIND = "kind";
	  private static final String HOTSPOT = "hotspot";
	  private static final String MESSAGE_PATTERN = "Hotspot %s: Average Execution Time of %s is %s.";
	  private static final String METHOD = "Method";
	  private static final String CONSTRUCTOR = "Constructor";
	  private static final int DECIMAL_PLACES = 3;

	@Override
	public ProgrammMarkerVisitor createFileVisitor(final ProgrammMarkerContext rootContext) {
		return new ProgrammMarkerVisitor() {

			@Override
			public ProgrammMarkerVisitor visit(MethodOccurence methode) {
				 for (HotspotTag hotspot :methode.getMeasurementTags(HotspotTag.class)) {
					 //Todo: Not nice at all look what we can evacuate
					 final Procedure procedure = hotspot.getProcedure();
					 final String valueAsText = TimeValues.toText(hotspot.getAverageExecutionTime(), DECIMAL_PLACES);
					 final String kind = procedure.getKind().equals(ProcedureKind.CONSTRUCTOR) ? CONSTRUCTOR : METHOD;
					 final String message = String.format(MESSAGE_PATTERN, kind, procedure.getName(), valueAsText);
					 final Map<String, Object> context = Maps.newHashMap();
				     context.put(KIND, kind);
				     context.put(NAME, procedure.getName());
				     context.put(AVG_EXECUTION_TIME, valueAsText);
				     final String description = rootContext.getTemplateHandler().getContent(HOTSPOT, context);
					 final Map<String, Object> additionalAttributes = Maps.newHashMap();
					 additionalAttributes.put(MarkerAttributes.DESCRIPTION, description);
					 additionalAttributes.put(MarkerAttributes.CLASS_NAME, procedure.getClassName());
					 additionalAttributes.put(MarkerAttributes.PROCEDURE_NAME, procedure.getName());
					 additionalAttributes.put(MarkerAttributes.ARGUMENTS, Joiners.onComma(procedure.getArguments().toArray()));
					 methode.markWarning(Ids.PERFORMANCE_MARKER,PerformanceMarkerTypes.HOTSPOT,message, additionalAttributes);
			    }
				return CONTINUE;
			}
			
		};
		
	}

}
