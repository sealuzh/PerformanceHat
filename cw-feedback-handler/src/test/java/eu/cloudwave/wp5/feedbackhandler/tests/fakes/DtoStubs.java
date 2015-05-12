package eu.cloudwave.wp5.feedbackhandler.tests.fakes;

import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;

/**
 * Provides common stubs that can be used as fake object in test cases.
 */
public class DtoStubs {

  public static final MethodInfoSummarized METHOD_INFO_SUMARIZED = new MethodInfoSummarized(1.5, 5);

  private DtoStubs() {}

}
