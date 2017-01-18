package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import org.eclipse.jdt.core.dom.CompilationUnit;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

public class AstRoot extends AAstNode<CompilationUnit> {

	AstRoot(CompilationUnit inner, AstContext ctx) {
		super(inner, ctx);
	}

}
