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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
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
public class MicroserviceMethodDeclarationParticipant extends AbstractCostFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  private FeedbackHandlerEclipseClient feedbackHandlerClient;

  private TemplateHandler templateHandler;

  private final static String HOVER_TEMPLATE = "methodDeclaration";

  private String annotationPrefix = "@" + Ids.MICROSERVICE_ENDPOINT_ANNOTATION;

  /**
   * Constructor: initialize dependencies from juice
   */
  public MicroserviceMethodDeclarationParticipant() {
    this.feedbackHandlerClient = CostPluginActivator.instance(FeedbackHandlerEclipseClient.class);
    this.templateHandler = CostPluginActivator.instance(TemplateHandler.class);
  }

  /**
   * Build file in which a service endpoint/method is defined
   */
  @Override
  protected void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot) {

    // System.out.println("EndpointParticipant buildFile");
    astRoot.accept(new ASTVisitor() {

      private AggregatedIncomingRequestsDto[] incomingRequests;
      private Map<String, AggregatedIncomingRequestsDto> incomingRequestByMethod;

      private AggregatedIncomingRequestsDto overallRequest;

      private AggregatedMicroserviceRequestsDto[] requests;

      private ApplicationDto application;

      @Override
      public boolean visit(MethodDeclaration node) {
        Optional<?> annotationCheck = checkMethodDeclarationAnnotation(node.modifiers());
        if (annotationCheck.isPresent()) {

          /*
           * Positions
           */
          int startPosition = node.getStartPosition();

          final Javadoc doc = node.getJavadoc();
          if (doc != null) {
            startPosition += doc.getLength() + annotationIndent + 1; // +1 for line break
          }

          final int line = astRoot.getLineNumber(startPosition);
          final int endPosition = startPosition + annotationPrefix.length();
          final MarkerPosition position = new MarkerPosition(line, startPosition, endPosition);

          final String markerInfoTitle = "Microservice Method " + node.getName().toString();
          final String serviceMethodIdentifier = extractAttributeValueFromAnnotation(annotationCheck.get().toString(), Ids.MICROSERVICE_DECLARATION_ANNOTATION_METHOD_ATTRIBUTE);
          final String serviceMethodName = node.getName().getIdentifier();
          final AggregatedIncomingRequestsDto incomingRequests = getIncomingRequestsByMethod(serviceMethodIdentifier);

          /*
           * Freemarker Template Context
           */
          final Map<String, Object> context = Maps.newHashMap();
          context.put("from", timeRangeFrom);
          context.put("to", timeRangeTo);
          context.put("interval", aggregationInterval);

          context.put("instances", getApplication().getInstances());
          context.put("maxRequests", getApplication().getMaxRequestsPerInstancePerSecond());
          context.put("pricePerInstance", getApplication().getPricePerInstanceInUSD());

          context.put("requests", getRequestsByCallee());
          if (incomingRequests != null) {
            context.put("incomingMin", incomingRequests.getMin());
            context.put("incomingAvg", incomingRequests.getAvg());
            context.put("incomingMax", incomingRequests.getMax());
          }

          if (getOverallRequests() != null) {
            context.put("overallMin", getOverallRequests().getMin());
            context.put("overallAvg", getOverallRequests().getAvg());
            context.put("overallMax", getOverallRequests().getMax());
          }

          context.put("serviceIdentifier", serviceIdentifier);
          if (serviceMethodIdentifier != null) {
            context.put("serviceMethod", serviceMethodIdentifier);
          }
          else {
            context.put("serviceMethod", serviceMethodName);
          }

          final String description = templateHandler.getContent(HOVER_TEMPLATE, context);

          // create marker
          if (showMethodDeclarationHover) {
            try {
              javaFile.addMarker(MarkerSpecification.of(CostIds.COST_MARKER, position, IMarker.SEVERITY_INFO, CostMarkerTypes.METHOD_DECLARATION, markerInfoTitle).and(MarkerAttributes.DESCRIPTION,
                  description));
            }
            catch (CoreException e) {}
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
       * Overall request statistics
       * 
       * @return AggregatedIncomingRequestsDto with min, avg and max
       */
      private AggregatedIncomingRequestsDto getOverallRequests() {
        if (overallRequest == null) {
          overallRequest = feedbackHandlerClient.incomingRequestsByIdentifierOverall(project, aggregationInterval, timeRangeFrom, timeRangeTo);
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
        if (application == null) {
          application = feedbackHandlerClient.currentApplication(project);
        }
        return application;
      }

      private Optional<?> checkMethodDeclarationAnnotation(List<?> modifiers) {
        // we do not go through the whole list:
        // the filter is only going to be applied until we reach any valid element
        return modifiers.stream().filter(modifier -> modifier.toString().startsWith(annotationPrefix)).findAny();
      }
    });
  }
}
