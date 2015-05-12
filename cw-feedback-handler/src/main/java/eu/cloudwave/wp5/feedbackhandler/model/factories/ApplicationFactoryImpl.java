package eu.cloudwave.wp5.feedbackhandler.model.factories;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.impl.DbApplicationImpl;

/**
 * Implementation of {@link ApplicationFactory}.
 */
@Service
public class ApplicationFactoryImpl implements ApplicationFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public DbApplication create(final String applicationId) {
    final String accessToken = new BigInteger(130, new SecureRandom()).toString(32);
    return new DbApplicationImpl(applicationId, accessToken);
  }

}
