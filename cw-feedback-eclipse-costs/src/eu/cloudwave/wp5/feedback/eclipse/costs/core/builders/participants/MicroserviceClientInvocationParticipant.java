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
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.common.dto.ApplicationDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedIncomingRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedMicroserviceRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.AggregatedRequestsDto;
import eu.cloudwave.wp5.common.dto.costs.InitialInvocationCheckDto;
import eu.cloudwave.wp5.common.model.Prediction;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostIds;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.markers.CostMarkerTypes;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.predictions.PredictionStrategy;
import eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers.CostContextBuilder;

/**
 * A builder participant that is responsible for the client invocation hovers. A hover will be added whenever you call a
 * method of a class that is marked as {@link Ids#MICROSERVICE_CLIENT_REQUEST_ANNOTATION}. <br />
 * <br />
 * The {@link FeedbackBuilderParticipant} calls the
 * {@link MicroserviceClientInvocationParticipant#buildFile(FeedbackJavaProject, FeedbackJavaFile, CompilationUnit)}
 * method for every file in your workspace that is built.
 */
public class MicroserviceClientInvocationParticipant extends AbstractCostFeedbackBuilderParticipant implements FeedbackBuilderParticipant {

  /**
   * The name of the template which should be used to display the hover
   */
  private final static String HOVER_TEMPLATE = "clientInvocation";

  /**
   * The name of the annotation which this MicroserviceClientInvocationParticipant cares about
   */
  private final static String targetAnnotation = "@" + Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION;

  /**
   * The prediction strategy that predicts cost impacts of changes
   */
  private PredictionStrategy strategy;

  /**
   * Constructor that initializes implementation of cost prediction strategy
   */
  public MicroserviceClientInvocationParticipant() {
    strategy = CostPluginActivator.instance(PredictionStrategy.class);
  }

  /**
   * Building files in which microservice client methods are invoked
   */
  @Override
  public void buildFile(FeedbackJavaProject project, FeedbackJavaFile javaFile, CompilationUnit astRoot/*, CompilationUnit oldAstRoot*/) {
    // System.out.println("ClientRequestParticipant buildFile");
    astRoot.accept(new ASTVisitor() {

      private AggregatedMicroserviceRequestsDto overallRequest;

      String currentMethodDeclaration;
      String currentMethodDeclarationClassname;

      @Override
      public boolean visit(MethodInvocation node) {
        String invokedMethodName = node.getName().getIdentifier();
        IAnnotationBinding[] annotationsOfNode = node.resolveMethodBinding().getMethodDeclaration().getAnnotations();

        // Does the current node really have a MicroserviceClientInvocation Annotation?
        // we do not go through the whole list, the filter is only going to be applied until we reach any valid element
        Optional<IAnnotationBinding> annotationCheck = Arrays.asList(annotationsOfNode).stream().filter(m -> m.toString().startsWith(targetAnnotation)).findAny();

        if (annotationCheck.isPresent()) {
          System.out.println("Client " + invokedMethodName + " is called within the method " + this.currentMethodDeclaration);

          // YahooClient
          String invokedClassname = node.getExpression().resolveTypeBinding().getQualifiedName();
          String invokedServiceIdentifier = extractAttributeValueFromAnnotation(annotationCheck.get().getAllMemberValuePairs(), Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_ATTRIBUTE);

          /*
           * This participant looks for client invocations. This means we call a class which is a client of another
           * Microservice x. In the following lines we'll try to detect the status of this service x (number of
           * instances, price per instance, etc.)
           */
          ApplicationDto app = getApplication(invokedServiceIdentifier);

          AggregatedRequestsDto requestsToInvokedService = getOverallRequestsOfApplication(invokedServiceIdentifier);

          List<Prediction> predictions = null;
          boolean isNewlyInvoked = checkIfMethodInvocationIsNew(new InitialInvocationCheckDto(invokedClassname, invokedMethodName, currentMethodDeclarationClassname, currentMethodDeclaration));
          if (isNewlyInvoked) {
            predictions = strategy.predict(app, requestsToInvokedService, getOverallRequestsByCallee());
          }

          /*
           * Depending on the user's properties we create and add a marker...
           */
          if ((showNewInvocationHover && isNewlyInvoked) || (showExistingInvocationHover && !isNewlyInvoked)) {

            // Marker Specification
            final int startPosition = node.getStartPosition() + node.getExpression().getLength() + 1; // +1 for the dot
            final int line = astRoot.getLineNumber(startPosition);
            final int endPosition = startPosition + node.getName().getLength();
            final MarkerPosition position = new MarkerPosition(line, startPosition, endPosition);
            final String markerInfoTitle = "Microservice Client Invocation " + node.getName().toString();
            MarkerSpecification costMarker = MarkerSpecification.of(CostIds.COST_MARKER, position, IMarker.SEVERITY_INFO, CostMarkerTypes.CLIENT_INVOCATION, markerInfoTitle);

            /*
             * Preparation of the hover content which is rendered by Freemarker. The template file is specified above
             * and can be found in the OSGI-INF/l10n/templates folder.
             */
            // @formatter:off
            costMarker = costMarker.and(MarkerAttributes.DESCRIPTION, templateHandler.getContent(HOVER_TEMPLATE, CostContextBuilder.init()
                .setTimeParameters(timeRangeFrom, timeRangeTo, aggregationInterval)
                .setRequestStats("overall", getOverallRequestsByCallee())
                .setApplication(app)
                 // substring from the properties: (eu.cloudwave.samples.services.) currency
                .add("serviceIdentifier", serviceIdentifier) 
                .add("invokedClassname", invokedClassname)
                .add("invokedMethodName", invokedMethodName)
                .add("isNew", isNewlyInvoked)
                .addIfNotNull("predictions", predictions)
                .build()));
            // @formatter:on

            addMarker(javaFile, costMarker);
          }
        }
        return false; // do not go further to children
      }

      /**
       * It looks like this is the easiest way to know the name of the method which is responsible for the current
       * MethodInvocation
       * 
       * @param node
       * 
       * @return true which means "go further to children"
       */
      @Override
      public boolean visit(MethodDeclaration node) {
        currentMethodDeclaration = node.getName().getIdentifier();
        currentMethodDeclarationClassname = node.resolveBinding().getDeclaringClass().getQualifiedName();
        return true;
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
        ApplicationDto app = cache.get(applicationId);
        if (app == null) {
          try {
            app = cache.addAndReturn(applicationId, feedbackHandlerClient.application(project, applicationId));
          }
          catch (Exception e) {}
        }
        return app;
      }

      private AggregatedIncomingRequestsDto getOverallRequestsOfApplication(final String applicationId) {
        return feedbackHandlerClient.overallIncomingRequestsByIdentifier(project, applicationId, aggregationInterval, timeRangeFrom, timeRangeTo);
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
