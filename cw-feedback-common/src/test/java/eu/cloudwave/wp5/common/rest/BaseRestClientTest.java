package eu.cloudwave.wp5.common.rest;

import static org.fest.assertions.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.google.common.io.CharStreams;

public class BaseRestClientTest {

  private static final String ANY_URL = "any";
  private static final String RESPONSE_STUB_PATH = "/BaseRestClientTestResponseStub.json";
  private static final String UTF_8 = "UTF-8";

  private static final String HEADER_ONE_NAME = "header-one-name";
  private static final String HEADER_ONE_VALUE = "header one value";
  private static final String HEADER_TWO_NAME = "header-two-name";
  private static final String HEADER_TWO_VALUE = "header two value";
  private static final String HEADER_THREE_NAME = "header-three-name";
  private static final String HEADER_THREE_VALUE = "header three value";

  @InjectMocks
  private RestClientMock restClientMock;

  @Spy
  private RestTemplate restTemplate = new RestTemplate();

  private MockRestServiceServer mockServer;

  @Before
  public void setup() {
    mockServer = MockRestServiceServer.createServer(restTemplate);
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testWithoutHeaders() throws UnsupportedEncodingException, IOException {
    mockServer.expect(requestTo(ANY_URL)).andExpect(method(HttpMethod.GET)).andRespond(withSuccess(getResponseStub(), MediaType.APPLICATION_JSON));

    final ResponseEntity<String> responseEntity = restClientMock.get(ANY_URL, String.class);
    assertThat(responseEntity.getBody()).isEqualTo(getResponseStub());
  }

  @Test
  public void testWithSingleHeader() throws UnsupportedEncodingException, IOException {
    mockServer.expect(requestTo(ANY_URL)).andExpect(method(HttpMethod.GET)).andExpect(header(HEADER_ONE_NAME, HEADER_ONE_VALUE)).andRespond(withSuccess(getResponseStub(), MediaType.APPLICATION_JSON));

    final ResponseEntity<String> responseEntity = restClientMock.get(ANY_URL, String.class, RestRequestHeader.of(HEADER_ONE_NAME, HEADER_ONE_VALUE));
    assertThat(responseEntity.getBody()).isEqualTo(getResponseStub());
  }

  @Test
  public void testWithMultipleHeaders() throws UnsupportedEncodingException, IOException {
    mockServer.expect(requestTo(ANY_URL)).andExpect(method(HttpMethod.GET)).andExpect(header(HEADER_ONE_NAME, HEADER_ONE_VALUE)).andExpect(header(HEADER_TWO_NAME, HEADER_TWO_VALUE))
        .andExpect(header(HEADER_THREE_NAME, HEADER_THREE_VALUE)).andRespond(withSuccess(getResponseStub(), MediaType.APPLICATION_JSON));

    final ResponseEntity<String> responseEntity = restClientMock.get(ANY_URL, String.class, RestRequestHeader.of(HEADER_ONE_NAME, HEADER_ONE_VALUE),
        RestRequestHeader.of(HEADER_TWO_NAME, HEADER_TWO_VALUE), RestRequestHeader.of(HEADER_THREE_NAME, HEADER_THREE_VALUE));
    assertThat(responseEntity.getBody()).isEqualTo(getResponseStub());
  }

  private String getResponseStub() throws UnsupportedEncodingException, IOException {
    final InputStream inputStream = getClass().getClass().getResourceAsStream(RESPONSE_STUB_PATH);
    return CharStreams.toString(new InputStreamReader(inputStream, UTF_8));
  }

  /**
   * A concrete sample implementation of {@link AbstractRestClient} to the functionality in {@link AbstractRestClient}.
   */
  private static final class RestClientMock extends AbstractRestClient {

    public <T> ResponseEntity<T> get(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders) {
      return restTemplate(requestHeaders).getForEntity(url, responseType);
    }

  }

}
