package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.Stack;
import java.util.function.BiFunction;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.Dimension;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.IntersectionType;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NameQualifiedType;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodReference;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeMethodReference;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.UnionType;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodOccurence;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.StaticAstFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

/**
 * An eclipse AST visitor which delegates its visit calls to a PerformanceVisitor 
 * and in the process does some conventions to enable additional features on the Performance AST.
 * Most notably it allows to switch out the used visitor for a subbranch.
 * @author Markus Knecht
 */
public class AstDelegator extends ASTVisitor  {
	
	 //A Entry tracking a sub branch visitor
	 private static class StackEntry{
		 public final int depth; 					//depth at which the new visitor came into play
		 public final PerformanceVisitor visitor;	//The Visitor
		 public StackEntry(int depth, PerformanceVisitor visitor) {
			this.depth = depth;
			this.visitor = visitor;
		 }
	 }
	
	 //Stack of all active Visitors
	 private final Stack<StackEntry> visitors = new Stack<>();
	 //Current context, needed to enrich created AST nodes
	 private AstContext context;
	 //Current Depth
	 private int depth = 0;
	 
	 /**
	  * Creates a new AstDelegato
	  * @param visitor to initially delegate to (may change for sub branches)
	  * @param context of the current Ast
	  */
	 public AstDelegator(final PerformanceVisitor visitor, AstContext context) {
		 this.context = context;
		 pushChildVisitor(visitor);
	 }
	 
	 
	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public void preVisit(ASTNode node) {
		 //Increase the depth before processing a node and its children
		 depth++;
	 }
	 
	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public void postVisit(ASTNode node) {
		 //if their are visitors which are no longer valid after we left this node remove them from the stack
		 while (!visitors.isEmpty()  && visitors.peek().depth >= depth) visitors.pop().visitor.finish();
		 //Decrease the depth after processing a node and its children
		 depth--;
	 }
	
	 //Activates a new Visitor for the current branch
	 private void pushChildVisitor(PerformanceVisitor visitor){
		 //if this visitor is already active or is invalid do not change anything
		 //null is here expected and explicitly is used as a sign to keep the current visitor
		 if(visitor == null || (!visitors.isEmpty() && visitors.peek().visitor == visitor))return;
		 //enable the new Visitor
		 visitors.push(new StackEntry(depth, visitor));
	 }

	 //Fetches the current Visitor, which is the peek of the stack
	 private PerformanceVisitor getCurrent(){
		 	return visitors.peek().visitor;
	 }
	 
	 //Helper method, to enable sharing of common Code
	 //Is called before each Node
	 private boolean handleVisitEnter(IAstNode node){
		 //Ask the current Visitor if he would like to process this node with another visitor
		 //null means No
		 PerformanceVisitor subst = getCurrent().concreteNodeVisitor(node);
		 //activate the new visitor (keeps current in case of null)
		 pushChildVisitor(subst);
		 //check if the active visitor needs to visit the current node (this is already the new one if a new one was requested)
		 return getCurrent().shouldVisitNode(node);
	 }

	 //Helper method, to enable sharing of common Code
	 //Is called after each Node is processed but before its child's are processed
	 //the parameter is the visitor used for the child's (if null then the visitor is not changed)
	 private boolean handleVisitReturn(PerformanceVisitor pmv, IAstNode node){
		 //enable the new visitor for the child's
		 pushChildVisitor(pmv);
		 //ask the child visitor if he wants to visit the child's
		 return getCurrent().shouldVisitChilds(node);
	 }
	 
	//a higher order function to encapsulate the primary visit logic.
	/* Called:
	 * genericVisit(
	 * 		node, //The eclipse astNode 
	 * 		StaticAstFactory::create[AstNodeName], //where [AstNodeName] is the name of the node (factory ethod
     *		(pv,n) -> pv.visit(n),				   //MostSpecific Visit	
     *		(pv,n) -> pv.visit((SuperType)n),	   //Secondary MostSpecific Visit	
     *		......							       //.......
	 */
	 
	@SafeVarargs //Generics varargs not supported
	private final <N extends ASTNode, T extends IAstNode> boolean genericVisit(final N eclipseNode, final BiFunction<N, AstContext, T> factoryMethod, final BiFunction<PerformanceVisitor, T, PerformanceVisitor>... visits){
		 //create the IAstNode from the AstNode
		 T node = factoryMethod.apply(eclipseNode, context);
		 //Process the enter
		 if(!handleVisitEnter(node)) return false;
		 //prepare to visit
		 PerformanceVisitor m = null;
		 PerformanceVisitor  cur = getCurrent();
		 //visit from most specific to least specific
		 for(BiFunction<PerformanceVisitor, T, PerformanceVisitor> visit: visits){
			 //abort if we already visited one, only visit most specific thats defined
			 //null signals not defined
			 if(m != null) break;
			 //call the visitor
			 m = visit.apply(cur, node);
		 }
		 //process the visit return value
		 return handleVisitReturn(m,node);
	 }
	
	//Build somehow fails if only one param is passed to vararg of genericVisit, thats why this forwarder exists
	@SuppressWarnings("unchecked")
	private final <N extends ASTNode, T extends IAstNode> boolean genericVisit(final N eclipseNode, final BiFunction<N, AstContext, T> factoryMethod, final BiFunction<PerformanceVisitor, T, PerformanceVisitor> visit){
		//Generics varargs not supported thus raw type
		return genericVisit(eclipseNode, factoryMethod, new BiFunction[]{visit});
	 }
	
	 //Helper method, used to visit eclipse nodes, that do not have a separate representation in the Performance Ast
	 // or which do not need special handling
	 private boolean defaultVisit(final ASTNode node){
		 //just a genericVisit, that uses the generic Factory Method and does not call any visit's
		 return genericVisit(node, StaticAstFactory::fromEclipseAstNode);
	 }
	
	 /**
	  * {@inheritDoc}
	  */
	 //This is a nonstandard Visit because it fills the MethodContext
	 @Override
     public boolean visit(final MethodDeclaration methodDeclaration) {
		 //this basically is inlined genericVisit with one extra line
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration decl = StaticAstFactory.createMethodDeclaration(methodDeclaration,context);
		 context = new ProgrammMarkerMethodContext(context, decl); //extra line
		 if(!handleVisitEnter(decl)) return false;
		 PerformanceVisitor m = getCurrent().visit(decl);
		 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m,decl);
     }
	 
	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public void endVisit(final MethodDeclaration methodDeclaration) {
		 //Remove the MethodContext
		 context = ((ProgrammMarkerMethodContext)context).base;
	 }
	
	/**
	 * {@inheritDoc}
	 */
    @Override
	public boolean visit(SingleVariableDeclaration node) {
    	 //This visit is special, because the eclipse Ast does treat Parameters as normal variable Declaration, but FeedbackHandler does not
    	 int count = 0;
    	 //fetch all the parameters of the current Method
    	 for(Object param :context.getCurrentMethod().getEclipseAstNode().parameters()){
    		//if this param is the visited variable declaration, treat it as ParameterDeclaration 
    		if(param.equals(node)) {
    			 final int finCount = count; //lambdas can only refer final variables, so we copy
    			 return genericVisit(
    					 node, 
    					 (n,c) -> StaticAstFactory.createParameterDeclaration(finCount,n,c), //we need full lambda because of the extra param
    					 (pv,n) -> pv.visit(n)
    			 );
    		}
    		count++;
    	 }
 		 //if this is the an ordinary variable declaration use the default
    	 return defaultVisit(node);

	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean visit(final MethodInvocation methodInvocation) {
    	 return genericVisit(
    			 methodInvocation, 
    			 StaticAstFactory::createMethodInvocation, 
    			 (pv,n) -> pv.visit(n),
    			 (pv,n) -> pv.visit((Invocation)n),
    			 (pv,n) -> pv.visit((MethodOccurence)n)
		 );
    }
  
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean visit(final SuperMethodInvocation methodInvocation) {
    	 return genericVisit(
    			 methodInvocation, 
    			 StaticAstFactory::createMethodInvocation, 
    			 (pv,n) -> pv.visit(n),
    			 (pv,n) -> pv.visit((Invocation)n),
    			 (pv,n) -> pv.visit((MethodOccurence)n)
		 ); 
    }
    
    /**
     * {@inheritDoc}
     */ 
    @Override
    public boolean visit(final ClassInstanceCreation newInstance) {
	   	 return genericVisit(
	   			 newInstance, 
				 StaticAstFactory::createConstructorInvocation, 
				 (pv,n) -> pv.visit(n),
				 (pv,n) -> pv.visit((Invocation)n),
				 (pv,n) -> pv.visit((MethodOccurence)n)
		 ); 
    }
   
    /**
     * {@inheritDoc}
     */ 
	@Override
	public boolean visit(final ConstructorInvocation alt) {
		return genericVisit(
				 alt, 
				 StaticAstFactory::createConstructorInvocation, 
				 (pv,n) -> pv.visit(n),
				 (pv,n) -> pv.visit((Invocation)n),
				 (pv,n) -> pv.visit((MethodOccurence)n)
		 );
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(final SuperConstructorInvocation alt) {
		return genericVisit(
				 alt, 
				 StaticAstFactory::createConstructorInvocation, 
				 (pv,n) -> pv.visit(n),
				 (pv,n) -> pv.visit((Invocation)n),
				 (pv,n) -> pv.visit((MethodOccurence)n)
		 );
	}
	
	/**
	 * {@inheritDoc}
	 */     
	@Override
	public boolean visit(final EnhancedForStatement foreachStatement) {
		return genericVisit(
				foreachStatement, 
				 StaticAstFactory::createForEach, 
				 (pv,n) -> pv.visit(n),
				 (pv,n) -> pv.visit((Loop)n)
		 );
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(final ForStatement forStatement) {
		return genericVisit(
				forStatement, 
				StaticAstFactory::createFor, 
				(pv,n) -> pv.visit(n),
				(pv,n) -> pv.visit((Loop)n)
		 );
	} 
	
	/**
	 * {@inheritDoc}
	 */ 
 	@Override
 	public boolean visit(CatchClause catchClause) {
 		return genericVisit(
 				catchClause, 
				StaticAstFactory::createCatchClause, 
				(pv,n) -> pv.visit(n)
		);
 	} 
 	
 	/**
 	 * {@inheritDoc}
 	 */	
	@Override
	public boolean visit(Block block) {
		return genericVisit(
				block, 
				StaticAstFactory::createBlock, 
				(pv,n) -> pv.visit(n)
		);
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public boolean visit(IfStatement ifNode) {
		return genericVisit(
				ifNode, 
				StaticAstFactory::createBranching, 
				(pv,n) -> pv.visit(n)
		);
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ConditionalExpression condNode) {
		return genericVisit(
				condNode, 
				StaticAstFactory::createBranching, 
				(pv,n) -> pv.visit(n)
		);
	}	

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public boolean visit(SwitchStatement switchNode) {
		return genericVisit(
				switchNode, 
				StaticAstFactory::createBranching, 
				(pv,n) -> pv.visit(n)
		);
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TryStatement tryNode) {
		return genericVisit(
				tryNode, 
				StaticAstFactory::createTry, 
				(pv,n) -> pv.visit(n)
		);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(SwitchCase node) {
		//Note: nothing special: we process but do not generate a hook
		return defaultVisit(node);
	}
	
	//TODO: Note: If Context ever tracks current class, then this needs special handling similar to MethodDeclaration
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		return defaultVisit(node);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(LabeledStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public boolean visit(DoStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(WhileStatement node) {
		return defaultVisit(node);
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return defaultVisit(node);
	}

	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ArrayAccess node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ArrayCreation node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ArrayInitializer node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ArrayType node) {
		return defaultVisit(node);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(AssertStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(Assignment node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(BooleanLiteral node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(BreakStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(CastExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(CharacterLiteral node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(CompilationUnit node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ContinueStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(CreationReference node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(Dimension node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(EmptyStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(EnumDeclaration node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ExpressionMethodReference node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ExpressionStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(FieldAccess node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(FieldDeclaration node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(InfixExpression node) {
		
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(Initializer node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(InstanceofExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(IntersectionType node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(Javadoc node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(LambdaExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(MarkerAnnotation node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(MemberRef node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(MemberValuePair node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(MethodRef node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(MethodRefParameter node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(Modifier node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(NameQualifiedType node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(NormalAnnotation node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(NullLiteral node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(NumberLiteral node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(PackageDeclaration node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ParameterizedType node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ParenthesizedExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(PostfixExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(PrefixExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(PrimitiveType node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(QualifiedName node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(QualifiedType node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ReturnStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(SimpleName node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(SimpleType node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(StringLiteral node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(SuperFieldAccess node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(SuperMethodReference node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(SynchronizedStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TagElement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TextElement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ThisExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(ThrowStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TypeDeclarationStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TypeLiteral node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TypeMethodReference node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(TypeParameter node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(UnionType node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(VariableDeclarationExpression node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(VariableDeclarationStatement node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(VariableDeclarationFragment node) {
		return defaultVisit(node);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean visit(WildcardType node) {
		return defaultVisit(node);
	}
     
}


