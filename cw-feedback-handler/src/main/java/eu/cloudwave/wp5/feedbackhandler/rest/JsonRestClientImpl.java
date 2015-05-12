package eu.cloudwave.wp5.feedbackhandler.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.cloudwave.wp5.common.rest.RestRequestBody;
import eu.cloudwave.wp5.common.rest.RestRequestHeader;

/**
 * Implementation of {@link JsonRestClient}
 */
@Service
public class JsonRestClientImpl implements JsonRestClient {

  @Autowired
  private RestClient restClient;

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonNode get(final String url, final RestRequestHeader... requestHeaders) throws JsonProcessingException, IOException {
    final ResponseEntity<String> response = restClient.get(url, String.class, requestHeaders);
    return getJson(response);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonNode post(final String url, final RestRequestBody requestBody, final RestRequestHeader... requestHeaders) throws JsonProcessingException, IOException {
    final ResponseEntity<String> response = restClient.post(url, String.class, requestBody, requestHeaders);
    return getJson(response);
  }

  private JsonNode getJson(final ResponseEntity<String> response) throws JsonProcessingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readTree(response.getBody());
  }
}
