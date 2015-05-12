package eu.cloudwave.wp5.feedbackhandler.cw.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CloudWave.IEventHandler;
import eu.cloudwave.wp5.feedbackhandler.repositories.MetricRepository;

@Service
public class CloudWaveEventHandler implements IEventHandler {

  @Autowired
  private MetricRepository metricRepository;

  @Override
  public void doEvent(final String event_json) {
    System.out.println("####### wohooo: " + event_json); // TODO cleanup: remove print
  }

}
