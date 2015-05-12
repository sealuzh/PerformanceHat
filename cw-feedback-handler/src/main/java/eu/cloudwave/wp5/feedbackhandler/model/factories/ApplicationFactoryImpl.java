/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
