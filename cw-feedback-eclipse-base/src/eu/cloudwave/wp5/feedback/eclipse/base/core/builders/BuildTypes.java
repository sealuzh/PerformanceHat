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
package eu.cloudwave.wp5.feedback.eclipse.base.core.builders;

import java.util.Map;

import org.eclipse.core.resources.IncrementalProjectBuilder;

import com.google.common.collect.Maps;

/**
 * Maps the build types (see {@link IncrementalProjectBuilder}) to a respective string representation.
 */
public class BuildTypes {

  private Map<Integer, String> map;

  public static BuildTypes INSTANCE = new BuildTypes();

  private BuildTypes() {}

  private void createMap() {
    this.map = Maps.newHashMap();
    this.map.put(IncrementalProjectBuilder.AUTO_BUILD, "AUTO BUILD");
    this.map.put(IncrementalProjectBuilder.INCREMENTAL_BUILD, "INCREMENTAL BUILD");
    this.map.put(IncrementalProjectBuilder.FULL_BUILD, "FULL BUILD");
    this.map.put(IncrementalProjectBuilder.CLEAN_BUILD, "CLEAN BUILD");
  }

  private Map<Integer, String> getMap() {
    if (map == null) {
      createMap();
    }
    return map;
  }

  public String get(final int kind) {
    return getMap().get(kind);
  }

}
