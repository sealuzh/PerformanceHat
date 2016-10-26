package eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;

public class ForStatement extends AAstNode<org.eclipse.jdt.core.dom.ForStatement> implements LoopStatement {
	
	public ForStatement(org.eclipse.jdt.core.dom.ForStatement forStatement, ProgrammMarkerContext ctx) {
		super(forStatement,ctx);
	}

	//Temporary until real Tag Collector is implemented
	@Override
	protected boolean corrolatesWith(Procedure procedure) {
		return false;
	}

	@Override
	protected int getStartPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getEndPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<CollectionSource> getSourceCollection() {
		//todo check if theire is 0 -> x.length/x.size, then return x.length
		//todo: Ev Create IterationDescriptor, start, step, end (each can be int or length of(AstNode)
		return Optional.absent();
	}

}
