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
package eu.cloudwave.wp5.feedback.eclipse.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import eu.cloudwave.wp5.feedback.eclipse.core.builders.FeedbackBuilderErrorHandlingTest;
import eu.cloudwave.wp5.feedback.eclipse.core.natures.FeedbackProjectNatureTest;
import eu.cloudwave.wp5.feedback.eclipse.ui.preferences.FeedbackPreferencesTest;
import eu.cloudwave.wp5.feedback.eclipse.ui.properties.FeedbackPropertiesTest;

/**
 * Running all SWTBot tests.
 */
@RunWith(Suite.class)
@SuiteClasses({//@formatter:off
  FeedbackBuilderErrorHandlingTest.class,
  FeedbackPreferencesTest.class,
  FeedbackPropertiesTest.class,
  FeedbackProjectNatureTest.class,
})//@formatter:on
public class AllSwtBotTests {}
