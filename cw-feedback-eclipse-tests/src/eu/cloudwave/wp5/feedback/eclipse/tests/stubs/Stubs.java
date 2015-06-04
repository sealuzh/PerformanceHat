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
