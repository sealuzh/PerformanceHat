package eu.cloudwave.wp5.feedbackhandler.rest;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.feedbackhandler.tests.base.AbstractRestClientTest;

public class JsonRestClientImplTest extends AbstractRestClientTest {

  private static final String ANY_URL = "any";
  private static final String RESPONSE_STUB_PATH = "/stubs/JsonRestClientImplTestResponseStub.json";

  @InjectMocks
  private JsonRestClient jsonRestClient = new JsonRestClientImpl();

  private String responseStub;

  @Before
  public void setupStubs() throws UnsupportedEncodingException, IOException {
    responseStub = getResourceAsString(RESPONSE_STUB_PATH);
  }

  @Test
  public void testGet() throws JsonProcessingException, IOException {
    setupExpectation(HttpMethod.GET);
    assertResponseOk(jsonRestClient.get(ANY_URL));
  }

  @Test
  public void testPost() throws UnsupportedEncodingException, IOException {
    setupExpectation(HttpMethod.POST);
    assertResponseOk(jsonRestClient.post(ANY_URL, RestRequestBody.of()));
  }

  private void setupExpectation(final HttpMethod httpMethod) throws UnsupportedEncodingException, IOException {
    mockServer.expect(requestTo(ANY_URL)).andExpect(method(httpMethod)).andRespond(withSuccess(responseStub, MediaType.APPLICATION_JSON));
  }

  private void assertResponseOk(final JsonNode actualResponse) throws JsonProcessingException, IOException {
    final JsonNode expectedResponse = new ObjectMapper().readTree(responseStub);
    assertThat(actualResponse.toString()).isEqualTo(expectedResponse.toString());
  }

}
