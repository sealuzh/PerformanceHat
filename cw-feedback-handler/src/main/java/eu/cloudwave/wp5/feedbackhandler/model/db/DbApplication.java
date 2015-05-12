package eu.cloudwave.wp5.feedbackhandler.model.db;

import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

/**
 * Represents applications that are registered to be used with the feedback handler.
 */
@Document(collection = DbTableNames.APPLICATIONS)
public interface DbApplication extends DbEntity {

  /**
   * Returns the id of the application.
   * 
   * @return the id of the application
   */
  public String getApplicationId();

  /**
   * Returns the authentication token of this application.
   * 
   * @return the authentication token
   */
  public String getAccessToken();

}
