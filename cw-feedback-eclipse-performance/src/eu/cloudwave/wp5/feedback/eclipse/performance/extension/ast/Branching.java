package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class Branching extends AAstNode<org.eclipse.jdt.core.dom.ASTNode>{
	
	  private org.eclipse.jdt.core.dom.IfStatement ifStat = null;
	  private org.eclipse.jdt.core.dom.ConditionalExpression condStat = null;
	  private org.eclipse.jdt.core.dom.SwitchStatement switchStat = null;


	  public Branching(org.eclipse.jdt.core.dom.IfStatement ifStat, ProgrammMarkerContext ctx) {
		 super(ifStat,ctx);
		 this.ifStat = ifStat;
      }
 	  
 	  public Branching(org.eclipse.jdt.core.dom.ConditionalExpression condStat, ProgrammMarkerContext ctx) {
		 super(condStat,ctx);
		 this.condStat = condStat;
      }
 	  
 	  public Branching(org.eclipse.jdt.core.dom.SwitchStatement switchStat, ProgrammMarkerContext ctx) {
 		 super(switchStat,ctx);
 		 this.switchStat = switchStat;
       }
 	  
 	  public List<IAstNode> getBranches(){
 		 List<IAstNode> res = Lists.newArrayList();
 		 if(ifStat != null){
 			 //Posiible is block
 			 org.eclipse.jdt.core.dom.Statement sm = ifStat.getThenStatement();
 			 if(sm != null) res.add(IAstNode.fromEclipseAstNode(sm, ctx));
 			 sm = ifStat.getElseStatement();
 			 if(sm != null) res.add(IAstNode.fromEclipseAstNode(sm, ctx));
 		 } else if (condStat != null) {
 			 org.eclipse.jdt.core.dom.Expression exp = condStat.getThenExpression();
 			 if(exp != null) res.add(IAstNode.fromEclipseAstNode(exp, ctx));
 			 exp = condStat.getElseExpression();
 			 if(exp != null) res.add(IAstNode.fromEclipseAstNode(exp, ctx));
 		 } else if (switchStat != null){
 			 for(Object st:switchStat.statements()){
 				res.add(IAstNode.fromEclipseAstNode((ASTNode)st, ctx));
 			 }
 		 }
 		 return res;
 	  }
 	  
 	  public IAstNode getCondition(){
 		 if(ifStat != null){
 			return IAstNode.fromEclipseAstNode(ifStat.getExpression(), ctx);
 		 } else if (condStat != null) {
 			return IAstNode.fromEclipseAstNode(condStat.getExpression(), ctx);
 		 } else if (switchStat != null){
  			return IAstNode.fromEclipseAstNode(switchStat.getExpression(), ctx);
 		 }
 		 return null;
 	  }
 	  
 	  public boolean isSkippable(){
 		 if(ifStat != null){
  			return ifStat.getElseStatement() == null;
  		 } else if (condStat != null) {
  			return false;
  		 } else if (switchStat != null){
   			return false;
  		 }
 		 return false;
 	  }
	

}
