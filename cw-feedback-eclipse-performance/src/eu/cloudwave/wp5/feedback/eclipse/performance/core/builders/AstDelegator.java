package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.Stack;

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

public class AstDelegator extends ASTVisitor  {
	
	 private static class StackEntry{
		 public final int depth;
		 public final PerformanceVisitor usedMarker;
		 public StackEntry(int depth, PerformanceVisitor usedMarker) {
			this.depth = depth;
			this.usedMarker = usedMarker;
		 }
	 }
	
	 private final Stack<StackEntry> visitors = new Stack<>();
	 private AstContext context;
	 
	 private int depth = 0;
	 
	 public AstDelegator(final PerformanceVisitor visitor, AstContext context) {
		 this.context = context;
		 pushChildVisitor(visitor);
	 }
	 
	@Override
	public void preVisit(ASTNode node) {
		depth++;
	}

	@Override
	public void postVisit(ASTNode node) {
		while (!visitors.isEmpty()  && visitors.peek().depth >= depth) visitors.pop().usedMarker.finish();
		depth--;
	}
	
	public void pushChildVisitor(PerformanceVisitor visitor){
		if(visitor == null || (!visitors.isEmpty() && visitors.peek().usedMarker == visitor))return;
		visitors.push(new StackEntry(depth, visitor));
	}

	public PerformanceVisitor getCurrent(){
		 return visitors.peek().usedMarker;
	 }
	 
	
	 private boolean handleVisitStart(IAstNode node){
		PerformanceVisitor subst = getCurrent().concreteNodeVisitor(node);
		pushChildVisitor(subst);
		return getCurrent().shouldVisitNode(node);
	 }

	 private boolean handleVisitReturn(PerformanceVisitor pmv){
		 if(pmv != null)  pushChildVisitor(pmv);
		 return getCurrent().shouldVisitChilds();
	 }
	
	 private boolean defaultVisit(final ASTNode node){
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode decl = StaticAstFactory.fromEclipseAstNode(node, context);
		 if(!handleVisitStart(decl)) return false;
		 return handleVisitReturn(null);
	 }
	 
	 //Todo: lambdafy visiors
	 
	 @Override
     public boolean visit(final MethodDeclaration methodDeclaration) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration decl = StaticAstFactory.createMethodDeclaration(methodDeclaration,context);
		 context = new ProgrammMarkerMethodContext(context, decl);
		 if(!handleVisitStart(decl)) return false;
		 PerformanceVisitor m = getCurrent().visit(decl);
		 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
	 
	 public void endVisit(final MethodDeclaration methodDeclaration) {
		 context = ((ProgrammMarkerMethodContext)context).base;
	 }
	 
	 private SimpleName label = null;
	 
	 @Override
	 public boolean visit(LabeledStatement node) {
		label = node.getLabel();
		node.getBody().accept(this);
		label = null;
		return false;
	 }
	
     @Override
	public boolean visit(SingleVariableDeclaration node) {
    	 int count = 0;
    	 
    	 for(Object param :context.getCurrentMethode().getEclipseAstNode().parameters()){
    		if(param.equals(node)) {
    	    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ParameterDeclaration decl = StaticAstFactory.createParameterDeclaration(count,context.getCurrentMethode(),node,context);
    			 if(!handleVisitStart(decl)) return false;
    	    	 PerformanceVisitor m = getCurrent().visit(decl);
    			 return handleVisitReturn(m);
    		}
    		count++;
    	 }
    	 
    	 return defaultVisit(node);

	}

	@Override
     public boolean visit(final MethodInvocation methodInvocation) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodInvocation decl =StaticAstFactory.createMethodInvocation(methodInvocation,context);
		 if(!handleVisitStart(decl)) return false;
    	 PerformanceVisitor m = getCurrent().visit(decl);
   	  	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
   
     @Override
     public boolean visit(final SuperMethodInvocation methodInvocation) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodInvocation decl = StaticAstFactory.createMethodInvocation(methodInvocation,context);
		 if(!handleVisitStart(decl)) return false;
    	 PerformanceVisitor m = getCurrent().visit(decl);
   	  	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
     
     @Override
     public boolean visit(final ClassInstanceCreation newInstance) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ConstructorInvocation decl = StaticAstFactory.createConstructorInvocation(newInstance,context);
		 if(!handleVisitStart(decl)) return false;
    	 PerformanceVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
     
	 @Override
     public boolean visit(final ConstructorInvocation alt) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ConstructorInvocation decl = StaticAstFactory.createConstructorInvocation(alt,context);
		 if(!handleVisitStart(decl)) return false;
    	 PerformanceVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }

	 @Override
     public boolean visit(final SuperConstructorInvocation alt) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ConstructorInvocation decl = StaticAstFactory.createConstructorInvocation(alt,context);
		 if(!handleVisitStart(decl)) return false;
    	 PerformanceVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Invocation)decl);
   	  	 if(m == null) m = getCurrent().visit((MethodOccurence)decl);
		 return handleVisitReturn(m);
     }
     
     @Override
     public boolean visit(final EnhancedForStatement foreachStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ForEach decl = StaticAstFactory.createForEach(foreachStatement,context);
		 if(!handleVisitStart(decl)) return false;
    	 PerformanceVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Loop)decl);
		 return handleVisitReturn(m);
     }

     @Override
     public boolean visit(final ForStatement forStatement) {
    	 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.For decl = StaticAstFactory.createFor(forStatement,context);
		 if(!handleVisitStart(decl)) return false;
    	 PerformanceVisitor m = getCurrent().visit(decl);
    	 if(m == null) m = getCurrent().visit((Loop)decl);
		 return handleVisitReturn(m);
     }    
     
 	@Override
 	public boolean visit(CatchClause catchClause) {
 		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.CatchClause decl = StaticAstFactory.createCatchClause(catchClause,context);
		 if(!handleVisitStart(decl)) return false;
 		 PerformanceVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
 	} 
	
	@Override
	public boolean visit(Block block) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Block decl = StaticAstFactory.createBlock(block,context);
		 if(!handleVisitStart(decl)) return false;
		 PerformanceVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
	}
	
	
	@Override
	public boolean visit(IfStatement ifNode) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching decl = StaticAstFactory.createBranching(ifNode,context);
		 if(!handleVisitStart(decl)) return false;
		 PerformanceVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
	}
	
	@Override
	public boolean visit(ConditionalExpression condNode) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching decl = StaticAstFactory.createBranching(condNode,context);
		 if(!handleVisitStart(decl)) return false;
		 PerformanceVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
	}

	
	@Override
	public boolean visit(SwitchStatement switchNode) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching decl = StaticAstFactory.createBranching(switchNode,context);
		 if(!handleVisitStart(decl)) return false;
		 PerformanceVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
	}

	@Override
	public boolean visit(TryStatement tryNode) {
		 eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try decl = StaticAstFactory.createTry(tryNode,context);
		 if(!handleVisitStart(decl)) return false;
		 PerformanceVisitor m = getCurrent().visit(decl);
		 return handleVisitReturn(m);
	}
	
	//Note: nothing special todo: weprocess but do not gen a hook
	@Override
	public boolean visit(SwitchCase node) {
		return defaultVisit(node);
	}

	//Note: to remember whats still open
	@Override
	public boolean visit(DoStatement node) {
		return defaultVisit(node);
	}


	@Override
	public boolean visit(WhileStatement node) {
		return defaultVisit(node);
	}
	
	
	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return defaultVisit(node);
	}

	//Better Save then Sorry
	//Makes sure, that even if we do not have special impl at least equals works in cases someone gens it over IAstNode
	

	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ArrayAccess node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ArrayCreation node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ArrayInitializer node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ArrayType node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(AssertStatement node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(Assignment node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(BooleanLiteral node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(BreakStatement node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(CastExpression node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(CharacterLiteral node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(CompilationUnit node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ContinueStatement node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(CreationReference node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(Dimension node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(EmptyStatement node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(EnumConstantDeclaration node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ExpressionMethodReference node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(FieldAccess node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(InfixExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(Initializer node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(InstanceofExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(IntersectionType node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(Javadoc node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(LambdaExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(MarkerAnnotation node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(MemberRef node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(MemberValuePair node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(MethodRef node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(MethodRefParameter node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(Modifier node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(NameQualifiedType node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(NullLiteral node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(NumberLiteral node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ParameterizedType node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ParenthesizedExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(PostfixExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(PrefixExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(PrimitiveType node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(QualifiedName node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(QualifiedType node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(SimpleName node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(SimpleType node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(SingleMemberAnnotation node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(StringLiteral node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(SuperFieldAccess node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(SuperMethodReference node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(TagElement node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(TextElement node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ThisExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(ThrowStatement node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(TypeDeclarationStatement node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(TypeLiteral node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(TypeMethodReference node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(TypeParameter node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(UnionType node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		
		return defaultVisit(node);
	}

	@Override
	public boolean visit(WildcardType node) {
		
		return defaultVisit(node);
	}


	
     
}


