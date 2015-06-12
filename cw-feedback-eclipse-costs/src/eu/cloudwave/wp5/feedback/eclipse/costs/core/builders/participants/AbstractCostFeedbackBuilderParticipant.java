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

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMemberValuePairBinding;

import eu.cloudwave.wp5.common.constants.AggregationInterval;
import eu.cloudwave.wp5.common.util.AggregationIntervalConverter;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.AbstractFeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.template.TemplateHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.cache.ApplicationDtoCache;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.feedbackhandler.FeedbackHandlerEclipseClient;
import eu.cloudwave.wp5.feedback.eclipse.costs.ui.Messages;

public abstract class AbstractCostFeedbackBuilderParticipant extends AbstractFeedbackBuilderParticipant {

  protected String serviceIdentifier;

  protected AggregationInterval aggregationInterval;
  protected int annotationIndent;

  protected String timeRangeFrom;
  protected String timeRangeTo;

  protected boolean showExistingInvocationHover;
  protected boolean showNewInvocationHover;
  protected boolean showMethodDeclarationHover;

  protected FeedbackHandlerEclipseClient feedbackHandlerClient;
  protected TemplateHandler templateHandler;
  protected ApplicationDtoCache cache;

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(final FeedbackJavaProject project, final Set<FeedbackJavaFile> javaFiles) throws CoreException {

    feedbackHandlerClient = CostPluginActivator.instance(FeedbackHandlerEclipseClient.class);
    templateHandler = CostPluginActivator.instance(TemplateHandler.class);
    cache = ApplicationDtoCache.getInstance();

    // reload properties before we call buildFile for every file that has to be rebuilt
    this.reloadProperties(project);

    // build files
    super.build(project, javaFiles);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected abstract void buildFile(final FeedbackJavaProject project, final FeedbackJavaFile javaFile, final CompilationUnit astRoot);

  /**
   * Helper to extract value of annotation attribute from string
   * 
   * @param annotationContent
   *          {@link String} with content of all annotations of this node
   * @param attribute
   *          name of the attribute we want to extract
   * 
   * @return attribute value
   */
  protected String extractAttributeValueFromAnnotation(String annotationContent, String attribute) {
    attribute += "=";
    annotationContent = annotationContent.substring(annotationContent.indexOf(attribute) + attribute.length());
    char quoteChar = annotationContent.charAt(0);
    if (quoteChar == '"') {
      return annotationContent.split("\"")[1];
    }
    else {
      return null;
    }
  }

  /**
   * Helper to extract value of annotation attribute from {@link IMemberValuePairBinding}
   * 
   * @param annotationContent
   *          {@link IMemberValuePairBinding} with key/value annotation attribute bindings
   * @param attribute
   *          name of the attribute we want to extract
   * 
   * @return attribute value
   */
  protected String extractAttributeValueFromAnnotation(IMemberValuePairBinding[] annotationContent, String attribute) {
    for (IMemberValuePairBinding binding : annotationContent) {
      if (binding.getName().equals(attribute)) {
        return binding.getValue().toString();
      }
    }
    return null;
  }

  /**
   * Load properties from current feedback project
   */
  protected void reloadProperties(final FeedbackProject project) {
    serviceIdentifier = project.getFeedbackProperties().get(Messages.PROPERTIES__MICROSERVICE_IDENTIFIER__KEY, "unknown");
    annotationIndent = Integer.parseInt(project.getFeedbackProperties().get(Messages.PROPERTIES__FORMATTING__ANNOTATION_INDENT__KEY, Messages.PROPERTIES__FORMATTING__ANNOTATION_INDENT__DEFAULT));

    showExistingInvocationHover = project.getFeedbackProperties().getBoolean(Messages.PROPERTIES__FEATURE__HOVER_EXISTING_INVOCATIONS__ISACTIVATED__KEY, true);
    showNewInvocationHover = project.getFeedbackProperties().getBoolean(Messages.PROPERTIES__FEATURE__HOVER_NEW_INVOCATIONS__ISACTIVATED__KEY, true);
    showMethodDeclarationHover = project.getFeedbackProperties().getBoolean(Messages.PROPERTIES__FEATURE__HOVER_METHOD_DECLARATION__ISACTIVATED__KEY, true);

    aggregationInterval = AggregationIntervalConverter.fromString(project.getFeedbackProperties().get(Messages.PROPERTIES__TIME__AGGREGATION__INTERVAL__KEY,
        Messages.PROPERTIES__TIME__AGGREGATION__INTERVAL__DEFAULT));
    boolean timeRangeFromIsActivated = project.getFeedbackProperties().getBoolean(Messages.PROPERTIES__TIME__FROM__ISACTIVATED__KEY, false);
    if (timeRangeFromIsActivated) {
      timeRangeFrom = project.getFeedbackProperties().get(Messages.PROPERTIES__TIME__FROM__KEY, null);
    }
    else {
      timeRangeFrom = null;
    }

    boolean timeRangeToIsActivated = project.getFeedbackProperties().getBoolean(Messages.PROPERTIES__TIME__TO__ISACTIVATED__KEY, false);
    if (timeRangeToIsActivated) {
      timeRangeTo = project.getFeedbackProperties().get(Messages.PROPERTIES__TIME__TO__KEY, null);
    }
    else {
      timeRangeTo = null;
    }
  }
}
