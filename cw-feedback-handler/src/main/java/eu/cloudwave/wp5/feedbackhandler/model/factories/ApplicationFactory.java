package eu.cloudwave.wp5.feedbackhandler.model.factories;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbApplicationImpl;

/**
 * A factory for {@link DbApplication}'s.
 */
public interface ApplicationFactory {

  /**
   * Creates a new {@link DbApplicationImpl}.
   * 
   * @param applicationId
   *          the id of the application
   * 
   * @return the created {@link DbApplicationImpl}
   */
  public DbApplication create(final String applicationId);

}
