package eu.cloudwave.wp5.feedback.eclipse.performance.extension.example;

import java.util.List;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ProgrammMarkerContext;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.IAstNode;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.ast.LoopStatement;

public class LoopUtils {
	
	private static final String COLLECTION_SIZE_TAG = "CollectionSize";

	
	public static Double findNumOfIterations(LoopStatement loop, ProgrammMarkerContext context){
		  final Optional<IAstNode> collectionSource = loop.getSourceNode();
		  if (collectionSource.isPresent()) {
			 List<Double> colSize = collectionSource.get().getDoubleTags(COLLECTION_SIZE_TAG);
			 if(!colSize.isEmpty()){
				 double averageSize = 0.0; 
				 for(double size :colSize)averageSize+=size;
				 averageSize /= colSize.size();
	        	 return averageSize;	        	
			 } 
		   }
		 return null;
	  }

}
