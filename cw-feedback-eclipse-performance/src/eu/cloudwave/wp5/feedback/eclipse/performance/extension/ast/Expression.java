package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public interface Expression extends IAstNode {
	public static Expression fromEclipseAstNode(org.eclipse.jdt.core.dom.Expression expr, ProgrammMarkerContext ctx){
		if(expr instanceof SimpleName){
			//todo: Should we provide node ore resolve to localVar/Param/Whatever
			throw new NotImplementedException("no AstNode for simple name expressions created yet");
		} else if(expr instanceof MethodInvocation){
			return new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation((MethodInvocation)expr, ctx);
		} else if(expr instanceof ClassInstanceCreation){
			return new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.ClassInstanceCreation((ClassInstanceCreation)expr, ctx);
		}
		return null;
	}
}
