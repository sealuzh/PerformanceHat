package eu.cloudwave.wp5.feedbackhandler.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbApplicationImpl;

/**
 * Implementation of {@link ApplicationRepositoryCustom}. It is named according to the naming conventions:
 * <ul>
 * <li>http://www.javabeat.net/spring-data-custom-repository/</li>
 * <li>http://stackoverflow.com/a/20789040/3721915</li>
 * </ul>
 */
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {

  private static final String APPLICATION_ID = "applicationId";

  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * {@inheritDoc}
   */
  @Override
  public DbApplication findOne(final String applicationId) {
    final Criteria criteria = new Criteria(APPLICATION_ID).is(applicationId);
    return mongoTemplate.findOne(new Query(criteria), DbApplicationImpl.class);
  }

}
