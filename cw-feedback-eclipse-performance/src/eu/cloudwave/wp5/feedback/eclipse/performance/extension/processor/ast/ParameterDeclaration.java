package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode representing a Method or Constructor Parameter
 * @author Markus Knecht
 *
 */
public class ParameterDeclaration extends AAstNode<org.eclipse.jdt.core.dom.SingleVariableDeclaration>{

	//this is the position of the Parameter in the current Method 
	private final int paramPos;
	
	ParameterDeclaration(int paramPos, org.eclipse.jdt.core.dom.SingleVariableDeclaration inner, AstContext ctx) {
		super(inner, ctx);
		this.paramPos = paramPos;
	}
	
	/**
	 * The position of the parameter in the Parameter list of the current Method
	 * @return the position as int
	 */
	public int getPosition(){
		return paramPos;
	}
}
