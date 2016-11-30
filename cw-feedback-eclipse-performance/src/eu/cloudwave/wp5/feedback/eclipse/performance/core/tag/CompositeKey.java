package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

//wow, java does and make it easy to declare ADT's I miss Haskell
// If you dont wnat to read all the inner classes they are (in Haskell)
// CompositeKey = AstNodeKey String AstNode | MethodKey String MethodLocator | ParamKey String MethodLocator int
interface CompositeKey{
	boolean isGlobalKey();
}