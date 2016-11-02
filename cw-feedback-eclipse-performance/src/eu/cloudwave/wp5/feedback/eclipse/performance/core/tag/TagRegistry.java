package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import com.google.common.collect.Maps;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;

//Todo: Track from which CompilationUnit/DataSource Some infos come
	//   An element is either bound to a datasource
	//						  or to a compilationUnit
	//    if its reevaluated, the corresponding input is cleared first

//Todo: we will need one per Project
public interface TagRegistry extends TagProvider {
	
	//Todo: clean if project is closed/deleted
	static Map<IPath,TagRegistry> registries = Maps.newHashMap();
	
	public static TagRegistry getProjectTagRegistry(final FeedbackProject proj){
		return registries.computeIfAbsent(proj.getFullPath(), x -> new TagRegistryImpl(proj));
	}
	
	//In CreatorNow @ Carlos: if you already use this please programm against TagCreator as the interface
	//public void addMethodTag(MethodLocator method, String tagName, Object tagValue);
	//public void addParameterTag(MethodLocator method, int paramPosition, String tagName, Object tagValue);
	//public void addMethodAnnotationTag(MethodLocator method, String anotationName, /*Simple for now*/ String[] paramValues, String tagName, Object tagValue);
	//public void addAstNodeTag(ASTNode node, String tagName, Object tagValue);

	public TagCreator getCreatorFor(FeedbackJavaFile file);
	public TagCreator getCreatorFor(Object dataSource);
	
	
	//Read Methods open
	


	
}
