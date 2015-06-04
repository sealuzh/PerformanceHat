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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.core;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;

/**
 * Extends an {@link IFile} with domain related functionality.
 */
public interface FeedbackFile extends FeedbackResource, IFile {

  /**
   * Returns the {@link FeedbackProject} which contains this resource.
   * 
   * @return the {@link FeedbackProject} which contains this resource
   */
  public FeedbackProject getFeedbackProject();

  /**
   * Adds a marker to the file.
   * 
   * @param id
   *          the id of the marker
   * @param position
   *          the position of the marker in the source code
   * @param severity
   *          the severity of the marker
   * @param type
   *          the type of the marker
   * @param message
   *          the message of the marker
   * @throws CoreException
   *           if one or more markers could not be deleted
   */
  public void addMarker(final String id, MarkerPosition position, final int severity, final FeedbackMarkerType type, final String message, Map<String, Object> additionalAttributes)
      throws CoreException;

  /**
   * Adds a marker with the attributes specified in the given {@link MarkerSpecification} to the file.
   * 
   * @param specification
   *          {@link MarkerSpecification} containing the attributes of the marker to be created
   * @throws CoreException
   *           if the marker could not successfully be created
   */
  public void addMarker(MarkerSpecification specification) throws CoreException;

}
