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
package eu.cloudwave.wp5.common.constants;

/**
 * Holds all ID's that are referenced in the Java code. Aggregating all the ID's here prohibits them from being spread
 * over multiple source code files.
 */
public class Ids {

  public static final String MICROSERVICE_CLIENT_REQUEST_ANNOTATION = "MicroserviceClientMethodDeclaration";
  public static final String MICROSERVICE_CLIENT_REQUEST_ANNOTATION_IDENTIFIER = "eu.cloudwave.wp5.monitoring.annotations." + MICROSERVICE_CLIENT_REQUEST_ANNOTATION;

  public static final String MICROSERVICE_ENDPOINT_ANNOTATION = "MicroserviceMethodDeclaration";

  public static final String MICROSERVICE_IDENTIFIER_LABEL = "microservice.identifier";

  public static final String MICROSERVICE_CLIENT_REQUEST_ANNOTATION_FROM_ATTRIBUTE = "caller";
  public static final String MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_ATTRIBUTE = "callee";

  public static final String MICROSERVICE_ENDPOINT_ANNOTATION_IDENTIFIER_ATTRIBUTE = "identifier";

  public static final String MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT = "Unknown";
}
