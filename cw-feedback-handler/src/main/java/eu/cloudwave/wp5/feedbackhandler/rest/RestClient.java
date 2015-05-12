package eu.cloudwave.wp5.feedbackhandler.rest;

import org.springframework.http.ResponseEntity;

import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * Provides methods to perform requests to a REST API.
 */
public interface RestClient {

  /**
   * Perform a GET request.
   * 
   * @param url
   *          the URL of the request
   * @param responseType
   *          the type of the object in the response body
   * @param requestHeaders
   *          the headers of the request
   * @return the {@link ResponseEntity}
   */
  public <T> ResponseEntity<T> get(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders);

  /**
   * Perform a POST request.
   * 
   * @param url
   *          the URL of the request
   * @param responseType
   *          the type of the object in the response body
   * @param requestBody
   *          the body of the request
   * @param requestHeaders
   *          the headers of the request
   * @return the {@link ResponseEntity}
   */
  public <T> ResponseEntity<T> post(final String url, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders);

}
