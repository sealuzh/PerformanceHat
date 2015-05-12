package eu.cloudwave.wp5.common.dto;

/**
 * A DTO to trasmit an access token.
 */
public class AccessTokenDto {

  private String accessToken;

  // default constructor is required for jackson deserialization
  public AccessTokenDto() {

  }

  public AccessTokenDto(final String accessToken) {
    this();
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

}
