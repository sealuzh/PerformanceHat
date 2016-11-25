package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InfixExpression.Operator;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class For extends AAstNode<org.eclipse.jdt.core.dom.ForStatement> implements Loop {
	
	For(org.eclipse.jdt.core.dom.ForStatement forStatement, ProgrammMarkerContext ctx) {
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
	IAstNode source = null;
	int iters = -1;
	
	private Integer parseIntOrNull(NumberLiteral lit){
		try{
			return Integer.parseInt(lit.getToken());
		} catch (NumberFormatException e){ 
			return null;
		}
	}
	
	
	private boolean checkUpdateVaidity(Expression update, String expectedName){
		if(update instanceof PostfixExpression){
			PostfixExpression pf = (PostfixExpression)update;
			return (pf.getOperand() instanceof SimpleName && expectedName.equals(((SimpleName)pf.getOperand()).getIdentifier())); 
		} 
		if(update instanceof PrefixExpression){
			PrefixExpression pf = (PrefixExpression)update;
			return (pf.getOperand() instanceof SimpleName && expectedName.equals(((SimpleName)pf.getOperand()).getIdentifier())); 
		}
		if(update instanceof Assignment){
			Assignment as = (Assignment)update;
			return (as.getRightHandSide() instanceof NumberLiteral && as.getLeftHandSide() instanceof SimpleName && expectedName.equals(((SimpleName)as.getLeftHandSide()).getIdentifier()));
		}
		return false;
	}
	
	private boolean checkConditionVaidity(InfixExpression infix, String expectedName){
		if(infix.getLeftOperand() instanceof SimpleName && ((SimpleName)infix.getLeftOperand()).getIdentifier().equals(expectedName)) return true;
		if(infix.getRightOperand() instanceof SimpleName && ((SimpleName)infix.getRightOperand()).getIdentifier().equals(expectedName)) return true;
		return false;
	}
	
	private IAstNode extractCollectionSource(Expression exp){
		String accessor;
		Expression receiver;
		if(exp instanceof org.eclipse.jdt.core.dom.MethodInvocation){
			receiver = ((org.eclipse.jdt.core.dom.MethodInvocation)exp).getExpression();
			accessor = ((org.eclipse.jdt.core.dom.MethodInvocation)exp).getName().getIdentifier();
		} else if(exp instanceof org.eclipse.jdt.core.dom.FieldAccess){
			receiver = ((org.eclipse.jdt.core.dom.FieldAccess)exp).getExpression();
			accessor = ((org.eclipse.jdt.core.dom.FieldAccess)exp).getName().getIdentifier();
		} else if(exp instanceof SimpleName){
			Optional<IAstNode> n = LoopAnalysisHelper.resolveName(this,(SimpleName)exp, ctx);
			if(!n.isPresent()) return null;
			if(!(n.get().getEclipseAstNode() instanceof Expression))return null;
			return extractCollectionSource((Expression)n.get().getEclipseAstNode());
		} else {
			return null;
		}
		if(accessor.equals("size") || accessor.equals("length")){
			if(receiver instanceof SimpleName){
				Optional<IAstNode> n = LoopAnalysisHelper.resolveName(this,(SimpleName)receiver, ctx);
				return n.orNull();
			}
			if(receiver instanceof org.eclipse.jdt.core.dom.MethodInvocation){
				return new eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.MethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation)receiver,ctx);
			}
			return StaticAstFactory.fromEclipseAstNode(receiver, ctx);
		}
		return null;
	}
	
	private Integer extractStep(Expression update) {
		if(update instanceof PostfixExpression){
			PostfixExpression pf = (PostfixExpression)update;
			if(pf.getOperator() == PostfixExpression.Operator.INCREMENT){
				return 1;
			} else if(pf.getOperator() == PostfixExpression.Operator.DECREMENT){
				return -1;
			}
		} 
		if(update instanceof PrefixExpression){
			PrefixExpression pf = (PrefixExpression)update;
			if(pf.getOperator() == PrefixExpression.Operator.INCREMENT){
				return 1;
			} else if(pf.getOperator() == PrefixExpression.Operator.DECREMENT){
				return -1;
			}
		} 
		if(update instanceof Assignment){
			Assignment as = (Assignment)update;
			if(as.getRightHandSide() instanceof NumberLiteral){
				Integer diff = parseIntOrNull((NumberLiteral)as.getRightHandSide());
				if(diff == null) return null;
				if(as.getOperator() == Assignment.Operator.PLUS_ASSIGN){
					return diff;
				} else if(as.getOperator() == Assignment.Operator.MINUS_ASSIGN){
					return -diff;
				}
			} 
		}
		return null;
	}
	
	//Complex, but its java, in scala would be so nicer even in kotlin
	public void analyze(){
		if(isInited) return;
		isInited = true;
		
		//Extractions and validations
		@SuppressWarnings("unchecked")
		List<Expression> inits = inner.initializers();
		@SuppressWarnings("unchecked")
		List<Expression> updaters = inner.updaters();
		if(inits.size() != 1 || updaters.size() != 1) return;
		if(!(inits.get(0) instanceof VariableDeclarationExpression)) return;
		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> frags = ((VariableDeclarationExpression)inits.get(0)).fragments();
		Expression update = updaters.get(0);
		if(frags.size() != 1) return;
		VariableDeclarationFragment frag = frags.get(0);
		if(!(inner.getExpression() instanceof InfixExpression)) return;
		InfixExpression infix = (InfixExpression)inner.getExpression();
		String updName = frag.getName().getIdentifier();
		if(updName == null) return; 
		if(!(update instanceof PostfixExpression || update instanceof PrefixExpression || update instanceof Assignment))return;
		if(!checkUpdateVaidity(update, updName)) return;
		Integer step= extractStep(update);
		if(step == null) return;
		if(!checkConditionVaidity(infix, updName)) return;
		Operator infixOperator = infix.getOperator();
		if(
				infixOperator != InfixExpression.Operator.LESS && 
				infixOperator != InfixExpression.Operator.GREATER &&
				infixOperator != InfixExpression.Operator.LESS_EQUALS && 
				infixOperator != InfixExpression.Operator.GREATER_EQUALS
		) return;
		
		
		
		//Collect stuff
		Integer startValue = null;
		IAstNode startCollectionNode = null;
		Integer endValue = null;
		IAstNode endCollectionNode = null;
		boolean endInclusive = false;
		boolean greater = true;
		
		Expression init = frag.getInitializer();
		if(init instanceof NumberLiteral){
			startValue = parseIntOrNull((NumberLiteral)init);
		} else {
			startCollectionNode = extractCollectionSource(init);
		}
	
		
		
		
		endInclusive = infixOperator == InfixExpression.Operator.LESS_EQUALS | infixOperator == InfixExpression.Operator.GREATER_EQUALS;
		greater = infixOperator == InfixExpression.Operator.GREATER | infixOperator == InfixExpression.Operator.GREATER_EQUALS;

		Expression bound;
		if(infix.getLeftOperand() instanceof SimpleName){
			bound = infix.getRightOperand();
		} else if(infix.getRightOperand() instanceof SimpleName){
			greater = !greater;
			bound = infix.getLeftOperand();
		} else {
			return;
		}
		
			
		if(bound instanceof NumberLiteral){
			endValue = parseIntOrNull((NumberLiteral)bound);
		}else {
			endCollectionNode = extractCollectionSource(bound);
		}
		

		if(startValue != null && endCollectionNode != null && step == 1){
			if((startValue == 0 && !endInclusive) || (startValue == 1 && endInclusive)){
				hasSource = true;
				source = endCollectionNode;
			} 
			return;
		} else if(endValue != null && startCollectionNode != null && step == -1){
			if((endValue == 0 && !endInclusive) || (endValue == 1 && endInclusive)){
				hasSource = true;
				source = startCollectionNode;
			} 
		} else if(endValue != null && startValue != null){
			int exclEnd = endInclusive?endValue+(step>0?1:-1):endValue;
			//we do not analyse this infinite or zero cases
			if(startValue < exclEnd && (step < 0 || greater)) return;
			if(startValue > exclEnd && (step > 0 || !greater)) return;
			iters = (exclEnd - startValue)/step;
		}
		return;
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
		if(hasSource) return Optional.of(source);
		return Optional.absent();
	}

	@Override
	public List<IAstNode> getInitNodes() {
		@SuppressWarnings("unchecked")
		List<org.eclipse.jdt.core.dom.Expression> exprs = inner.initializers();
		return exprs.stream().map(e -> StaticAstFactory.fromEclipseAstNode(e,ctx)).collect(Collectors.toList());
	}
	
	public List<IAstNode> getUpdaters() {
		@SuppressWarnings("unchecked")
		List<org.eclipse.jdt.core.dom.Expression> exprs = inner.updaters();
		return exprs.stream().map(e -> StaticAstFactory.fromEclipseAstNode(e,ctx)).collect(Collectors.toList());
	}
	
	public IAstNode getCondition(){
		return StaticAstFactory.fromEclipseAstNode(inner.getExpression(), ctx);
	}
	
	public IAstNode getBody(){
		return StaticAstFactory.fromEclipseAstNode(inner.getBody(), ctx);
	}

	
	
}
