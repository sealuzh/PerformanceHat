package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class For extends AAstNode<org.eclipse.jdt.core.dom.ForStatement> implements Loop {
	
	public For(org.eclipse.jdt.core.dom.ForStatement forStatement, ProgrammMarkerContext ctx) {
		super(forStatement,ctx);
	}

	boolean hasRange = false;
	boolean hasSource = false;
	boolean isInited = false;
	int startIncl = 0;
	int endExcl = 0;
	int step = 0;
	
	//Complex, but its java, in scala would be so nicer even in kotlin
	//Does not work yet
	public void analyze(){
		if(isInited) return;
		isInited = true;
		
		List<org.eclipse.jdt.core.dom.Expression> inits = inner.initializers();
		List<org.eclipse.jdt.core.dom.Expression> updaters = inner.updaters();

		if(inits.size() != 1 || updaters.size() != 1) return;
		if(!(inits.get(0) instanceof VariableDeclarationExpression)) return;
		List<org.eclipse.jdt.core.dom.VariableDeclarationFragment> frags = ((org.eclipse.jdt.core.dom.VariableDeclarationExpression)inits.get(0)).fragments();
		if(frags.size() != 1) return;
		org.eclipse.jdt.core.dom.VariableDeclarationFragment frag = frags.get(0);
		
		org.eclipse.jdt.core.dom.SimpleName updName = null;
		org.eclipse.jdt.core.dom.Expression update = updaters.get(0);
		if(update instanceof org.eclipse.jdt.core.dom.PostfixExpression){
			org.eclipse.jdt.core.dom.PostfixExpression pf = (org.eclipse.jdt.core.dom.PostfixExpression)update;
			if(pf.getOperator() == PostfixExpression.Operator.INCREMENT)step = 1;
			else if(pf.getOperator() == PostfixExpression.Operator.DECREMENT)step = -1;
			if(pf.getOperand() instanceof org.eclipse.jdt.core.dom.SimpleName) updName = (org.eclipse.jdt.core.dom.SimpleName)pf.getOperand();
		} else if(update instanceof org.eclipse.jdt.core.dom.PrefixExpression){
			org.eclipse.jdt.core.dom.PrefixExpression pf = (org.eclipse.jdt.core.dom.PrefixExpression)update;
			if(pf.getOperator() == PrefixExpression.Operator.INCREMENT)step = 1;
			else if(pf.getOperator() == PrefixExpression.Operator.DECREMENT)step = -1;
			if(pf.getOperand() instanceof org.eclipse.jdt.core.dom.SimpleName) updName = (org.eclipse.jdt.core.dom.SimpleName)pf.getOperand();
		} else if(update instanceof org.eclipse.jdt.core.dom.Assignment){
			org.eclipse.jdt.core.dom.Assignment as = (org.eclipse.jdt.core.dom.Assignment)update;
			if(as.getRightHandSide() instanceof org.eclipse.jdt.core.dom.NumberLiteral){
				String token = ((org.eclipse.jdt.core.dom.NumberLiteral)as.getRightHandSide()).getToken(); //find
				try{
					int diff = Integer.parseInt(token);
					if(as.getOperator() == Assignment.Operator.PLUS_ASSIGN)step = diff;
					else if(as.getOperator() == Assignment.Operator.MINUS_ASSIGN)step = -diff;
					if(as.getLeftHandSide() instanceof org.eclipse.jdt.core.dom.SimpleName) updName = (org.eclipse.jdt.core.dom.SimpleName)as.getLeftHandSide();
				} catch (NumberFormatException e){ }	
			} 
		}
		
		if(step == 0 || updName == null || !updName.getIdentifier().equals(frag.getName().getIdentifier())) return;
		
		org.eclipse.jdt.core.dom.Expression init = frag.getInitializer();
		if(init instanceof org.eclipse.jdt.core.dom.NumberLiteral){
			org.eclipse.jdt.core.dom.NumberLiteral initNum = (org.eclipse.jdt.core.dom.NumberLiteral)init;
			try{
				startIncl = Integer.parseInt(initNum.getToken());
				org.eclipse.jdt.core.dom.Expression condition = inner.getExpression();
				if(condition instanceof org.eclipse.jdt.core.dom.InfixExpression){
					org.eclipse.jdt.core.dom.InfixExpression infix = (org.eclipse.jdt.core.dom.InfixExpression)condition;
					boolean leftId = true;
					org.eclipse.jdt.core.dom.Expression bound;
					if(infix.getLeftOperand() instanceof org.eclipse.jdt.core.dom.SimpleName){
						if(!(((org.eclipse.jdt.core.dom.SimpleName)infix.getLeftOperand()).getIdentifier().equals(updName))) return;
						bound = infix.getRightOperand();
					} else if(infix.getRightOperand() instanceof org.eclipse.jdt.core.dom.SimpleName){
						if(!(((org.eclipse.jdt.core.dom.SimpleName)infix.getRightOperand()).getIdentifier().equals(updName))) return;
						leftId = false;
						bound = infix.getLeftOperand();
					} else {
						return;
					}
					
					if(bound instanceof org.eclipse.jdt.core.dom.NumberLiteral){
						endExcl = Integer.parseInt(((org.eclipse.jdt.core.dom.NumberLiteral)bound).getToken());
						//we do not analyse this infinite or zero cases
						if(startIncl <= endExcl){
							if(step <= 0)return;
							//expect < or <=
							if((leftId && infix.getOperator() == InfixExpression.Operator.LESS_EQUALS) || (!leftId && infix.getOperator() == InfixExpression.Operator.GREATER_EQUALS)){
								endExcl++; //we use exclusive end
							} else if((leftId && infix.getOperator() != InfixExpression.Operator.LESS) || (!leftId && infix.getOperator() != InfixExpression.Operator.GREATER)){
								return;
							}
							hasRange = true;
						} else if(startIncl >= endExcl){
							if(step >= 0)return;
							//expect > or >=
							if((leftId && infix.getOperator() == InfixExpression.Operator.GREATER_EQUALS) || (!leftId && infix.getOperator() == InfixExpression.Operator.LESS_EQUALS)){
								endExcl++; //we use exclusive end
							} else if((leftId && infix.getOperator() != InfixExpression.Operator.GREATER) || (!leftId && infix.getOperator() != InfixExpression.Operator.LESS)){
								return;
							}
							hasRange = true;
						}
						return;
					}
				}
				if(true)throw new AssertionFailedException(""+condition.getClass());

			} catch (NumberFormatException e){ 
				return;
			}	
		} else {
			if(Math.abs(step) != 1) return;
			//Todo: my be a collection
			return;
		}
	
	}

	@Override
	public Optional<Integer> getIterations() {
		analyze();
		if(!hasRange) return Optional.absent();
		return Optional.of((endExcl - startIncl)/step);
	}

	@Override
	public Optional<IAstNode> getSourceNode() {
		analyze();
		//todo: not done yet
		return Optional.absent();
	}

	@Override
	public List<IAstNode> getInitNodes() {
		@SuppressWarnings("unchecked")
		List<org.eclipse.jdt.core.dom.Expression> exprs = inner.initializers();
		return exprs.stream().map(e -> IAstNode.fromEclipseAstNode(e,ctx)).collect(Collectors.toList());
	}
	
	public List<IAstNode> getUpdaters() {
		@SuppressWarnings("unchecked")
		List<org.eclipse.jdt.core.dom.Expression> exprs = inner.updaters();
		return exprs.stream().map(e -> IAstNode.fromEclipseAstNode(e,ctx)).collect(Collectors.toList());
	}
	
	public IAstNode getCondition(){
		return IAstNode.fromEclipseAstNode(inner.getExpression(), ctx);
	}
	
	public IAstNode getBody(){
		return IAstNode.fromEclipseAstNode(inner.getBody(), ctx);
	}

	
	
}
