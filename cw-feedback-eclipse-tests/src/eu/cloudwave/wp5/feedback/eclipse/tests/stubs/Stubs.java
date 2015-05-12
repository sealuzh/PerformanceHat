package eu.cloudwave.wp5.feedback.eclipse.tests.stubs;

import java.util.List;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.dto.AggregatedProcedureMetricsDto;
import eu.cloudwave.wp5.common.dto.newrelic.MethodInfoSummarized;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

/**
 * Provides stubs for testing.
 */
public class Stubs {

  public static final MethodInfoSummarized METHOD_INFO_SUMMARIZED = new MethodInfoSummarized(1.75, 13);

  public static final String CLASS__SAMPLE_SERVICE = "eu.cloudwave.wp5.samples.fixture.SampleService";
  public static final String METHOD__PRINT = "print";
  public static final List<String> METHOD__PRINT_PARAMS = Lists.newArrayList("java.lang.String");

  public static final Procedure PROCEDURE = new ProcedureImpl(CLASS__SAMPLE_SERVICE, METHOD__PRINT, ProcedureKind.METHOD, METHOD__PRINT_PARAMS, Lists.newArrayList());
  public static final AggregatedProcedureMetricsDto PROCECDURE_DTO = new AggregatedProcedureMetricsDto(PROCEDURE, 2500, 2);
  public static final AggregatedProcedureMetricsDto[] HOTSPOTS = { PROCECDURE_DTO };

}
