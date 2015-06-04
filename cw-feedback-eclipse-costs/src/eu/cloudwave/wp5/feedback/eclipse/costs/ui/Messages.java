/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.costs.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

  private static final String BUNDLE_NAME = "eu.cloudwave.wp5.feedback.eclipse.costs.ui.messages"; //$NON-NLS-1$

  public static String PROPERTY_PAGES__COSTS__DESCRIPTION;

  /* Identifier Group */
  public static String PROPERTIES__GROUP__MICROSERVICE;

  public static String PROPERTIES__MICROSERVICE_IDENTIFIER__KEY;
  public static String PROPERTIES__MICROSERVICE_IDENTIFIER__LABEL;

  /* Features Group */
  public static String PROPERTIES__GROUP__FEATURES;

  public static String PROPERTIES__FEATURE__HOVER_EXISTING_INVOCATIONS__ISACTIVATED__KEY;
  public static String PROPERTIES__FEATURE__HOVER_EXISTING_INVOCATIONS__ISACTIVATED__LABEL;

  public static String PROPERTIES__FEATURE__HOVER_NEW_INVOCATIONS__ISACTIVATED__KEY;
  public static String PROPERTIES__FEATURE__HOVER_NEW_INVOCATIONS__ISACTIVATED__LABEL;

  public static String PROPERTIES__FEATURE__HOVER_METHOD_DECLARATION__ISACTIVATED__KEY;
  public static String PROPERTIES__FEATURE__HOVER_METHOD_DECLARATION__ISACTIVATED__LABEL;

  /* Time Group */
  public static String PROPERTIES__GROUP__TIME;

  public static String PROPERTIES__TIME__AGGREGATION__INTERVAL__KEY;
  public static String PROPERTIES__TIME__AGGREGATION__INTERVAL__LABEL;
  public static String PROPERTIES__TIME__AGGREGATION__INTERVAL__DEFAULT;

  public static String PROPERTIES__TIME__FROM__ISACTIVATED__KEY;
  public static String PROPERTIES__TIME__FROM__ISACTIVATED__LABEL;
  public static String PROPERTIES__TIME__FROM__KEY;
  public static String PROPERTIES__TIME__FROM__LABEL;

  public static String PROPERTIES__TIME__TO__ISACTIVATED__KEY;
  public static String PROPERTIES__TIME__TO__ISACTIVATED__LABEL;
  public static String PROPERTIES__TIME__TO__KEY;
  public static String PROPERTIES__TIME__TO__LABEL;

  /* Formatting Group */
  public static String PROPERTIES__GROUP__FORMATTING;
  public static String PROPERTIES__FORMATTING__ANNOTATION_INDENT__KEY;
  public static String PROPERTIES__FORMATTING__ANNOTATION_INDENT__LABEL;
  public static String PROPERTIES__FORMATTING__ANNOTATION_INDENT__DEFAULT;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {}
}
