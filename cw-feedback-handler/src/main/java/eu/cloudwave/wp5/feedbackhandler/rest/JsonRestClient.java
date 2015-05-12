package eu.cloudwave.wp5.feedbackhandler.rest;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * A REST client that returns the responses as {@link JsonNode}.
 */
public interface JsonRestClient {

  /**
   * Perform a GET request.
   * 
   * @param url
   *          the URL of the request
   * @param requestHeaders
   *          the headers of the request
   * @return the response as {@link JsonNode}
   * @throws JsonProcessingException
   * @throws IOException
   */
  public JsonNode get(final String url, final RestRequestHeader... requestHeaders) throws JsonProcessingException, IOException;

  /**
   * Perform a POST request.
   * 
   * @param url
   *          the URL of the request
   * @param requestBody
   *          the body of the request
   * @param requestHeaders
   *          the headers of the request
   * @return the {@link ResponseEntity}
   * @throws JsonProcessingException
   * @throws IOException
   */
  public JsonNode post(final String url, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) throws JsonProcessingException, IOException;

}
