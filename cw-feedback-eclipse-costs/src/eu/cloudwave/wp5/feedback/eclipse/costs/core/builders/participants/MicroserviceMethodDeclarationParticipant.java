package eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.common.dto.AggregatedMicroserviceRequestsDto;
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
import eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.markers.CostMarkerTypes;

/**
 * A builder participant that is responsible to display warnings for microservice endpoints
 */
public class MicroserviceMethodDeclarationParticipant extends AbstractFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  private FeedbackHandlerEclipseClient feedbackHandlerClient;

  private TemplateHandler templateHandler;

  private final static String HOVER_TEMPLATE = "methodDeclaration";

  public MicroserviceMethodDeclarationParticipant() {
    this.feedbackHandlerClient = CostPluginActivator.instance(FeedbackHandlerEclipseClient.class);
    this.templateHandler = CostPluginActivator.instance(TemplateHandler.class);
  }

  @Override
  protected void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {
    // System.out.println("EndpointParticipant buildFile");
    astRoot.accept(new ASTVisitor() {

      private AggregatedMicroserviceRequestsDto[] requests;

      private AggregatedMicroserviceRequestsDto[] getRequestsByCallee() {
        if (requests == null) {
          requests = feedbackHandlerClient.requestsByCallee(project);
        }
        return requests;
      }

      @Override
      public boolean visit(MethodDeclaration node) {

        AggregatedMicroserviceRequestsDto[] requests = getRequestsByCallee();

        String prefix = "@" + Ids.MICROSERVICE_ENDPOINT_ANNOTATION;
        List<?> myList = node.modifiers();

        // we do not go through the whole list, the filter is only going to be applied until we reach any valid element
        Optional<?> endpointAnnotationCheck = myList.stream().filter(m -> m.toString().startsWith(prefix)).findAny();

        if (endpointAnnotationCheck.isPresent()) {
          // System.out.println(prefix + " is present: " + endpointAnnotationCheck.isPresent());
          final int startPosition = node.getStartPosition();
          final int line = astRoot.getLineNumber(startPosition);
          final int endPosition = startPosition + prefix.length();
          final MarkerPosition position = new MarkerPosition(line, startPosition, endPosition);
          final String markerInfoTitle = "Microservice Method " + node.getName().toString();

          // for (AggregatedMicroserviceRequestsDto request : requests) {
          // markerDescription += "<li>" + request.getCaller() + " </li>";
          // }

          final Map<String, Object> context = Maps.newHashMap();
          context.put("requests", requests);
          final String description = templateHandler.getContent(HOVER_TEMPLATE, context);

          try {
            javaFile.addMarker(MarkerSpecification.of(CostIds.COST_MARKER, position, IMarker.SEVERITY_INFO, CostMarkerTypes.METHOD_DECLARATION, markerInfoTitle).and(MarkerAttributes.DESCRIPTION,
                description));
          }
          catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        return true; // do not go further to children
      }
    });
  }
}
