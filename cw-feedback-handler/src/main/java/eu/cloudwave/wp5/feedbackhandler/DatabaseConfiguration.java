package eu.cloudwave.wp5.feedbackhandler;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Configurations for the MongoDB.
 */
@Configuration
@EnableMongoRepositories
public class DatabaseConfiguration extends AbstractMongoConfiguration {

  private static final String DB_NAME = "feedback-handler";
  private static final String REPO_BASE_PACKAGE = "eu.cloudwave.wp5.feedbackhandler.repositories";

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getDatabaseName() {
    return DB_NAME;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Mongo mongo() throws Exception {
    return new MongoClient();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getMappingBasePackage() {
    return REPO_BASE_PACKAGE;
  }

}
