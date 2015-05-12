package eu.cloudwave.wp5.feedbackhandler.messages;

import java.util.ResourceBundle;

public class Messages {

  public static final String ERRORS__NEW_RELIC__INVALID_API_KEY = getMessage("errors.new_relic.bad_api_key");

  private static String getMessage(final String key) {
    return ResourceBundle.getBundle("messages").getString(key);
  }

}
