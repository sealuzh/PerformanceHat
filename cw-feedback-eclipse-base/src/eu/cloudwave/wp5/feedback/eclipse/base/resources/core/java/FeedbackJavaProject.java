package eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * Extends a {@link FeedbackProject} with Java related functionality.
 */
public interface FeedbackJavaProject extends FeedbackProject {

  /**
   * Checks whether the {@link IProject} has Java nature and whether the Java nature is enabled.
   * 
   * @return true if the {@link IProject} has Java nature and it is enabled, false otherwise
   */
  public boolean isJavaNatureEnabled();

  /**
   * Returns all Java source files as. Only source files are considered, binary files are NOT included.
   * 
   * @return a {@link Set} containing all {@link FeedbackJavaFile}'s
   */
  public Set<FeedbackJavaFile> getJavaSourceFiles();

  /**
   * Returns the Java source file with the given name.
   * 
   * @param name
   *          name of the Java source file
   * @return the Java source file with the given name or {@link Optional#absent()}, if no Java source file with the
   *         given name exits
   */
  public Optional<FeedbackJavaFile> getJavaSourceFile(final String name);

  /**
   * Returns the Java project corresponding to the given project.
   * 
   * @return the {@link IJavaProject} corresponding to the given project
   */
  public IJavaProject getJavaProject();

}
