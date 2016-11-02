package eu.cloudwave.wp5.feedback.eclipse.performance.extension;

import java.util.Collections;
import java.util.List;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

public interface ProgrammMarker {

	default List<String> getProvidedTags(){return Collections.emptyList();}
	default List<String> getRequiredTags(){return Collections.emptyList();}
	default List<String> getOptionalRequiredTags(){return Collections.emptyList();}

	ProgrammMarkerVisitor createFileVisitor(ProgrammMarkerContext rootContext);

}
