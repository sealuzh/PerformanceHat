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
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.base.AbstractFileDecorator;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerType;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.FeedbackMarkerTypeSerializer;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerAttributes;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerPosition;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.markers.MarkerSpecification;

/**
 * Implementation of {@link FeedbackFile}. Acts as a decorator of the wrapped {@link IFile}.
 */
public class FeedbackFileImpl extends AbstractFileDecorator implements FeedbackFile {

  private final FeedbackResourceExtension feedbackResourceExtension;

  private final FeedbackResourceFactory feedbackResourceFactory;

  /**
   * Constructor
   * 
   * @param file
   *          {@link IFile}
   * @param extensionFactory
   *          {@link FeedbackResourceExtensionFactory}
   * @param resourceFactory
   *          {@link FeedbackResourceFactory}
   */
  protected FeedbackFileImpl(final IFile file, final FeedbackResourceExtensionFactory extensionFactory, final FeedbackResourceFactory resourceFactory) {
    super(file);
    this.feedbackResourceExtension = extensionFactory.create(file);
    this.feedbackResourceFactory = resourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public FeedbackProject getFeedbackProject() {
    return this.feedbackResourceFactory.create(getProject()).get();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteMarkers() throws CoreException {
    this.feedbackResourceExtension.deleteMarkers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMarker(final String id, final MarkerPosition position, final int severity, final FeedbackMarkerType type, final String message, final Map<String, Object> additionalAttributes)
      throws CoreException {
    final IMarker marker = file.createMarker(id);
    marker.setAttribute(IMarker.LINE_NUMBER, position.getLine());
    marker.setAttribute(IMarker.CHAR_START, position.getStart());
    marker.setAttribute(IMarker.CHAR_END, position.getEnd());
    marker.setAttribute(IMarker.SEVERITY, severity);
    marker.setAttribute(MarkerAttributes.TYPE, new FeedbackMarkerTypeSerializer().serialize(type));
    marker.setAttribute(IMarker.MESSAGE, message);
    if (additionalAttributes != null) {
      for (final Map.Entry<String, Object> attribute : additionalAttributes.entrySet()) {
        marker.setAttribute(attribute.getKey(), attribute.getValue());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMarker(final MarkerSpecification specification) throws CoreException {
    addMarker(specification.getId(), specification.getPosition(), specification.getSeverity(), specification.getType(), specification.getMessage(), specification.getAdditionalAttributes());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMarker> findMarkers(final String id) {
    return this.feedbackResourceExtension.findMarkers(id);
  }

}
