package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.MeasurementTag;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.HotspotTag;

public abstract class AAstNode<T> implements BackedAstNode<T> {
	
	private final ProgrammMarkerContext ctx;
	protected final T inner;
	
	public AAstNode(T inner, ProgrammMarkerContext ctx) {
		this.ctx = ctx;
		this.inner = inner;
	}

	@Override
	public T getEclipseAstNode() {
		return inner;
	}

	public MethodDeclaration getCurrentMethode(){
		return ctx.getCurrentMethode();
	}
	
	private List<HotspotTag> hotspots = null;
	
	protected abstract boolean corrolatesWith(Procedure procedure);
	
	@Override
	public <E extends MeasurementTag> List<E> getMeasurementTags(Class<E> tagType) {
		if(tagType == HotspotTag.class){
			if (hotspots == null) {
				hotspots = new ArrayList<>();
				for(AggregatedProcedureMetricsDto measure : ctx.getFeedBackClient().hotspots(ctx.getProject())){
					if(corrolatesWith(measure.getProcedure())){
						hotspots.add(new HotspotTag(measure));
					}
				}
				hotspots = Collections.unmodifiableList(hotspots);
				
	        }
	        return (List<E>)hotspots;
		}
		
		
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}
	
	@Override
	public void markWarning(String id, FeedbackMarkerType type, String message, Map<String, Object> additionalAttributes){
		 final FeedbackJavaFile javaFile =  ctx.getFile();
		 final int start = getStartPosition();
		 MarkerSpecification markerSpecification = new MarkerSpecification(id, new MarkerPosition(ctx.getLine(start), getStartPosition(), getEndPosition()), IMarker.SEVERITY_WARNING, type, message,additionalAttributes);
		 try {
		      javaFile.addMarker(markerSpecification);
		    }
		    catch (final CoreException e) {
		      e.printStackTrace();
		    }
	 }
	
	/**
	  * Returns the start position (in the respective Java file) of the wrapped object.
     * 
     * @return the start position (in the respective Java file) of the wrapped object.
     */
	  protected abstract int getStartPosition();

	  /**
	   * Returns the end position (in the respective Java file) of the wrapped object.
	   * 
	   * @return the end position (in the respective Java file) of the wrapped object.
	   */
	  protected abstract int getEndPosition();

}
