package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;

import com.google.common.collect.Maps;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.feedbackhandler.FeedbackHandlerEclipseClient;

public class TagRegistryImpl implements TagRegistry{
	
	private interface CompositeKey{};
	private static final class AstNodeKey implements CompositeKey{
		public final String tagName;
		public final ASTNode node;
		public AstNodeKey(String tagName, ASTNode node) {
			this.tagName = tagName;
			this.node = node;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + node.hashCode();
			result = prime * result + tagName.hashCode();
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof AstNodeKey)) return false;
			AstNodeKey other = (AstNodeKey) obj;
			if (!node.equals(other.node)) return false;
			if (!tagName.equals(other.tagName)) return false;
			return true;
		}
	}
	
	private static final class MethodKey implements CompositeKey{
		public final String tagName;
		public final MethodLocator loc;
		public MethodKey(String tagName, MethodLocator loc) {
			this.tagName = tagName;
			this.loc = loc;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + loc.hashCode();
			result = prime * result + tagName.hashCode();
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof MethodKey)) return false;
			MethodKey other = (MethodKey) obj;
			if (!loc.equals(other.loc)) return false;
			if (!tagName.equals(other.tagName)) return false;
			return true;
		}
	}
	
	private static final class ParamKey implements CompositeKey{
		public final String tagName;
		public final MethodLocator loc;
		public final int paramPos;
		public ParamKey(String tagName, MethodLocator loc, int paramPos) {
			this.tagName = tagName;
			this.loc = loc;
			this.paramPos = paramPos;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + loc.hashCode();
			result = prime * result + paramPos;
			result = prime * result + tagName.hashCode();
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (!(obj instanceof ParamKey)) return false;
			ParamKey other = (ParamKey) obj;
			if (!loc.equals(other.loc)) return false;
			if (paramPos != other.paramPos) return false;
			if (!tagName.equals(other.tagName)) return false;
			return true;
		}		
	}
	
	//For now we only accept exact matches
	private final Map<CompositeKey,List<Object>> entries = Maps.newHashMap();
	//will be needed by creator to clean old stuff where object is Datasource or the path/feedbackfile
	private final Map<Object,Set<CompositeKey>> keyAssoc = Maps.newHashMap();

	private final FeedbackHandlerEclipseClient fddClient = PerformancePluginActivator.instance(FeedbackHandlerEclipseClient.class);
	private final FeedbackProject project;

	public TagRegistryImpl(FeedbackProject project) {
		this.project = project;
	}

	@Override
	public List<Object> getTagsForMethod(MethodLocator method, String tagName) {
		if(tagName.equals("AvgExcecutionTime")){
				Double measure = fddClient.avgExecTime(project,method.className, method.methodName, method.argumentTypes);
				if(measure != null)  return Collections.singletonList(measure);
		} else if(tagName.equals("CollectionSize")){
			     Double averageSize = fddClient.collectionSize(project,method.className, method.methodName, method.argumentTypes, "");
			     if(averageSize != null)  return Collections.singletonList(averageSize);
		} 
		return entries.getOrDefault(new MethodKey(tagName, method), Collections.emptyList());
	}

	@Override
	public List<Object> getTagsForParam(MethodLocator method, int paramPosition, String tagName) {
		if(tagName.equals("CollectionSize")){
		     Double averageSize = fddClient.collectionSize(project,method.className, method.methodName, method.argumentTypes, paramPosition+"");
		     if(averageSize != null)  return Collections.singletonList(averageSize);
		} 
		return entries.getOrDefault(new ParamKey(tagName, method, paramPosition), Collections.emptyList());

	}

	@Override
	public List<Object> getTagsForNode(ASTNode node, String tagName) {
		return entries.getOrDefault(new AstNodeKey(tagName, node), Collections.emptyList());
	}

	
	@Override
	public TagCreator getCreatorFor(Object dataSource) {
		// TODO just a stub to prevent null pointers
		return new TagCreatorImpl();
	}

	@Override
	public TagCreator getCreatorFor(FeedbackJavaFile file) {
		// TODO just a stub to prevent null pointers
		return new TagCreatorImpl();
	}

}
