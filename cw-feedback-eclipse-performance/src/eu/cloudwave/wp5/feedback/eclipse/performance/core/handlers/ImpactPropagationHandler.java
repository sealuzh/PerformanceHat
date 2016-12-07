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
package eu.cloudwave.wp5.feedback.eclipse.performance.core.handlers;

import java.net.URI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Toggles the project nature for this plug-in.
 */
public class ImpactPropagationHandler extends AbstractHandler {


  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(final ExecutionEvent event) throws ExecutionException {
	  
	  ISelection sel = HandlerUtil.getCurrentSelection(event);
	  if(!(sel instanceof ITextSelection)) return null;
	  final ITextSelection textSel = (ITextSelection)sel;
      IFile file = getFileFromEditorInput(HandlerUtil.getActiveEditor(event).getEditorInput());
	  FeedbackJavaResourceFactory factory = PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
	  Optional<? extends FeedbackJavaFile> ojfFile = factory.create(file);  
	  if(!ojfFile.isPresent())  return null;
	  FeedbackJavaFile jfFile = ojfFile.get();
	  Optional<CompilationUnit> astRoot = jfFile.getAstRoot();
	  if(!astRoot.isPresent()) return null;	
	  CompilationUnit unit = astRoot.get();
	  unit.accept(new ASTVisitor() {
		@Override
		public boolean visit(MethodDeclaration node) {
			int off = textSel.getOffset();
			if(node.getStartPosition() <= off && off <= node.getStartPosition()+node.getLength()){
				try {
					ImpactPropagator.calculateImpact((IMember)node.resolveBinding().getJavaElement());
				} catch (CoreException e) {
					throw new IllegalArgumentException(e);
				}
			}
			return false;
		} 
	  });
	  
	 	        
	  return null;
  }
  
  public static IFile getFileFromEditorInput(IEditorInput input)
  {
    if (input == null)
      return null;

    if (input instanceof IFileEditorInput)
      return ((IFileEditorInput)input).getFile();

    return null;
  }


}
