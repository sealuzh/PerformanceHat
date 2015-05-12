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

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.error.RequestException;
import eu.cloudwave.wp5.feedback.eclipse.base.core.builders.participants.FeedbackBuilderParticipant;
import eu.cloudwave.wp5.feedback.eclipse.base.core.feedbackhandler.RequestExceptionHandler;
import eu.cloudwave.wp5.feedback.eclipse.base.infrastructure.logging.Logger;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceDelta;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;

/**
 * A Builder for the feedback plugin. Delegates the Work to a list of registered {@link FeedbackBuilderParticipant}'s.
 */
public abstract class FeedbackBuilder extends IncrementalProjectBuilder {

  public FeedbackBuilder() {
    System.out.println("Abstract FeedbackBuilder");
  }

  /*
   * Returns implementation of FeedbackJavaResourceFactory (each plugin can use its own dependency injector in the
   * FeedbackBuilder subclass)
   */
  protected abstract FeedbackJavaResourceFactory getFeedbackJavaResourceFactory();

  /*
   * Returns implementation of FeedbackCleaner (each plugin can use its own dependency injector in the FeedbackBuilder
   * subclass)
   */
  protected abstract FeedbackCleaner getFeedbackCleaner();

  /*
   * Returns list of build participants, has to be implemented in the concrete builder (e.g. PerformanceBuilder,
   * CostBuilder)
   */
  protected abstract List<FeedbackBuilderParticipant> getParticipants();

  /**
   * {@inheritDoc}
   */
  @Override
  protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor) throws CoreException {
    try {
      final Optional<? extends FeedbackJavaProject> javaProjectOptional = this.getFeedbackJavaResourceFactory().create(getProject());
      if (javaProjectOptional.isPresent()) {
        final FeedbackJavaProject project = javaProjectOptional.get();
        Logger.print(String.format("Triggered %S.", BuildTypes.INSTANCE.get(kind)));
        if (kind == IncrementalProjectBuilder.FULL_BUILD || kind == IncrementalProjectBuilder.CLEAN_BUILD) {
          fullBuild(project);
        }
        else {
          incrementalBuild(project);
        }
      }
    }
    catch (final RequestException e) {
      new RequestExceptionHandler().handle(getProject(), e);
    }
    return null;
  }

  /**
   * All resources in the project are built (no delta is available).
   * 
   * @throws CoreException
   *           if markers could not be correctly added or removed
   */
  private void fullBuild(final FeedbackJavaProject project) throws CoreException {
    System.out.println("fullBuild");
    this.getFeedbackCleaner().cleanAll(project);
    for (final FeedbackBuilderParticipant participant : this.getParticipants()) {
      participant.build(project, project.getJavaSourceFiles());
    }
  }

  /**
   * Only changed resources should be built (a delta is available).
   * 
   * @throws CoreException
   *           if markers could not be correctly added or removed
   */
  private void incrementalBuild(final FeedbackJavaProject project) throws CoreException {
    System.out.println("incrementalBuild");
    final IResourceDelta resourceDelta = getDelta(getProject());
    final Optional<? extends FeedbackJavaResourceDelta> feedbackDeltaOptional = this.getFeedbackJavaResourceFactory().create(resourceDelta);

    if (feedbackDeltaOptional.isPresent()) {
      final FeedbackJavaResourceDelta delta = feedbackDeltaOptional.get();
      this.getFeedbackCleaner().cleanDelta(delta);
      for (final FeedbackBuilderParticipant participant : this.getParticipants()) {
        participant.build(project, feedbackDeltaOptional.get().getChangedJavaFiles());
      }
    }
  }
}
