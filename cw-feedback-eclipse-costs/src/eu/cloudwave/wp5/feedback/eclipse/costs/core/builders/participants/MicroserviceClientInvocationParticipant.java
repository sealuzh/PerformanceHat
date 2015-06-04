/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.costs.core.builders.participants;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.InitialInvocationCheckDto;
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
 * A builder participant that is responsible to display warnings for microservice client request calls.
 */
public class MicroserviceClientInvocationParticipant extends AbstractCostFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  private FeedbackHandlerEclipseClient feedbackHandlerClient;

  private TemplateHandler templateHandler;

  private final static String HOVER_TEMPLATE = "clientInvocation";

  public MicroserviceClientInvocationParticipant() {
    this.feedbackHandlerClient = CostPluginActivator.instance(FeedbackHandlerEclipseClient.class);
    this.templateHandler = CostPluginActivator.instance(TemplateHandler.class);
  }

  @Override
  protected void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {
    // System.out.println("ClientRequestParticipant buildFile");
    astRoot.accept(new ASTVisitor() {

      private AggregatedMicroserviceRequestsDto overallRequest;

      String currentMethodDeclaration;
      String currentMethodDeclarationClassname;

      @Override
      public boolean visit(MethodInvocation node) {
        String name = node.getName().getIdentifier();

        IAnnotationBinding[] annotations = node.resolveMethodBinding().getMethodDeclaration().getAnnotations();
        String prefix = "@" + Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION;

        // we do not go through the whole list, the filter is only going to be applied until we reach any valid element
        Optional<IAnnotationBinding> annotationCheck = Arrays.asList(annotations).stream().filter(m -> m.toString().startsWith(prefix)).findAny();

        if (annotationCheck.isPresent()) {
          System.out.println("Client " + name + " is called within the method " + this.currentMethodDeclaration);

          final int startPosition = node.getStartPosition() + node.getExpression().getLength() + 1; // +1 for the dot
          final int line = astRoot.getLineNumber(startPosition);
          final int endPosition = startPosition + node.getName().getLength();
          final MarkerPosition position = new MarkerPosition(line, startPosition, endPosition);
          final String markerInfoTitle = "Microservice Client Invocation " + node.getName().toString();

          // prepare context for freemarker template
          final Map<String, Object> context = Maps.newHashMap();

          context.put("from", timeRangeFrom);
          context.put("to", timeRangeTo);
          context.put("interval", aggregationInterval);

          context.put("overallMin", getOverallRequestsByCallee().getMin());
          context.put("overallAvg", getOverallRequestsByCallee().getAvg());
          context.put("overallMax", getOverallRequestsByCallee().getMax());

          // substring from the properties: (eu.cloudwave.samples.services.) currency
          context.put("serviceIdentifier", serviceIdentifier);

          // YahooClient
          String invokedClassname = node.getExpression().resolveTypeBinding().getQualifiedName();
          context.put("invokedClassname", invokedClassname);

          ApplicationDto app;

          try {
            String invokedServiceIdentifier = "";

            for (IMemberValuePairBinding binding : annotationCheck.get().getAllMemberValuePairs()) {
              if (binding.getName().equals(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_ATTRIBUTE)) {
                invokedServiceIdentifier = binding.getValue().toString();
              }
            }

            app = getApplication(invokedServiceIdentifier);

            context.put("instances", app.getInstances());
            context.put("maxRequests", app.getMaxRequestsPerInstancePerSecond());
            context.put("pricePerInstance", app.getPricePerInstanceInUSD());
          }
          catch (Exception e) {}

          // getCHFtoEUR
          context.put("invokedMethodName", name);

          boolean isNewlyInvoked = checkIfMethodInvocationIsNew(new InitialInvocationCheckDto(invokedClassname, name, currentMethodDeclarationClassname, currentMethodDeclaration));
          context.put("isNew", isNewlyInvoked);

          final String description = templateHandler.getContent(HOVER_TEMPLATE, context);

          if ((showNewInvocationHover && isNewlyInvoked) || (showExistingInvocationHover && !isNewlyInvoked)) {
            try {
              javaFile.addMarker(MarkerSpecification.of(CostIds.COST_MARKER, position, IMarker.SEVERITY_INFO, CostMarkerTypes.CLIENT_INVOCATION, markerInfoTitle).and(MarkerAttributes.DESCRIPTION,
                  description));
            }
            catch (CoreException e) {}
          }
        }
        return false; // do not go further to children
      }

      /**
       * It looks like this is the cheapest way to always know the name of the method which is responsible for the
       * MethodInvocation of our client
       * 
       * @param node
       * @return true
       */
      @Override
      public boolean visit(MethodDeclaration node) {
        currentMethodDeclaration = node.getName().getIdentifier();
        currentMethodDeclarationClassname = node.resolveBinding().getDeclaringClass().getQualifiedName();
        return true; // go further to children
      }

      /**
       * Overall request statistics of the current project/service
       * 
       * @return AggregatedMicroserviceRequestsDto with min, avg and max
       */
      private AggregatedMicroserviceRequestsDto getOverallRequestsByCallee() {
        if (overallRequest == null) {
          overallRequest = feedbackHandlerClient.requestsByCalleeOverall(project, aggregationInterval, timeRangeFrom, timeRangeTo);
        }
        return overallRequest;
      }

      /**
       * Returns an application
       * 
       * @return {@link ApplicationDto}
       */
      private ApplicationDto getApplication(final String applicationId) {
        return feedbackHandlerClient.application(project, applicationId);
      }

      /**
       * Check if client method invocation was newly added to the current method
       * 
       * @param invocationDto
       * @return boolean that indicates if this invocation was newly added to the current method
       */
      private boolean checkIfMethodInvocationIsNew(final InitialInvocationCheckDto invocationDto) {
        if (invocationDto.getCallerClassName() == null || invocationDto.getCallerMethodName() == null) {
          return true;
        }
        return feedbackHandlerClient.isNewlyInvoked(project, invocationDto);
      }
    });
  }
}
