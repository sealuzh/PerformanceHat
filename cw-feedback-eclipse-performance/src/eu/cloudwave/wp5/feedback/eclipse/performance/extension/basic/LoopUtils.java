package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic;

import java.util.Collection;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.Loop;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.ParameterDeclaration;

/**
 * A utility class to analyze loops
 * @author Markus Knecht
 *
 */
public class LoopUtils {
	
	//Tag to use
	private static final String COLLECTION_SIZE_TAG = "CollectionSize";

	/**
	 * finds the number of iteration in a loop based on collection size or other ast information
	 * @param loop to lookup iterations
	 * @return the number of iterations (Average)
	 */
	public static Double findNumOfIterations(Loop loop){
		  //has the loop a loop source
		  final Optional<IAstNode> collectionSource = loop.getSourceNode();
		  if (collectionSource.isPresent()) {
			  		  
			 //has it a Collection size Tag
			 Collection<Double> colSize = collectionSource.get().getDoubleTags(COLLECTION_SIZE_TAG);
			 //we average all of them out and use that
			 if(!colSize.isEmpty()){
				 double averageSize = 0.0; 
				 for(double size :colSize)averageSize+=size;
				 averageSize /= colSize.size();
	        	 return averageSize;	        	
			 } 
		   } else {
			   //is the size statically known then use that
			   final Optional<Integer> its = loop.getIterations();
			   if(its.isPresent()) return its.get().doubleValue();
 		   }
		 return null;
	  }

}
