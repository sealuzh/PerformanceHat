package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagCreator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

public class ImplementorTagLookupHelper {
	  public static Collection<Object> getSingleSubclassTags(IMethodBinding method, AstContext ctx, String tagName){


			ITypeBinding classBind = method.getDeclaringClass();
			IJavaElement elem = classBind.getJavaElement();
			if(!elem.getJavaProject().equals(ctx.getProject().getJavaProject())) return Collections.emptyList();
		  	//Get the Creator and Consumer
			
			
			TagProvider prov = ctx.getTagProvider();
			String methodName = method.getName();
			String[] argumentTypes = Arrays.stream(method.getParameterTypes()).map(pt -> pt.getName()).collect(Collectors.toList()).toArray(new String[]{});
			
			if(elem instanceof IType){
				IType cType = (IType)elem;
		        try {
					ITypeHierarchy typeHierarchy = cType.newTypeHierarchy(ctx.getProject().getJavaProject(), new NullProgressMonitor());
					IType[] potentialImplementors = typeHierarchy.getAllSubtypes(cType);
					//todo: for all tags not only these
					MethodLocator source = null;
					for(IType subT: potentialImplementors){
						MethodLocator src = new MethodLocator(subT.getFullyQualifiedName(), methodName, argumentTypes);
						if(!prov.getTagsForMethod(src, tagName).isEmpty()){
							if(source != null) {
								source = null;
								break;
							} else {
								source = src;
							}
						}
					}
					
					if(source != null){
						return prov.getTagsForMethod(source, tagName);
					}
					
		        } catch (JavaModelException e) {
					e.printStackTrace();
		        }
			}
	        return Collections.emptyList();
	  }
}
