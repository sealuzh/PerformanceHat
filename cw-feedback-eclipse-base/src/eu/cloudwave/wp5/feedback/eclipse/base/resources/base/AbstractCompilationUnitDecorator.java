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
package eu.cloudwave.wp5.feedback.eclipse.base.resources.base;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.CompletionRequestor;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.IBufferFactory;
import org.eclipse.jdt.core.ICodeCompletionRequestor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ICompletionRequestor;
import org.eclipse.jdt.core.IImportContainer;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IProblemRequestor;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.WorkingCopyOwner;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.text.edits.UndoEdit;

/**
 * An abstract base implementation for {@link ICompilationUnit} decorators. Delegates all methods to the decorated
 * {@link ICompilationUnit}.
 */
@SuppressWarnings("deprecation")
public abstract class AbstractCompilationUnitDecorator implements ICompilationUnit {

  protected final ICompilationUnit compilationUnit;

  public AbstractCompilationUnitDecorator(final ICompilationUnit compilationUnit) {
    this.compilationUnit = compilationUnit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement[] getChildren() throws JavaModelException {
    return compilationUnit.getChildren();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void codeComplete(final int offset, final ICodeCompletionRequestor requestor) throws JavaModelException {
    compilationUnit.codeComplete(offset, requestor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void copy(final IJavaElement container, final IJavaElement sibling, final String rename, final boolean replace, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.copy(container, sibling, rename, replace, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasChildren() throws JavaModelException {
    return compilationUnit.hasChildren();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType findPrimaryType() {
    return compilationUnit.findPrimaryType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
    return compilationUnit.getAdapter(adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getElementAt(final int position) throws JavaModelException {
    return compilationUnit.getElementAt(position);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() throws JavaModelException {
    return compilationUnit.getSource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void codeComplete(final int offset, final ICompletionRequestor requestor) throws JavaModelException {
    compilationUnit.codeComplete(offset, requestor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws JavaModelException {
    compilationUnit.close();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISourceRange getSourceRange() throws JavaModelException {
    return compilationUnit.getSourceRange();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICompilationUnit getWorkingCopy(final WorkingCopyOwner owner, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.getWorkingCopy(owner, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final boolean force, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.delete(force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commit(final boolean force, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.commit(force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UndoEdit applyTextEdit(final TextEdit edit, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.applyTextEdit(edit, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String findRecommendedLineSeparator() throws JavaModelException {
    return compilationUnit.findRecommendedLineSeparator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void codeComplete(final int offset, final CompletionRequestor requestor) throws JavaModelException {
    compilationUnit.codeComplete(offset, requestor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISourceRange getNameRange() throws JavaModelException {
    return compilationUnit.getNameRange();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void move(final IJavaElement container, final IJavaElement sibling, final String rename, final boolean replace, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.move(container, sibling, rename, replace, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBuffer getBuffer() throws JavaModelException {
    return compilationUnit.getBuffer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasUnsavedChanges() throws JavaModelException {
    return compilationUnit.hasUnsavedChanges();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void becomeWorkingCopy(final IProblemRequestor problemRequestor, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.becomeWorkingCopy(problemRequestor, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void codeComplete(final int offset, final CompletionRequestor requestor, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.codeComplete(offset, requestor, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
    compilationUnit.destroy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists() {
    return compilationUnit.exists();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isConsistent() throws JavaModelException {
    return compilationUnit.isConsistent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rename(final String name, final boolean replace, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.rename(name, replace, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement findSharedWorkingCopy(final IBufferFactory bufferFactory) {
    return compilationUnit.findSharedWorkingCopy(bufferFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOpen() {
    return compilationUnit.isOpen();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getAncestor(final int ancestorType) {
    return compilationUnit.getAncestor(ancestorType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void makeConsistent(final IProgressMonitor progress) throws JavaModelException {
    compilationUnit.makeConsistent(progress);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getOriginal(final IJavaElement workingCopyElement) {
    return compilationUnit.getOriginal(workingCopyElement);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void codeComplete(final int offset, final ICompletionRequestor requestor, final WorkingCopyOwner owner) throws JavaModelException {
    compilationUnit.codeComplete(offset, requestor, owner);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAttachedJavadoc(final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.getAttachedJavadoc(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void becomeWorkingCopy(final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.becomeWorkingCopy(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getOriginalElement() {
    return compilationUnit.getOriginalElement();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void open(final IProgressMonitor progress) throws JavaModelException {
    compilationUnit.open(progress);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void codeComplete(final int offset, final CompletionRequestor requestor, final WorkingCopyOwner owner) throws JavaModelException {
    compilationUnit.codeComplete(offset, requestor, owner);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getCorrespondingResource() throws JavaModelException {
    return compilationUnit.getCorrespondingResource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(final IProgressMonitor progress, final boolean force) throws JavaModelException {
    compilationUnit.save(progress, force);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commitWorkingCopy(final boolean force, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.commitWorkingCopy(force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getSharedWorkingCopy(final IProgressMonitor monitor, final IBufferFactory factory, final IProblemRequestor problemRequestor) throws JavaModelException {
    return compilationUnit.getSharedWorkingCopy(monitor, factory, problemRequestor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getElementName() {
    return compilationUnit.getElementName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getElementType() {
    return compilationUnit.getElementType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHandleIdentifier() {
    return compilationUnit.getHandleIdentifier();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void codeComplete(final int offset, final CompletionRequestor requestor, final WorkingCopyOwner owner, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.codeComplete(offset, requestor, owner, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IImportDeclaration createImport(final String name, final IJavaElement sibling, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.createImport(name, sibling, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaModel getJavaModel() {
    return compilationUnit.getJavaModel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaProject getJavaProject() {
    return compilationUnit.getJavaProject();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IOpenable getOpenable() {
    return compilationUnit.getOpenable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getParent() {
    return compilationUnit.getParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getPath() {
    return compilationUnit.getPath();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IImportDeclaration createImport(final String name, final IJavaElement sibling, final int flags, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.createImport(name, sibling, flags, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getWorkingCopy() throws JavaModelException {
    return compilationUnit.getWorkingCopy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getPrimaryElement() {
    return compilationUnit.getPrimaryElement();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement[] codeSelect(final int offset, final int length) throws JavaModelException {
    return compilationUnit.codeSelect(offset, length);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getResource() {
    return compilationUnit.getResource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement getWorkingCopy(final IProgressMonitor monitor, final IBufferFactory factory, final IProblemRequestor problemRequestor) throws JavaModelException {
    return compilationUnit.getWorkingCopy(monitor, factory, problemRequestor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISchedulingRule getSchedulingRule() {
    return compilationUnit.getSchedulingRule();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement[] codeSelect(final int offset, final int length, final WorkingCopyOwner owner) throws JavaModelException {
    return compilationUnit.codeSelect(offset, length, owner);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getUnderlyingResource() throws JavaModelException {
    return compilationUnit.getUnderlyingResource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReadOnly() {
    return compilationUnit.isReadOnly();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPackageDeclaration createPackageDeclaration(final String name, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.createPackageDeclaration(name, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isStructureKnown() throws JavaModelException {
    return compilationUnit.isStructureKnown();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isBasedOn(final IResource resource) {
    return compilationUnit.isBasedOn(resource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType createType(final String contents, final IJavaElement sibling, final boolean force, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.createType(contents, sibling, force, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMarker[] reconcile() throws JavaModelException {
    return compilationUnit.reconcile();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reconcile(final boolean forceProblemDetection, final IProgressMonitor monitor) throws JavaModelException {
    compilationUnit.reconcile(forceProblemDetection, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void discardWorkingCopy() throws JavaModelException {
    compilationUnit.discardWorkingCopy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IJavaElement[] findElements(final IJavaElement element) {
    return compilationUnit.findElements(element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICompilationUnit findWorkingCopy(final WorkingCopyOwner owner) {
    return compilationUnit.findWorkingCopy(owner);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType[] getAllTypes() throws JavaModelException {
    return compilationUnit.getAllTypes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IImportDeclaration getImport(final String name) {
    return compilationUnit.getImport(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IImportContainer getImportContainer() {
    return compilationUnit.getImportContainer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IImportDeclaration[] getImports() throws JavaModelException {
    return compilationUnit.getImports();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICompilationUnit getPrimary() {
    return compilationUnit.getPrimary();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WorkingCopyOwner getOwner() {
    return compilationUnit.getOwner();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPackageDeclaration getPackageDeclaration(final String name) {
    return compilationUnit.getPackageDeclaration(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPackageDeclaration[] getPackageDeclarations() throws JavaModelException {
    return compilationUnit.getPackageDeclarations();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getType(final String name) {
    return compilationUnit.getType(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType[] getTypes() throws JavaModelException {
    return compilationUnit.getTypes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICompilationUnit getWorkingCopy(final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.getWorkingCopy(monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ICompilationUnit getWorkingCopy(final WorkingCopyOwner owner, final IProblemRequestor problemRequestor, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.getWorkingCopy(owner, problemRequestor, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasResourceChanged() {
    return compilationUnit.hasResourceChanged();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWorkingCopy() {
    return compilationUnit.isWorkingCopy();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CompilationUnit reconcile(final int astLevel, final boolean forceProblemDetection, final WorkingCopyOwner owner, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.reconcile(astLevel, forceProblemDetection, owner, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CompilationUnit reconcile(final int astLevel, final boolean forceProblemDetection, final boolean enableStatementsRecovery, final WorkingCopyOwner owner, final IProgressMonitor monitor)
      throws JavaModelException {
    return compilationUnit.reconcile(astLevel, forceProblemDetection, enableStatementsRecovery, owner, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CompilationUnit reconcile(final int astLevel, final int reconcileFlags, final WorkingCopyOwner owner, final IProgressMonitor monitor) throws JavaModelException {
    return compilationUnit.reconcile(astLevel, reconcileFlags, owner, monitor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void restore() throws JavaModelException {
    compilationUnit.restore();
  }

}
