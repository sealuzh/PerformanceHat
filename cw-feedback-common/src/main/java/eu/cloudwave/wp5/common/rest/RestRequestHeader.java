package eu.cloudwave.wp5.common.rest;

public class RestRequestHeader {

  private String key;
  private String value;

  private RestRequestHeader(final String key, final String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public static final RestRequestHeader of(final String key, final String value) {
    return new RestRequestHeader(key, value);
  }

}
