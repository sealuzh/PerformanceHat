package eu.cloudwave.wp5.feedbackhandler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cloudwave.wp5.common.dto.model.MetricContainingProcedureExecutionDto;
import eu.cloudwave.wp5.common.dto.model.ProcedureExecutionDto;
import eu.cloudwave.wp5.common.model.ProcedureExecutionMetric;
import eu.cloudwave.wp5.feedbackhandler.cw.converter.CwMetricConverter;
import eu.cloudwave.wp5.feedbackhandler.cw.internal.CwMode;
import eu.cloudwave.wp5.feedbackhandler.cw.wrapper.CloudWaveWrapper;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbApplication;
import eu.cloudwave.wp5.feedbackhandler.model.db.DbProcedureExecution;
import eu.cloudwave.wp5.feedbackhandler.model.impl.converter.DbConverter;
import eu.cloudwave.wp5.feedbackhandler.repositories.MetricRepository;
import eu.cloudwave.wp5.feedbackhandler.repositories.ProcedureExecutionRepository;

/**
 * Implementation of {@link MonitoringDataService}.
 */
@Service
public class MonitoringDataServiceImpl implements MonitoringDataService {

  @Autowired
  private DbConverter modelConverter;

  @Autowired
  private ProcedureExecutionRepository procedureExecutionRepository;

  @Autowired
  private MetricRepository metricRepository;

  @Autowired
  private CloudWaveWrapper cloudWaveWrapper;

  @Autowired
  private CwMetricConverter metricConverter;

  /**
   * {@inheritDoc}
   */
  @Override
  public void persist(final DbApplication application, final MetricContainingProcedureExecutionDto procedureExecution) {
    persistSubTree(application, procedureExecution, null);
  }

  private void persistSubTree(final DbApplication application, final MetricContainingProcedureExecutionDto procedureExecutionDto, final DbProcedureExecution caller) {
    final DbProcedureExecution procedureExecution = persistProcedureExecution(application, procedureExecutionDto, caller);
    for (final ProcedureExecutionDto calleeDto : procedureExecutionDto.getCallees()) {
      persistSubTree(application, (MetricContainingProcedureExecutionDto) calleeDto, procedureExecution);
    }
  }

  private DbProcedureExecution persistProcedureExecution(final DbApplication application, final MetricContainingProcedureExecutionDto procedureExecutionDto, final DbProcedureExecution caller) {
    DbProcedureExecution procedureExecution = modelConverter.convert(procedureExecutionDto, application);
    procedureExecution.setCaller(caller);
    procedureExecution = procedureExecutionRepository.save(procedureExecution);
    for (final ProcedureExecutionMetric metric : procedureExecutionDto.getMetrics()) {
      // TODO cleanup: this service should work independently of CloudWave
      if (CwMode.isActive()) {
        cloudWaveWrapper.recordMetric(metricConverter.convert(metric));
        // save metric in db anyway
        metricRepository.save(modelConverter.convert(metric, procedureExecution, application));
      }
      else {
        metricRepository.save(modelConverter.convert(metric, procedureExecution, application));
      }
    }
    return procedureExecution;
  }

}
