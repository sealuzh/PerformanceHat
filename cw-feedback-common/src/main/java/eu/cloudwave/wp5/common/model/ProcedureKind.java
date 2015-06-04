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
package eu.cloudwave.wp5.common.model;

/**
 * Represents the type of a procedure. A procedure can either be a (non-static) method, a static method or a
 * constructor.
 */
public enum ProcedureKind {

  METHOD,
  STATIC_METHOD,
  CONSTRUCTOR,
  UNKNOWN;

  public static ProcedureKind of(final String textualKind) {
    for (final ProcedureKind kind : ProcedureKind.values()) {
      if (textualKind.toUpperCase().equals(kind.toString())) {
        return kind;
      }
    }
    return UNKNOWN;
  }

}
