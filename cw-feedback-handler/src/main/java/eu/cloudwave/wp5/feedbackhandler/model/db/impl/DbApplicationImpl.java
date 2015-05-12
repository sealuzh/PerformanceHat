package eu.cloudwave.wp5.feedbackhandler.model.db.impl;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;

/**
 * Implementation of {@link DbApplication}.
 */
@Document(collection = DbTableNames.APPLICATIONS)
public class DbApplicationImpl implements DbApplication {

  @Id
  private ObjectId id;

  private String applicationId;
  private String accessToken;

  public DbApplicationImpl(final String applicationId, final String accessToken) {
    this.applicationId = applicationId;
    this.accessToken = accessToken;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getApplicationId() {
    return applicationId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAccessToken() {
    return accessToken;
  }

}
