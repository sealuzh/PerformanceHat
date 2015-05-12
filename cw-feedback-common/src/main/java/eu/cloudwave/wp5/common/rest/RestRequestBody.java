package eu.cloudwave.wp5.common.rest;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public final class RestRequestBody {

  private MultiValueMap<String, String> parameters;

  private RestRequestBody() {
    this.parameters = new LinkedMultiValueMap<String, String>();
  }

  public MultiValueMap<String, String> get() {
    return this.parameters;
  }

  private void addParameter(final String key, final String value) {
    parameters.add(key, value);
  }

  public RestRequestBody with(final String key, final String value) {
    parameters.add(key, value);
    return this;
  }

  public static RestRequestBodyBuilder of(final String key, final String value) {
    final RestRequestBodyBuilder requestBodyBuilder = new RestRequestBodyBuilder();
    requestBodyBuilder.addParameter(key, value);
    return requestBodyBuilder;
  }

  public static RestRequestBody of() {
    return new RestRequestBodyBuilder().create();
  }

  public static final class RestRequestBodyBuilder {

    private RestRequestBody requestBody;

    private RestRequestBodyBuilder() {
      this.requestBody = new RestRequestBody();
    }

    public RestRequestBodyBuilder and(final String key, final String value) {
      addParameter(key, value);
      return this;
    }

    public void addParameter(final String key, final String value) {
      this.requestBody.addParameter(key, value);
    }

    public RestRequestBody create() {
      return this.requestBody;
    }

  }

}
