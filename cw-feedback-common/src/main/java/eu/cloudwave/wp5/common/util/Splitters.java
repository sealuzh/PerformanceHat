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
package eu.cloudwave.wp5.common.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class Splitters {

  private static final String COMMA = ",";

  public static Iterable<String> onComma(final String text) {
    return Splitter.on(COMMA).trimResults().omitEmptyStrings().split(text);
  }

  public static String[] arrayOnComma(final String text) {
    return Iterables.toArray(onComma(text), String.class);
  }

  /**
   * Removes ["content"] brackets
   * 
   * @param text
   * @return content in between brackets
   */
  public static String inBetweenBrackets(final String text) {
    if (text != null && text.contains("\"")) {
      return text.split("\"")[1];
    }
    else if (text != null && text.equals("[ ]")) {
      return null;
    }
    else {
      return text;
    }
  }

  private Splitters() {}

}
