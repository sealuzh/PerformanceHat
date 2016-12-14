package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

//wow, java does not make it easy to declare ADT's I miss Haskell/Scala/Kotlin((Any other resonalbe language)
/**
 * An Abstract Data type for composite keys usable in maps
 * @author Markus Knecht
 *
 */
interface CompositeKey{
	/**
	 * Some Keys are globally valid some only for the local TagProvider
	 */
	boolean isGlobalKey();
}