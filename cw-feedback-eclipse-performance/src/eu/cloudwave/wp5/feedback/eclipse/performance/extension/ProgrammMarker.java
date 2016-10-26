package eu.cloudwave.wp5.feedback.eclipse.performance.extension;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.ProgrammMarkerVisitor;

public interface ProgrammMarker {

	ProgrammMarkerVisitor createFileVisitor(ProgrammMarkerContext rootContext);

}
