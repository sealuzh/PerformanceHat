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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostIds;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.markers.CostMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers.CostContextBuilder;

/**
 * A builder participant that is responsible to display warnings for microservice endpoints
 */
public class MicroserviceMethodDeclarationParticipant extends AbstractCostFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  /**
   * The name of the template which should be used to display the hover
   */
  private final static String HOVER_TEMPLATE = "methodDeclaration";

  /**
   * The name of the annotation which this MicroserviceMethodDeclarationParticipant cares about
   */
  private String targetAnnotation = "@" + Ids.MICROSERVICE_ENDPOINT_ANNOTATION;

  /**
   * Building files in which microservice endpoints/methods are defined
   */
  @Override
  protected void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {

    // System.out.println("EndpointParticipant buildFile");
    astRoot.accept(new ASTVisitor() {

      private AggregatedIncomingRequestsDto[] incomingRequests;
      private Map<String, AggregatedIncomingRequestsDto> incomingRequestByMethod;

      private AggregatedIncomingRequestsDto overallRequest;

      private AggregatedMicroserviceRequestsDto[] requests;

      @Override
      public boolean visit(MethodDeclaration node) {
        Optional<?> annotationCheck = checkMethodDeclarationAnnotation(node.modifiers());
        if (annotationCheck.isPresent()) {

          final String serviceMethodIdentifier = extractAttributeValueFromAnnotation(annotationCheck.get().toString(), Ids.MICROSERVICE_DECLARATION_ANNOTATION_METHOD_ATTRIBUTE);
          final String serviceMethodName = node.getName().getIdentifier();

          /*
           * Depending on the user's properties we create and add a marker...
           */
          if (showMethodDeclarationHover) {

            // Marker Specification
            int startPosition = node.getStartPosition();
            if (node.getJavadoc() != null) {
              startPosition += node.getJavadoc().getLength() + annotationIndent + 1; // +1 for line break
            }
            final int line = astRoot.getLineNumber(startPosition);
            final int endPosition = startPosition + targetAnnotation.length();
            final MarkerPosition position = new MarkerPosition(line, startPosition, endPosition);
            final String markerInfoTitle = "Microservice Method " + node.getName().toString();
            MarkerSpecification costMarker = MarkerSpecification.of(CostIds.COST_MARKER, position, IMarker.SEVERITY_INFO, CostMarkerTypes.METHOD_DECLARATION, markerInfoTitle);

            /*
             * Preparation of the content of the hover which is rendered by Freemarker. The template is specified above
             * and can be found in the OSGI-INF/l10n/templates folder.
             */
            // @formatter:off
            costMarker = costMarker.and(MarkerAttributes.DESCRIPTION, templateHandler.getContent(HOVER_TEMPLATE, CostContextBuilder.init()
                .setTimeParameters(timeRangeFrom, timeRangeTo, aggregationInterval)
                .setApplication(getApplication())
                .setRequestStats("incoming", getIncomingRequestsByMethod(serviceMethodIdentifier))
                .setRequestStats("overall", getOverallRequests())
                .add("serviceIdentifier", serviceIdentifier)  // substring from the properties: (eu.cloudwave.samples.services.) currency
                .addIfNotNull("serviceMethod", serviceMethodIdentifier, serviceMethodName)
                .add("requests", getRequestsByCallee())
                .build()));
            // @formatter:on

            addMarker(javaFile, costMarker);
          }
        }
        return true; // do not go further to children
      }

      /**
       * Requests by callee in order to display different callers
       * 
       * @return AggregatedMicroserviceRequestsDto with min, avg and max
       */
      private AggregatedMicroserviceRequestsDto[] getRequestsByCallee() {
        if (requests == null) {
          requests = feedbackHandlerClient.requestsByCallee(project, aggregationInterval, timeRangeFrom, timeRangeTo);
        }
        return requests;
      }

      /**
       * Overall incoming request statistics to the application of the current project in the workspace.
       * 
       * @return AggregatedIncomingRequestsDto with min, avg and max
       */
      private AggregatedIncomingRequestsDto getOverallRequests() {
        if (overallRequest == null) {
          overallRequest = feedbackHandlerClient.overallIncomingRequestsByIdentifier(project, aggregationInterval, timeRangeFrom, timeRangeTo);
        }
        return overallRequest;
      }

      /**
       * Incoming request statistics of the current project/service
       * 
       * @return AggregatedMicroserviceRequestsDto with min, avg and max
       */
      private AggregatedIncomingRequestsDto[] getIncomingRequests() {
        if (incomingRequests == null) {
          incomingRequests = feedbackHandlerClient.incomingRequestsByIdentifier(project, aggregationInterval, timeRangeFrom, timeRangeTo);
        }
        return incomingRequests;
      }

      private AggregatedIncomingRequestsDto getIncomingRequestsByMethod(String key) {
        if (incomingRequestByMethod == null) {
          incomingRequestByMethod = Maps.newHashMap();

          for (AggregatedIncomingRequestsDto request : getIncomingRequests()) {
            incomingRequestByMethod.put(request.getMethod(), request);
          }
        }
        return incomingRequestByMethod.get(key);
      }

      /**
       * Returns application of the current project
       * 
       * @return {@link ApplicationDto}
       */
      private ApplicationDto getApplication() {
        ApplicationDto app = cache.get(project.getApplicationId());
        if (app == null) {
          app = cache.addAndReturn(project.getApplicationId(), feedbackHandlerClient.currentApplication(project));
        }
        return app;
      }

      private Optional<?> checkMethodDeclarationAnnotation(List<?> modifiers) {
        // we do not go through the whole list:
        // the filter is only going to be applied until we reach any valid element
        return modifiers.stream().filter(modifier -> modifier.toString().startsWith(targetAnnotation)).findAny();
      }
    });
  }
}
