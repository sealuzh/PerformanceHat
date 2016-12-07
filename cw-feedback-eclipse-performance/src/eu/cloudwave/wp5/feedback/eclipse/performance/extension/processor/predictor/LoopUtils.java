package eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.predictor;

import java.util.Collection;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.AstContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;

public class LoopUtils {
	
	private static final String COLLECTION_SIZE_TAG = "CollectionSize";

	
	public static Double findNumOfIterations(Loop loop, AstContext context){
		  final Optional<IAstNode> collectionSource = loop.getSourceNode();
		  if (collectionSource.isPresent()) {
			 Collection<Double> colSize = collectionSource.get().getDoubleTags(COLLECTION_SIZE_TAG);
			 if(!colSize.isEmpty()){
				 double averageSize = 0.0; 
				 for(double size :colSize)averageSize+=size;
				 averageSize /= colSize.size();
	        	 return averageSize;	        	
			 } 
		   } else {
			   final Optional<Integer> its = loop.getIterations();
			   if(its.isPresent()) return its.get().doubleValue();
 		   }
		 return null;
	  }

}
