package eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.AbstractFeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostIds;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.markers.CostMarkerTypes;

/**
 * A builder participant that is responsible to display warnings for microservice client request calls.
 */
public class MicroserviceClientInvocationParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  private TemplateHandler templateHandler;

  private final static String HOVER_TEMPLATE = "clientInvocation";

  public MicroserviceClientInvocationParticipant() {
    this.templateHandler = CostPluginActivator.instance(TemplateHandler.class);
  }

  @Override
  protected void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {
    // System.out.println("ClientRequestParticipant buildFile");
    astRoot.accept(new ASTVisitor() {
      @Override
      public boolean visit(MethodInvocation node) {
        String name = node.getName().getIdentifier();

        IAnnotationBinding[] annotations = node.resolveMethodBinding().getMethodDeclaration().getAnnotations();
        String prefix = "@" + Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION;

        // we do not go through the whole list, the filter is only going to be applied until we reach any valid
        // element
        Optional<IAnnotationBinding> clientRequestAnnotationCheck = Arrays.asList(annotations).stream().filter(m -> m.toString().startsWith(prefix)).findAny();

        if (clientRequestAnnotationCheck.isPresent()) {
          System.out.println("Client " + name + " is called");

          final int startPosition = node.getStartPosition();
          final int line = astRoot.getLineNumber(startPosition);
          final int endPosition = startPosition + node.getName().getLength();
          final MarkerPosition position = new MarkerPosition(line, startPosition, endPosition);
          final String markerInfoTitle = "Microservice Client Invocation " + node.getName().toString();

          final Map<String, Object> context = Maps.newHashMap();
          // context.put("", kind);
          final String description = templateHandler.getContent(HOVER_TEMPLATE, context);

          try {
            javaFile.addMarker(MarkerSpecification.of(CostIds.COST_MARKER, position, IMarker.SEVERITY_INFO, CostMarkerTypes.CLIENT_INVOCATION, markerInfoTitle).and(MarkerAttributes.DESCRIPTION,
                description));
          }
          catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        return false; // do not go further to children
      }
    });
  }
}
