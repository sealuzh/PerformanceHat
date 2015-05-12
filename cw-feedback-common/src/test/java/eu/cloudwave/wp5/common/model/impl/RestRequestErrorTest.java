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
package eu.cloudwave.wp5.common.model.impl;

import static eu.cloudwave.wp5.common.tests.assertion.Asserts.assertEquality;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import eu.cloudwave.wp5.common.dto.RestRequestErrorDto;
import eu.cloudwave.wp5.common.error.ErrorType;

public class RestRequestErrorTest {

  private static final String EQUAL_MESSAGE = "equal message";
  private static final String ANOTHER_MESSAGE = "another message";
  private static final RestRequestErrorDto RRE_EQUAL_ONE = new RestRequestErrorDto(ErrorType.GENERAL, EQUAL_MESSAGE);
  private static final RestRequestErrorDto RRE_EQUAL_TWO = new RestRequestErrorDto(ErrorType.GENERAL, EQUAL_MESSAGE);
  private static final RestRequestErrorDto RRE_DIFFERENT_ONE = new RestRequestErrorDto(ErrorType.GENERAL, ANOTHER_MESSAGE);
  private static final RestRequestErrorDto RRE_DIFFERENT_TWO = new RestRequestErrorDto(ErrorType.UNKNOWN, EQUAL_MESSAGE);
  private static final RestRequestErrorDto RRE_DIFFERENT_THREE = new RestRequestErrorDto(ErrorType.UNKNOWN, ANOTHER_MESSAGE);

  @Test
  public void testEqualsAndHashCode() {
    assertEquality(ImmutableList.of(RRE_EQUAL_ONE, RRE_EQUAL_TWO), ImmutableList.of(RRE_DIFFERENT_ONE, RRE_DIFFERENT_TWO, RRE_DIFFERENT_THREE));
  }

}
