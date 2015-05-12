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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;

/**
 * Extends an {@link IResourceDelta} with domain related functionality.
 */
public interface FeedbackResourceDelta extends IResourceDelta {

  /**
   * Returns all changed files of the sub-tree of the current resource.
   * 
   * @return a {@link Set} containing all affected files
   */
  public Set<IFile> getChangedFiles();

}
