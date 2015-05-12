package eu.cloudwave.wp5.feedbackhandler.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.rest.AbstractRestClient;
import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * Implementation of {@link RestClient}.
 */
@Service
public class RestClientImpl extends AbstractRestClient implements RestClient {

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> ResponseEntity<T> get(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders) {
    return restTemplate(requestHeaders).getForEntity(url, responseType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> ResponseEntity<T> post(final String url, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) {
    return restTemplate(requestHeaders).postForEntity(url, requestBody.get(), responseType);
  }

}
