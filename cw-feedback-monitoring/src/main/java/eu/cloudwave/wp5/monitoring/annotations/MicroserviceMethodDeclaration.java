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

import eu.cloudwave.wp5.common.constants.Ids;

/**
 * Annotating a method with this interface indicates that the method is a microservice method that can be called from
 * other services and thus receive requests from another services. <br />
 * <br />
 * Important: the name of the annotation has to be equal to {@link Ids#MICROSERVICE_ENDPOINT_ANNOTATION}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MicroserviceMethodDeclaration {

  /**
   * Identifier of the service <br />
   * <br />
   * Important:
   * <ul>
   * <li>the name of the attribute (= 'identifier') has to be equal to
   * {@link Ids#MICROSERVICE_ENDPOINT_ANNOTATION_IDENTIFIER_ATTRIBUTE}</li>
   * <li>the default value is defined in {@link Ids#MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT}</li>
   * </ul>
   * 
   * @return the identifier of the declared service method
   */
  public String identifier() default Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT;

  /*
   * Microservice Endpoint
   */
  public String endpoint();

}
