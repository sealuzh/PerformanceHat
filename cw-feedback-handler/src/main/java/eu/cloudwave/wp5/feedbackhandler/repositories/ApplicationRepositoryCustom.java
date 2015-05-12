package eu.cloudwave.wp5.feedbackhandler.repositories;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * This class extends the default spring repository with custom methods. It has to be named according to the naming
 * convention (see http://www.javabeat.net/spring-data-custom-repository/).
 */
public interface ApplicationRepositoryCustom {

  /**
   * Finds the application with the given id.
   * 
   * @param applicationId
   *          the application id
   * @return the application with the given id
   */
  public DbApplication findOne(String applicationId);

}
