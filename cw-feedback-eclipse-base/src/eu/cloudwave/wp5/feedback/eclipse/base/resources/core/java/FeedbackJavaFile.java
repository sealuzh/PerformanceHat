package eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackFile;

/**
 * Extends a {@link FeedbackFile} with Java related functionality.
 */
public interface FeedbackJavaFile extends FeedbackFile {

  /**
   * Returns the respective {@link ICompilationUnit} if the {@link IFile} is a Java source or binary file.
   * 
   * @return the respective {@link ICompilationUnit} of the current {@link IFile}
   */
  public Optional<? extends ICompilationUnit> getCompilationUnit();

  /**
   * Returns the root node of the Abstract Syntax Tree (AST) if the {@link IFile} is a Java source file.
   * 
   * @return the root AST node which is of type {@link CompilationUnit}
   */
  public Optional<CompilationUnit> getAstRoot();

}
