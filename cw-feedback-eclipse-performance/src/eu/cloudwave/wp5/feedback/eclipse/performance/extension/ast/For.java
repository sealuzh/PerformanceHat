package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class For extends AAstNode<org.eclipse.jdt.core.dom.ForStatement> implements Loop {
	
	public For(org.eclipse.jdt.core.dom.ForStatement forStatement, ProgrammMarkerContext ctx) {
		super(forStatement,ctx);
	}
	
	@Override
	protected int getStartPosition() {
		return inner.getStartPosition()+3;
	}

	@Override
	protected int getEndPosition() {
          return inner.getBody().getStartPosition();
	}

	boolean hasSource = false;
	boolean isInited = false;
	int iters = -1;
	
	//Complex, but its java, in scala would be so nicer even in kotlin
	public void analyze(){
		if(isInited) return;
		isInited = true;
		
		@SuppressWarnings("unchecked")
		List<Expression> inits = inner.initializers();
		@SuppressWarnings("unchecked")
		List<Expression> updaters = inner.updaters();

		if(inits.size() != 1 || updaters.size() != 1) return;
		if(!(inits.get(0) instanceof VariableDeclarationExpression)) return;
		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> frags = ((VariableDeclarationExpression)inits.get(0)).fragments();
		if(frags.size() != 1) return;
		VariableDeclarationFragment frag = frags.get(0);
		
		String updName = null;
		Expression update = updaters.get(0);
		int step= 0;
		if(update instanceof PostfixExpression){
			PostfixExpression pf = (PostfixExpression)update;
			if(pf.getOperator() == PostfixExpression.Operator.INCREMENT)step = 1;
			else if(pf.getOperator() == PostfixExpression.Operator.DECREMENT)step = -1;
			if(pf.getOperand() instanceof SimpleName) updName = ((SimpleName)pf.getOperand()).getIdentifier();
		} else if(update instanceof PrefixExpression){
			PrefixExpression pf = (PrefixExpression)update;
			if(pf.getOperator() == PrefixExpression.Operator.INCREMENT)step = 1;
			else if(pf.getOperator() == PrefixExpression.Operator.DECREMENT)step = -1;
			if(pf.getOperand() instanceof SimpleName) updName = ((SimpleName)pf.getOperand()).getIdentifier();
		} else if(update instanceof Assignment){
			Assignment as = (Assignment)update;
			if(as.getRightHandSide() instanceof NumberLiteral){
				String token = ((NumberLiteral)as.getRightHandSide()).getToken(); //find
				try{
					int diff = Integer.parseInt(token);
					if(as.getOperator() == Assignment.Operator.PLUS_ASSIGN)step = diff;
					else if(as.getOperator() == Assignment.Operator.MINUS_ASSIGN)step = -diff;
					if(as.getLeftHandSide() instanceof SimpleName) updName = ((SimpleName)as.getLeftHandSide()).getIdentifier();
				} catch (NumberFormatException e){ }	
			} 
		}
		
		if(step == 0 || updName == null || !updName.equals(frag.getName().getIdentifier())) return;
		
		Expression init = frag.getInitializer();
		if(init instanceof NumberLiteral){
			NumberLiteral initNum = (NumberLiteral)init;
			try{
				int startIncl = Integer.parseInt(initNum.getToken());
				Expression condition = inner.getExpression();
				if(condition instanceof InfixExpression){
					InfixExpression infix = (InfixExpression)condition;
					boolean leftId = true;
					Expression bound;
					if(infix.getLeftOperand() instanceof SimpleName){
						if(!(((SimpleName)infix.getLeftOperand()).getIdentifier().equals(updName))) return;
						bound = infix.getRightOperand();
					} else if(infix.getRightOperand() instanceof SimpleName){
						if(!(((SimpleName)infix.getRightOperand()).getIdentifier().equals(updName))) return;
						leftId = false;
						bound = infix.getLeftOperand();
					} else {
						return;
					}
					
					if(bound instanceof NumberLiteral){
						int endExcl = Integer.parseInt(((NumberLiteral)bound).getToken());
						boolean pased = false;
						boolean greater = true;
						if(infix.getOperator() == InfixExpression.Operator.LESS_EQUALS || infix.getOperator() == InfixExpression.Operator.GREATER_EQUALS){
							endExcl++;
							pased = true;
							if(infix.getOperator() == InfixExpression.Operator.LESS_EQUALS)greater = false;
						} else if(infix.getOperator() == InfixExpression.Operator.LESS || infix.getOperator() == InfixExpression.Operator.GREATER){
							pased = true;
							if(infix.getOperator() == InfixExpression.Operator.LESS)greater = false;
						}
						if(!pased) return;
						if(!leftId) greater = !greater;
						//we do not analyse this infinite or zero cases
						if(startIncl < endExcl && (step < 0 || greater)) return;
						if(startIncl > endExcl && (step > 0 || !greater)) return;
						iters = (endExcl - startIncl)/step;
						return;
					}
				}
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
		if(iters < 0) return Optional.absent();
		return Optional.of(iters);
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
