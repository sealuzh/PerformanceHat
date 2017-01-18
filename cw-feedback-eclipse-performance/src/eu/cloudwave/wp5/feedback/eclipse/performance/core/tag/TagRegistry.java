package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import com.google.common.collect.Maps;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;

/**
 * Tag provider that also allows to generate a TagCreator
 * @author Markus Knecht
 *
 */
public interface TagRegistry extends TagProvider {
	
	/**
	 * All the Registries, Each project gets is own registry
	 */
	//TODO: Clean if Project is closed
	static Map<IPath,TagRegistry> registries = Maps.newHashMap();
	
	/**
	 * Creates a TagRegistry for a Project
	 * @param proj is the project this registry is for
	 * @return the TagRegistry
	 */
	public static TagRegistry getProjectTagRegistry(final FeedbackProject proj){
		return registries.computeIfAbsent(proj.getFullPath(), x -> new TagRegistryImpl());
	}
		
	/**
	 * Creates a TagCreator for whichs tag appera in this TagProvider
	 * @param file for which the creator creates Tags
	 * @param plugin which creates the Tags 
	 * @return the TagCreator
	 */
	public TagCreator getCreatorFor(Object identifier, PerformancePlugin plugin);
	
}
