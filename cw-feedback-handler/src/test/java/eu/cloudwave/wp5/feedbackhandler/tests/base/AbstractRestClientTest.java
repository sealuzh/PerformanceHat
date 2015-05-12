package eu.cloudwave.wp5.feedbackhandler.tests.base;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import eu.cloudwave.wp5.feedbackhandler.rest.RestClientImpl;

/**
 * Provides basic functionality for test classes that want to test a {@link RestTemplate} with the
 * {@link MockRestServiceServer}.
 */
public abstract class AbstractRestClientTest extends AbstractTest {

  protected static final String ANY_URL = "any";

  @Spy
  @InjectMocks
  protected RestClientImpl restClient = new RestClientImpl();

  @Spy
  protected RestTemplate restTemplate = new RestTemplate();

  protected MockRestServiceServer mockServer;

  @Before
  public void setup() {
    mockServer = MockRestServiceServer.createServer(restTemplate);
    MockitoAnnotations.initMocks(this);
  }

}
