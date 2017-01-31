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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.Ids;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;

/**
 * Handler for the Propagate Feedback Context menu action
 */
public class ImpactPropagationHandler extends AbstractHandler {
	
  /**
   * {@inheritDoc}
   */
  @Override
  public Object execute(final ExecutionEvent event) throws ExecutionException {
	  new Job("Feedback propagator"){
		  
		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			  //Get the currently selected Text
			  ISelection sel = HandlerUtil.getCurrentSelection(event);
			  // Make sure their is a selection
			  if(!(sel instanceof ITextSelection)) return new Status(Status.ERROR,Ids.PLUGIN,"No Valid Selection");
			  final ITextSelection textSel = (ITextSelection)sel;
			  //get the current File
		      IFile file = getFileFromEditorInput(HandlerUtil.getActiveEditor(event).getEditorInput());
			  //fetch Performance representations
		      FeedbackJavaResourceFactory factory = PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
			  Optional<? extends FeedbackJavaFile> ojfFile = factory.create(file);  
			  if(!ojfFile.isPresent()) return new Status(Status.ERROR,Ids.PLUGIN,"Operation must be used on a Feedback enabled file");
			  FeedbackJavaFile jfFile = ojfFile.get();
			  //get Ast Root
			  Optional<CompilationUnit> astRoot = jfFile.getAstRoot();
			  if(!astRoot.isPresent()) return new Status(Status.ERROR,Ids.PLUGIN,"Could not find corresponding compilation unit");	
			  CompilationUnit unit = astRoot.get();
			  //Visit ast to discover clicked method
			  unit.accept(new ASTVisitor() {
				boolean done = false;
				@Override
				public boolean visit(MethodDeclaration node) {
					if(done) return false;
					int off = textSel.getOffset();
					//Does position match?
					if(node.getStartPosition() <= off && off <= node.getStartPosition()+node.getLength()){
						try {
							//Jep it does, lets Go
							done = true;
							ImpactPropagator.calculateImpact((IMember)node.resolveBinding().getJavaElement(), monitor);
						} catch (CoreException e) {
							throw new IllegalArgumentException(e);
						}
					}
					return false;
				} 
			  });
			 
			  return new Status(Status.OK,Ids.PLUGIN,"");
		}
		  
	  }.schedule();
	  return null;
  }
  
  //little helper to get IFile from IEditor
  public static IFile getFileFromEditorInput(IEditorInput input)
  {
    if (input == null)
      return null;

    if (input instanceof IFileEditorInput)
      return ((IFileEditorInput)input).getFile();

    return null;
  }


}
