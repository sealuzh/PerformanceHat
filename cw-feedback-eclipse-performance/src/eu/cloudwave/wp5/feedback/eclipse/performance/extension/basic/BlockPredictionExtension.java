package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.Modifier;

import com.google.common.collect.Lists;

import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.PerformanceBuilder;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.MethodLocator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagCreator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.tag.TagProvider;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformanceHatExtension;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block.APrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block.BlockPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block.BranchPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block.BranchingPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block.LoopPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.prediction.block.MethodCallPrediction;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.PredictionNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.AstRoot;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Branching;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ImplementorTagLookupHelper;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Invocation;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.MethodDeclaration;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Try;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor.BlockTimePredictor;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor.BlockTimePredictorCallback;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.visitor.PerformanceVisitor;

/**
 * A Predictor using the BlockTimePrediction Framework to predict method executioin times
 * @author Markus Knecht
 *
 */
public class BlockPredictionExtension implements PerformanceHatExtension, BlockTimePredictorCallback{

	  private static final String ID = "eu.cloudwave.wp5.feedback.eclipse.performance.extension.example.BlockPredictionPlugin";
	  private static final String COLLECTION_SIZE_TAG = "CollectionSize";
	  private static final String AVG_EXEC_TIME_TAG = "AvgExecutionTime";
	  public static final String AVG_PRED_TIME_TAG = "AvgPredictionTime";
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public String getId() {
			return ID;
	  }	  
	
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public List<String> getRequiredTags() {
		  	return Lists.asList(COLLECTION_SIZE_TAG,AVG_EXEC_TIME_TAG, new String[]{});
	  }
	  
	  /**
	   * {@inheritDoc}
	   */
	  public List<String> getProvidedTags(){
		  return Collections.singletonList(AVG_PRED_TIME_TAG);
	  }
	  
	  
	  /**
	   * {@inheritDoc}
	   */
	  @Override
	  public void processPerformanceAst(AstRoot ast/*, AstRoot ignoreOldRoot*/) {
		  long t0 = System.nanoTime();
		  ast.accept(createPerformanceVisitor(ast.getContext()));
	  }

	  private PerformanceVisitor createPerformanceVisitor(final AstContext rootContext) {
		  //Visitor that visits all method declaration and predict excecution times
		  return new PerformanceVisitor() {
			  @Override
			  public PerformanceVisitor visit(MethodDeclaration method) {
					//Use BlockTimeCollector to measure the method
					return new BlockTimePredictor(BlockPredictionExtension.this){
						@Override
						public PredictionNode generateResults() {
							//simply sum up contributions
							final double methodTimeSumPred = sumPred(excecutionTimeStats);
							final double methodTimeSumMes = sumMes(excecutionTimeStats);

							//generate a generic Block Prediction for method body
							final PredictionNode methodN = new BlockPrediction("method declaration", methodTimeSumPred, methodTimeSumMes, excecutionTimeStats);	
							//publish the results as a Tag (Consumed by Hotspot at least)
							method.attachPublicTag(AVG_PRED_TIME_TAG, methodN);
							//method.attachTag(AVG_PRED_TIME_TAG, methodN);
							return methodN;
						} 
					}; 
				} 
		  };	
	  } 
	  
	private static double sumPred(List<PredictionNode> nodes){
		return nodes.stream().mapToDouble(n -> ((APrediction)n).avgTimePred).sum();
	}

	private static double sumMes(List<PredictionNode> nodes){
		return nodes.stream().mapToDouble(n -> ((APrediction)n).avgTimeMes).sum();
	}
	
	/**
	 * Predicts based on the AVG_EXEC_TIME_TAG and AVG_PRED_TIME_TAG
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode invocationEncountered(Invocation invocation) {
		Collection<Double> measurements = invocation.getDoubleTags(BlockPredictionExtension.AVG_EXEC_TIME_TAG);
		//Consume own Tags, only works over multiple compiles or else we would need complext tag management
		Collection<Object> predTags = invocation.getTags(BlockPredictionExtension.AVG_PRED_TIME_TAG);
		//final values once for prediction prefered and once for measurement prefered
		//Only consider other BlockPredictors results
		Collection<Double> predPref = predTags.stream().filter(p -> p instanceof APrediction).map(p -> ((APrediction)p).avgTimePred).collect(Collectors.toList());
		Collection<Double> mesPref = null;
		
		//if we do not have mesurements use predictions
		if(measurements.isEmpty()){
			mesPref = predTags.stream().map(p -> ((APrediction)p).avgTimeMes).collect(Collectors.toList());
		} else {
			mesPref = measurements;
		}
		
		//if we do not have predictions use measurement
		if(predPref.isEmpty()) {
			predPref = mesPref;
		}

		//Calc Avg Prediction
		double avgExecTimePred = 0.0;
		for(double avgT : predPref) {
			avgExecTimePred+=avgT;
		}
		if(predPref.size() != 0)avgExecTimePred /= predPref.size();
	
		//Calc Avg mesurement
		double avgExecTimeMes= 0.0;
		for(double avgT : mesPref) {
			avgExecTimeMes+=avgT;
		}
		if(mesPref.size() != 0)avgExecTimeMes /= mesPref.size();
		
		if(avgExecTimeMes == 0 && avgExecTimePred == 0) return null;
		MethodLocator loc = invocation.createCorrespondingMethodLocation();
		return new MethodCallPrediction(loc, avgExecTimePred, avgExecTimeMes);
	}

	/**
	 * Predicts based on loop Header and body and the number of iterations
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode loopMeasured(List<PredictionNode> iterationExecutionTime, List<PredictionNode> headerExecutionTime, Loop loop){

		//do we know the iterations
		Double avgItersLookup = LoopUtils.findNumOfIterations(loop);
		//at least do one iteration
		double avgIters = (avgItersLookup == null)?1:avgItersLookup;
		final double iterExcecTimePred = sumPred(iterationExecutionTime);
		final double headerExectimePred =  sumPred(headerExecutionTime);
		final double iterExcecTimeMes = sumMes(iterationExecutionTime);
		final double headerExectimeMes  =  sumMes(headerExecutionTime);
		
		//Predict n*body + 1*header
		final APrediction headerN = new BlockPrediction("loop header", headerExectimePred, headerExectimeMes, headerExecutionTime);
		final APrediction bodyN = new BlockPrediction("loop body", iterExcecTimePred, iterExcecTimeMes, iterationExecutionTime);		
		double loopTimePred = (avgIters*bodyN.avgTimePred)+headerN.avgTimePred;
		double loopTimeMes = (avgIters*bodyN.avgTimeMes)+headerN.avgTimeMes;
		if(loopTimeMes == 0 && loopTimePred == 0) return null;
		final PredictionNode loopN = new LoopPrediction(loopTimePred, loopTimeMes ,(avgItersLookup == null)?Double.NaN:avgItersLookup, bodyN, headerN);	
		

		//Publish prediction per tag
		loop.attachTag(AVG_PRED_TIME_TAG, loopN);
		return loopN;
	}

	/**
	 * Predicts based on all branches (assumes equal likelihood)
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode branchingMeasured(List<PredictionNode> conditionExecutionTime, List<List<PredictionNode>> branchExecutionTimes, Branching branch){
		 final List<PredictionNode> branchNodes = Lists.newArrayList();
		 //for the condition simply sum up
		 final PredictionNode conditionN = new BlockPrediction("condition", sumPred(conditionExecutionTime), sumMes(conditionExecutionTime), conditionExecutionTime);
		 //Create the Branch Prediction by summing up and deviding through number of branches
		 double totPred = 0;
		 double totMes = 0;
		 int branchCount = branchExecutionTimes.size();
		 if(branch.isSkippable())branchCount++; //shows if their is a chance for zero branch in that case we giv it also equal likelyhood
		 int i = 0;
		 //factor
		 double part = 1.0/branchCount;
		 //calc each branch
		 for(List<PredictionNode> bet:branchExecutionTimes) {
			 final double betSumPred = sumPred(bet);
			 final double betSumMes = sumMes(bet);
			 
			 branchNodes.add(new BranchPrediction((++i),part, betSumPred/branchCount, betSumMes/branchCount , bet));
			 totPred+=betSumPred;
			 totMes+=betSumMes;

		 }
		 //average
		 totPred /= branchCount;
		 totMes /= branchCount;

		 if(totMes == 0 && totPred == 0) return null;

		 //main prediction node
		 final PredictionNode branchN = new BranchingPrediction(totPred,totMes, conditionN, branchNodes);

		 //Publish prediction per tag
		 branch.attachTag(AVG_PRED_TIME_TAG, branchN);
		 
		 return branchN;
	}

	/**
	 * Predicts based on try,finally but ignores catches
	 * {@inheritDoc}
	 */
	@Override
	public PredictionNode tryMeasured(List<PredictionNode> tryExecutionTime, List<PredictionNode> finnalyExecutionTime, List<List<PredictionNode>> catchExccutionTimes, Try tryStm) {
		//Is a finally present
		if(finnalyExecutionTime == null){
			//if not its simply the sum of the try
			return new BlockPrediction("try",sumPred(tryExecutionTime), sumMes(tryExecutionTime), tryExecutionTime);
		} else {
			//else record both try and finally
			//the try
			final double tryTimeSumPred = sumPred(tryExecutionTime);
			final double tryTimeSumMes = sumMes(tryExecutionTime);

			final PredictionNode bodyN = new BlockPrediction("body", tryTimeSumPred, tryTimeSumMes, tryExecutionTime);
			//the finally
			final double finallyTimeSumPred = sumPred(finnalyExecutionTime);
			final double finallyTimeSumMes = sumMes(finnalyExecutionTime);

			if(tryTimeSumMes+finallyTimeSumMes == 0 && tryTimeSumPred+finallyTimeSumPred == 0) return null;

			final PredictionNode finallyN = new BlockPrediction("finally", finallyTimeSumPred, finallyTimeSumMes, finnalyExecutionTime);		
			//thr full try/finally
			final PredictionNode tryN = new BlockPrediction("try", tryTimeSumPred+finallyTimeSumPred,tryTimeSumMes+finallyTimeSumMes, bodyN, finallyN);
			
			 //Publish prediction per tag
			tryStm.attachTag(AVG_PRED_TIME_TAG, tryN); 
			
			return tryN;
		}
	}
	
}
