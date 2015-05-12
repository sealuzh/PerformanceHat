/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
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
package eu.cloudwave.wp5.feedback.eclipse.core.builders;

import static eu.cloudwave.wp5.feedback.eclipse.tests.assertion.Asserts.assertHasMarkers;
import static eu.cloudwave.wp5.feedback.eclipse.tests.assertion.Asserts.assertHasNoMarkers;
import static eu.cloudwave.wp5.feedback.eclipse.tests.assertion.Asserts.assertMarkerMessages;

import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.junit.Test;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.util.TimeValues;
import eu.cloudwave.wp5.feedback.eclipse.base.core.BaseIds;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.tests.base.AbstractWorkbenchTest;
import eu.cloudwave.wp5.feedback.eclipse.tests.fixtures.samples.SampleProject;
import eu.cloudwave.wp5.feedback.eclipse.tests.stubs.Stubs;

/**
 * Test cases for the Feedback Builder.
 */
public class FeedbackBuilderTest extends AbstractWorkbenchTest {

  private static final String MARKER_MESSAGE_PATTERN = "Hotspot %s: Average Execution Time of %s is %s.";
  private static final String PROCEDURE_KIND = "Method";
  private static final int DECIMAL_PLACES = 3;

  /**
   * Tests whether the builder is triggered when the nature is enabled.
   */
  @Test
  public void testBuildWhenNatureIsEnabled() throws CoreException, OperationCanceledException, InterruptedException {
    final FeedbackProject feedbackProject = sampleProject.getFeedbackProject();
    assertHasNoMarkers(feedbackProject, BaseIds.MARKER);
    enableFeedbackNature();
    assertHasMarkers(feedbackProject, BaseIds.MARKER, 2);
  }

  /**
   * Tests whether the marker has the right message.
   */
  @Test
  public void testMarkerMessage() throws OperationCanceledException, CoreException, InterruptedException {
    enableFeedbackNature();

    final FeedbackJavaFile javaFile = sampleProject.getFeedbackProject().getJavaSourceFile(SampleProject.APP.getName()).get();
    final Set<IMarker> markers = javaFile.findMarkers(BaseIds.MARKER);
    assertMarkerMessages(markers, getMarkerMessage(Stubs.PROCECDURE_DTO));
  }

  private String getMarkerMessage(final AggregatedProcedureMetricsDto procedureDto) {
    final String executionTime = TimeValues.toText(procedureDto.getAverageExecutionTime(), DECIMAL_PLACES);
    return String.format(MARKER_MESSAGE_PATTERN, PROCEDURE_KIND, procedureDto.getProcedure().getName(), executionTime);
  }
}
