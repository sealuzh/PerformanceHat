package eu.cloudwave.wp5.common.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableList;

/**
 * Abstract base implementation for a REST-client.
 */
public abstract class AbstractRestClient {

  private RestTemplate restTemplate;

  public AbstractRestClient() {
    this.restTemplate = new RestTemplate();
  }

  /**
   * Creates a {@link RestTemplate} with the given HTTP headers.
   * 
   * @param requestHeaders
   *          the HTTP headers
   * @return the created {@link RestTemplate}
   */
  protected RestTemplate restTemplate(final RestRequestHeader[] requestHeaders) {
    if (hasHeaders(requestHeaders)) {
      addHeaders(requestHeaders);
    }
    else {
      final List<ClientHttpRequestInterceptor> interceptors = ImmutableList.of();
      restTemplate.setInterceptors(interceptors);
    }
    return restTemplate;
  }

  private boolean hasHeaders(final RestRequestHeader[] requestHeaders) {
    return requestHeaders != null && requestHeaders.length > 0;
  }

  private void addHeaders(final RestRequestHeader[] requestHeaders) {
    final ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {

      @Override
      public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {

        for (final RestRequestHeader requestHeader : requestHeaders) {
          request.getHeaders().add(requestHeader.getKey(), requestHeader.getValue());
        }
        return execution.execute(request, body);
      }
    };
    restTemplate.setInterceptors(Collections.singletonList(interceptor));
  }

}
