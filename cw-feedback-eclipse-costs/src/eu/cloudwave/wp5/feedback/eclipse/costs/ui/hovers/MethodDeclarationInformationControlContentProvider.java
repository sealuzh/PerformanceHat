/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.costs.ui.hovers;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.AbstractFeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.base.ui.hovers.contentprovider.FeedbackInformationControlContentProvider;
import eu.cloudwave.wp5.feedback.eclipse.costs.core.CostPluginActivator;

/**
 * Implementation of {@link FeedbackInformationControlContentProvider}
 */
public class MethodDeclarationInformationControlContentProvider extends AbstractFeedbackInformationControlContentProvider implements FeedbackInformationControlContentProvider {

  @Override
  protected void fillIndividualContent() {

  }

  @Override
  protected FeedbackJavaResourceFactory getFeedbackJavaResourceFactory() {
    return CostPluginActivator.instance(FeedbackJavaResourceFactory.class);
  }
}
