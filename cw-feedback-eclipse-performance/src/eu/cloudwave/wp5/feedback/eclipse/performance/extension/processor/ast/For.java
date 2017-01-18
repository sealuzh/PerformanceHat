package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast;

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

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;

/**
 * A IAstNode for For loops
 * @author Markus Knecht
 *
 */
public class For extends AAstNode<org.eclipse.jdt.core.dom.ForStatement> implements Loop {
	
	//Lazy calced SubNodes
	private List<IAstNode> initNodes = null;	
	private List<IAstNode> updaterNodes = null;	
	private IAstNode condition = null;
	private IAstNode body = null;
	
	For(org.eclipse.jdt.core.dom.ForStatement forStatement, AstContext ctx) {
		super(forStatement,ctx);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getStartPosition() {
		return inner.getStartPosition()+3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getEndPosition() {
          return inner.getBody().getStartPosition();
	}

	//results of the iteration/source analysis
	boolean hasSource = false;
	boolean isInited = false;
	IAstNode source = null;
	int iters = -1;
	
	//Helper to parse int literals
	private Integer parseIntOrNull(NumberLiteral lit){
		try{
			return Integer.parseInt(lit.getToken());
		} catch (NumberFormatException e){ 
			return null;
		}
	}
	
	//Helper to check the update block of the loop
	private boolean checkUpdateVaidity(Expression update, String expectedName /*i in comments*/){
		//is it a postfix: i++,i--
		if(update instanceof PostfixExpression){
			PostfixExpression pf = (PostfixExpression)update;
			if(pf.getOperator() != PostfixExpression.Operator.INCREMENT || pf.getOperator() == PostfixExpression.Operator.DECREMENT) return false;
			return (pf.getOperand() instanceof SimpleName && expectedName.equals(((SimpleName)pf.getOperand()).getIdentifier())); 
		} 
		//is it a prefix: ++i,--i
		if(update instanceof PrefixExpression){
			PrefixExpression pf = (PrefixExpression)update;
			if(pf.getOperator() != PrefixExpression.Operator.INCREMENT || pf.getOperator() == PrefixExpression.Operator.DECREMENT) return false;
			return (pf.getOperand() instanceof SimpleName && expectedName.equals(((SimpleName)pf.getOperand()).getIdentifier())); 
		}
		//is it a assignement: i += ?, i -= ?, .....
		if(update instanceof Assignment){
			Assignment as = (Assignment)update;
			if(as.getOperator() != Assignment.Operator.PLUS_ASSIGN || as.getOperator() == Assignment.Operator.MINUS_ASSIGN) return false;
			return (as.getRightHandSide() instanceof NumberLiteral && as.getLeftHandSide() instanceof SimpleName && expectedName.equals(((SimpleName)as.getLeftHandSide()).getIdentifier()));
		}
		return false;
	}
	
	//Helper to check the condition
	private boolean checkConditionVaidity(InfixExpression infix, String expectedName/*i in comments*/){
		Operator infixOperator = infix.getOperator();			
		if(																		//check that it is a comparison
				infixOperator != InfixExpression.Operator.LESS && 
				infixOperator != InfixExpression.Operator.GREATER &&
				infixOperator != InfixExpression.Operator.LESS_EQUALS && 
				infixOperator != InfixExpression.Operator.GREATER_EQUALS
		) return false;
		
		if(infix.getLeftOperand() instanceof SimpleName && ((SimpleName)infix.getLeftOperand()).getIdentifier().equals(expectedName)) return true;
		if(infix.getRightOperand() instanceof SimpleName && ((SimpleName)infix.getRightOperand()).getIdentifier().equals(expectedName)) return true;
		return false;
	}
	
	//Extracts the collection source from a expression
	private IAstNode extractCollectionSource(Expression exp){
		String accessor;
		Expression receiver;
		//if its a MethodCall, the collection is the return of the method
		//So the source is the call (we don't track into calls)
		if(exp instanceof org.eclipse.jdt.core.dom.MethodInvocation){
			receiver = ((org.eclipse.jdt.core.dom.MethodInvocation)exp).getExpression();
			accessor = ((org.eclipse.jdt.core.dom.MethodInvocation)exp).getName().getIdentifier();
		//If it is a field, the source is the field initialisation/assignement
		} else if(exp instanceof org.eclipse.jdt.core.dom.FieldAccess){
			receiver = ((org.eclipse.jdt.core.dom.FieldAccess)exp).getExpression();
			accessor = ((org.eclipse.jdt.core.dom.FieldAccess)exp).getName().getIdentifier();
		//If it is a field, the source is the variable initialisation/assignement
		} else if(exp instanceof SimpleName){
			Optional<IAstNode> n = LoopAnalysisHelper.resolveName(this,(SimpleName)exp, ctx);
			if(!n.isPresent()) return null;
			if(!(n.get().getEclipseAstNode() instanceof Expression))return null;
			return extractCollectionSource((Expression)n.get().getEclipseAstNode());
		} else {
			return null;
		}
		//we only consider collections valid source and we need the length size in a for loop
		//this is hardcoded because theirs no better way
		if(accessor.equals("size") || accessor.equals("length")){
			//if its just a name, find where the name is initialised/assigned
			if(receiver instanceof SimpleName){
				Optional<IAstNode> n = LoopAnalysisHelper.resolveName(this,(SimpleName)receiver, ctx);
				return n.orNull();
			}
			//if it is a method, this is the source
			if(receiver instanceof org.eclipse.jdt.core.dom.MethodInvocation){
				return StaticAstFactory.createMethodInvocation((org.eclipse.jdt.core.dom.MethodInvocation)receiver,ctx);
			}
			return StaticAstFactory.fromEclipseAstNodeOrDefault(receiver, ctx);
		}
		return null;
	}
	
	//extracts the step size from an update
	private Integer extractStep(Expression update) {
		//if its an postfix its +1 or -1 depending on if it is i++ or i--
		if(update instanceof PostfixExpression){
			PostfixExpression pf = (PostfixExpression)update;
			if(pf.getOperator() == PostfixExpression.Operator.INCREMENT){
				return 1;
			} else if(pf.getOperator() == PostfixExpression.Operator.DECREMENT){
				return -1;
			}
		}
		//if its an prefix its +1 or -1 depending on if it is ++i or --i
		if(update instanceof PrefixExpression){
			PrefixExpression pf = (PrefixExpression)update;
			if(pf.getOperator() == PrefixExpression.Operator.INCREMENT){
				return 1;
			} else if(pf.getOperator() == PrefixExpression.Operator.DECREMENT){
				return -1;
			}
		} 
		//if its an assignment it can be +/-x depending on i += x or i -= x
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
	
	//analyzes the node once
	public void analyze(){
		//if done already abort
		if(isInited) return;
		isInited = true; //mark as done
		
		//Extractions and validations
		@SuppressWarnings("unchecked")
		List<Expression> inits = inner.initializers(); 			//get inializers
		@SuppressWarnings("unchecked")	
		List<Expression> updaters = inner.updaters();			//get updaters
		if(inits.size() != 1 || updaters.size() != 1) return;	// we can not handle multiple updaters/initializers
		if(!(inits.get(0) instanceof VariableDeclarationExpression)) return; //we can only handle loops over a variable so we need a int i = ?? initializer
		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> frags = ((VariableDeclarationExpression)inits.get(0)).fragments(); //get the fragments
		if(frags.size() != 1) return;							//we can not handle i = x = ??, only i = ??
		VariableDeclarationFragment frag = frags.get(0);		//get the fragment
		if(!(inner.getExpression() instanceof InfixExpression)) return; // make sure its an infix (later we check for assignment)
		InfixExpression infix = (InfixExpression)inner.getExpression();
		String updName = frag.getName().getIdentifier();		//extract the name of the looping variable, comments assume its i
		if(updName == null) return; 							//ups something wrong abort
		Expression update = updaters.get(0);					//continue with the updater
		if(!checkUpdateVaidity(update, updName)) return;		//updater check (is separate method to reduce pollution of this method)
		Integer step= extractStep(update);						//find out how much the variable increases decreases per iteration
		if(step == null) return;								//ups no static increase decrease abort
		if(!checkConditionVaidity(infix, updName)) return;		//condition check (is separate method to reduce pollution of this method)
		
		//Init results or potential results
		Integer startValue = null;
		IAstNode startCollectionNode = null;
		Integer endValue = null;
		IAstNode endCollectionNode = null;
		boolean endInclusive = false;
		boolean greater = true;
		
		//get the start value or the start value producer
		Expression init = frag.getInitializer();
		if(init instanceof NumberLiteral){
			startValue = parseIntOrNull((NumberLiteral)init);
		} else {
			startCollectionNode = extractCollectionSource(init);
		}
		
		//based on the compare type define if the ends are inclusive and if the start or end is the greater value
		Operator infixOperator = infix.getOperator();
		endInclusive = infixOperator == InfixExpression.Operator.LESS_EQUALS | infixOperator == InfixExpression.Operator.GREATER_EQUALS;
		greater = infixOperator == InfixExpression.Operator.GREATER | infixOperator == InfixExpression.Operator.GREATER_EQUALS;

		//find the bound ( the value used to compare against, the non variable part )
		Expression bound;
		if(infix.getLeftOperand() instanceof SimpleName){
			bound = infix.getRightOperand();
		} else if(infix.getRightOperand() instanceof SimpleName){
			greater = !greater;
			bound = infix.getLeftOperand();
		} else {
			return;
		}
		
		//is the bound a static number or a collection	
		if(bound instanceof NumberLiteral){
			endValue = parseIntOrNull((NumberLiteral)bound);
		}else {
			endCollectionNode = extractCollectionSource(bound);
		}
		
		//Test if we have a valid case
		if(startValue != null && endCollectionNode != null && step == 1){					//we only consider step size one for collections  
			if((startValue == 0 && !endInclusive) || (startValue == 1 && endInclusive)){	//we only consider loops looping over each element | 0 -> col.size-1, 1 -> col.size
				hasSource = true;
				source = endCollectionNode;
			} 
			return;
		} else if(endValue != null && startCollectionNode != null && step == -1){			//we only consider step size one for collections
			if((endValue == 0 && !endInclusive) || (endValue == 1 && endInclusive)){		//we only consider loops looping over each element | col.size -> 1, col.size -1 -> 0
				hasSource = true;
				source = startCollectionNode;
			} 
		} else if(endValue != null && startValue != null){					//no collection inwolved
			int exclEnd = endInclusive?endValue+(step>0?1:-1):endValue;		//calc end exclusive
			//we do not analyse this infinite or zero cases
			if(startValue < exclEnd && (step < 0 || greater)) return;
			if(startValue > exclEnd && (step > 0 || !greater)) return;
			//calc iters
			iters = (exclEnd - startValue)/step;
		}
		return;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Integer> getIterations() {
		analyze();
		if(iters < 0) return Optional.absent();
		return Optional.of(iters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<IAstNode> getSourceNode() {
		analyze();
		if(hasSource) return Optional.of(source);
		return Optional.absent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IAstNode> getInitNodes() {
		if(initNodes == null){
			@SuppressWarnings("unchecked")
			List<org.eclipse.jdt.core.dom.Expression> exprs = inner.initializers();
			initNodes = exprs.stream().map(e -> StaticAstFactory.fromEclipseAstNodeOrDefault(e,ctx)).collect(Collectors.toList()); 
		}

		return initNodes;
	}
	
	/**
	 * get the update statements of this loop
	 * @return List of IAstNode's representing the updaters
	 */
	public List<IAstNode> getUpdaters() {
		if(updaterNodes == null){
			@SuppressWarnings("unchecked")
			List<org.eclipse.jdt.core.dom.Expression> exprs = inner.updaters();
			updaterNodes = exprs.stream().map(e -> StaticAstFactory.fromEclipseAstNodeOrDefault(e,ctx)).collect(Collectors.toList());		
		}
		return updaterNodes;
	}
	
	/**
	 * get the condition expression of this loop
	 * @return the IAstNode representing the condition
	 */
	public IAstNode getCondition(){
		if(condition == null){
			condition = StaticAstFactory.fromEclipseAstNodeOrDefault(inner.getExpression(), ctx);
		}
		return condition;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IAstNode getBody(){
		if(body == null){
			body = StaticAstFactory.fromEclipseAstNodeOrDefault(inner.getBody(), ctx);
		}
		return body;
	}

	
	
}
