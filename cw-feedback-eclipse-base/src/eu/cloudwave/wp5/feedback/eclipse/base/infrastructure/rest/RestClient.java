package eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.rest;

import java.util.Map;

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
   *          the url of the request
   * @param responseType
   *          the type of object that is responded (deserialization from JSON)
   * @param requestHeaders
   *          the HTTP headers of the request
   * @return the deserialized object
   */
  public <T> T get(final String url, final Class<T> responseType, final RestRequestHeader... requestHeaders);

  /**
   * Perform a GET request.
   * 
   * @param url
   *          the url of the request
   * @param urlVariables
   *          the url variables (that are appended to the url)
   * @param responseType
   *          the type of object that is responded (deserialization from JSON)
   * @param requestHeaders
   *          the HTTP headers of the request
   * @return the deserialized object
   */
  public <T> T get(final String url, Map<String, String> urlVariables, final Class<T> responseType, final RestRequestHeader... requestHeaders);

  /**
   * Perform a POST request.
   * 
   * @param url
   *          the url of the request
   * @param responseType
   *          the type of object that is responded (deserialization from JSON)
   * @param requestHeaders
   *          the HTTP headers of the request
   * @return the deserialized object
   */
  public <T> T post(final String url, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders);

  /**
   * Perform a POST request.
   * 
   * @param url
   *          the url of the request
   * @param urlVariables
   *          the url variables (that are appended to the url)
   * @param responseType
   *          the type of object that is responded (deserialization from JSON)
   * @param requestHeaders
   *          the HTTP headers of the request
   * @return the deserialized object
   */
  public <T> T post(final String url, Map<String, String> urlVariables, final Class<T> responseType, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders);

}
