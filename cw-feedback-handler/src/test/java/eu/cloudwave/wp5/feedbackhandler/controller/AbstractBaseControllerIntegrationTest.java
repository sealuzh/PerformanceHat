package eu.cloudwave.wp5.feedbackhandler.controller;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A base class for controller tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
@WebAppConfiguration
public abstract class AbstractBaseControllerIntegrationTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  protected final String expectedResponseBodyOf(final String string) throws JsonProcessingException {
    return string;
  }

  protected final String expectedResponseBodyOf(final Object object) throws JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

  protected final void assertTextResponseOk(final MockHttpServletRequestBuilder requestBuilder, final String expectedResponseText) throws Exception {
    assertTextResponse(requestBuilder, status().isOk(), expectedResponseText);
  }

  protected final void assertObjectResponseOk(final MockHttpServletRequestBuilder requestBuilder, final Object expectedResponseBody) throws Exception {
    assertObjectResponse(requestBuilder, status().isOk(), expectedResponseBody);
  }

  protected final void assertTextResponse(final MockHttpServletRequestBuilder requestBuilder, final ResultMatcher expectedResponseStatus, final String expectedResponseText) throws Exception {
    assertThat(executeRequest(requestBuilder, expectedResponseStatus)).isEqualTo(expectedResponseText);
  }

  protected final void assertObjectResponse(final MockHttpServletRequestBuilder requestBuilder, final ResultMatcher expectedResponseStatus, final Object expectedResponseBody) throws Exception {
    assertThat(executeRequest(requestBuilder, expectedResponseStatus)).isEqualTo(expectedResponseBodyOf(expectedResponseBody));
  }

  private String executeRequest(final MockHttpServletRequestBuilder requestBuilder, final ResultMatcher expectedResponseStatus) throws Exception {
    final MvcResult result = mockMvc.perform(requestBuilder).andExpect(expectedResponseStatus).andReturn();
    return result.getResponse().getContentAsString();
  }

}
