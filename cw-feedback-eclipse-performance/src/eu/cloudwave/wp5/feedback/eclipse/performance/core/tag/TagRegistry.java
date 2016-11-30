package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import com.google.common.collect.Maps;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;

public interface TagRegistry extends TagProvider {
	
	//Todo: clean if project is closed/deleted
	static Map<IPath,TagRegistry> registries = Maps.newHashMap();
	
	public static TagRegistry getProjectTagRegistry(final FeedbackProject proj){
		return registries.computeIfAbsent(proj.getFullPath(), x -> new TagRegistryImpl());
	}
	
	//Todo: needs public registry for inter ast Markers
	//public TagCreator getCreatorFor(FeedbackProject proj);	
	public TagCreator getCreatorFor(FeedbackJavaFile file);
	
}
