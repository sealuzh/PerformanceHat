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
 * Utility methods and constants related to URL's.
 */
public class Urls {

  private static final String SLASH = "/";

  /*
   * New Relic
   */
  public static final String NEW_RELIC__BASE = "newrelic";
  private static final String NEW_RELIC__QUALIFIER = NEW_RELIC__BASE + SLASH;
  public static final String NEW_RELIC__SUMMARIZE = NEW_RELIC__QUALIFIER + "summarize";

  /*
   * Monitoring
   */
  private static final String MONITORING__BASE = "monitoring";
  private static final String MONITORING__QUALIFIER = MONITORING__BASE + SLASH;

  public static final String MONITORING__REGISTER = MONITORING__QUALIFIER + "register";
  public static final String MONITORING__DATA = MONITORING__QUALIFIER + "data";

  /*
   * Analysis
   */
  private static final String ANALYSIS__BASE = "analysis";
  private static final String ANALYSIS__QUALIFIER = ANALYSIS__BASE + SLASH;

  public static final String ANALYIS__HOTSPOTS = ANALYSIS__QUALIFIER + "hotspots";
  public static final String ANALYIS__PROCEDURE = ANALYSIS__QUALIFIER + "procedure";
  public static final String ANALYIS__AVG_EXEC_TIME = ANALYSIS__QUALIFIER + "avgexectime";
  public static final String ANALYIS__COLLECTION_SIZE = ANALYSIS__QUALIFIER + "collectionsize";

  /*
   * Cost
   */
  private static final String COST__BASE = "cost";
  private static final String COST__QUALIFIER = COST__BASE + SLASH;
  private static final String COST__FILTER__QUALIFIER = COST__QUALIFIER + "filter" + SLASH;

  public static final String COST__ALL = COST__QUALIFIER + "all";
  public static final String COST__FILTER__CALLEE = COST__FILTER__QUALIFIER + "callee";
  public static final String COST__FILTER__CALLER = COST__FILTER__QUALIFIER + "caller";

  /*
   * Plots
   */
  private static final String PLOTS__BASE = "plots";
  private static final String PLOTS__QUALIFIER = PLOTS__BASE + SLASH;

  public static final String PLOTS_PROCEDURE = PLOTS__QUALIFIER + "procedure";

  /**
   * Concatenates the given root URL with the given URL fragment.
   * 
   * @param rootUrl
   *          the root URL
   * @param urlFragment
   *          the url fragment to be appended to the root URL
   * @return the concatenated URL
   */
  public static String concatenate(final String rootUrl, final String urlFragment) {
    final String rootWithSlash = rootUrl.endsWith(SLASH) ? rootUrl : rootUrl + SLASH;
    return rootWithSlash + urlFragment;
  }

  private Urls() {}

}
