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
package eu.cloudwave.wp5.monitoring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotating a method with this interface indicates that the developer is interested in its performance.
 * 
 * Using this custom annotation decouples applications that make use of the feedback handler from the New Relic API
 * (newrelic-api.jar).
 * 
 * In order to enable New Relic to work with the interface, add an appropriate class_transformer information in
 * newrelic.yml.
 * 
 * More info: https://docs.newrelic.com/docs/agents/java-agent/custom-instrumentation/java-instrumentation-annotation
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Trace {

  public String metricName() default "";

  public boolean dispatcher() default false;

  public String tracerFactoryName() default "";

}
