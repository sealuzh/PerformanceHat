/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
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
package eu.cloudwave.wp5.feedbackhandler.cw.wrapper;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CloudWave.CloudWave;
import CloudWave.CloudWaveException;
import CloudWave.MetricEventSource;
import CloudWave.MetricEventType;
import eu.cloudwave.wp5.feedbackhandler.cw.internal.CwMode;
import eu.cloudwave.wp5.feedbackhandler.cw.model.CwMetric;

/**
 * Implementation of {@link CloudWaveWrapper}.
 */
@Service
public class CloudWaveWrapperImpl implements CloudWaveWrapper {

  private static final String SEPARATOR = "$";
  private static final String MODE_INACTIVE_ERROR_MSG = "CloudWaveCode has been called although CloudWave-Mode is inactive";

  @Autowired
  CloudWaveEventHandler eventHandler;

  private Logger cwLogger = LogManager.getLogger(getClass());

  private static boolean isLoaded = false;

  @PostConstruct
  void init() {
    if (CwMode.isActive()) {
      if (!isLoaded) {
        try {
          CloudWave.getInstance().init();
        }
        catch (final CloudWaveException e) {
          e.printStackTrace();
        }
        /*
         * CloudWave.getInstance().setEventHandler(eventHandler);
         * System.out.println("############# Subscribe to event"); long id =
         * CloudWave.getInstance().subscribe("FDDExecutionTimes"); System.out.println("############# Subscription ID: "
         * + id);
         */
        isLoaded = true;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean recordMetric(final CwMetric metric) {
    return recordMetric(metric, "");
  }

  @Override
  public boolean recordMetric(final CwMetric metric, final String additionalData) {
    if (!CwMode.isActive()) {
      cwLogger.warn(MODE_INACTIVE_ERROR_MSG);
      return false;
    }

    // only send metric if it's greater than 0 to avoid overhead
    if (metric.getValue().intValue() <= 0) {
      return false;
    }

    try {
      //@formatter:off 
      // #: Metric Interface
      /*
      #          ./send_metric m i 123456 a Myname Mytps g Mymetadata
      #
      #                        ^ ^    ^   ^    ^     ^   ^     ^
      #                        | |    |   |    |     |   |     |
      # parameters             | |    |   |    |     |   |     |
      #                        | |    |   |    |     |   |     |
      # measure or event ------+ |    |   |    |     |   |     |
      # Integer/Double or Char --+    |   |    |     |   |     |
      # The value to be sent ---------+   |    |     |   |     |
      # App or Vm ------------------------+    |     |   |     |
      # The name string to be sent ------------+     |   |     |
      # The units string describing value -----------+   |     |
      # Gauge/Cumulative/Delta --------------------------+     |
      # Metadata string to be sent with packet ----------------+
      */
      //@formatter:on

      final String metadata = String.format("{ \"data\" : \"%s\"}", metric.getData() + SEPARATOR + additionalData);

      CloudWave.getInstance().recordMetric(MetricEventSource.Application, metric.getName(), metadata, metric.getUnit(), MetricEventType.Gauge, metric.getValue().longValue());

      // TODO cleanup: remove print
      System.out.println(String.format("#### Metric %s, %s, %s sent", metric.getName(), metadata, metric.getValue().toString()));

      return true;
    }
    catch (final CloudWaveException e) {
      System.err.println("#### record metric failed ####");
      return false;
    }
  }

}
