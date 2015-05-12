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
