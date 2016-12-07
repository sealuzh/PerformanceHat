package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.dom.ASTNode;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;

public class TagRegistryImpl implements TagRegistry{
	
	//For now we only accept exact matches
	//Todo; needs way to remove all AstKeys (to clean up after compile)
	private final Map<CompositeKey,Map<String,Object>> entries = Maps.newHashMap();
	//will be needed by creator to clean old stuff where object is Datasource or the path/feedbackfile
	private final Map<IPath,Set<CompositeKey>> keyAssoc = Maps.newHashMap();
	
	private final Collection<Object> extract(CompositeKey key){
		return entries.getOrDefault(key, Collections.emptyMap()).values();
	}

	private final Object extract(String pluginId, CompositeKey key){
		return entries.getOrDefault(key, Collections.emptyMap()).get(pluginId);
	}
	
	
	@Override
	public Collection<Object> getTagsForNode(ASTNode node, String tagName) {
		return extract(new AstNodeKey(tagName, node));
	}
	
	
	@Override
	public Object getTagsForNode(String pluginId, ASTNode node, String tagName) {
		return extract(pluginId, new AstNodeKey(tagName, node));
	}
	
	@Override
	public Collection<Object> getTagsForMethod(MethodLocator loc, String tagName) {
		return extract(new MethodKey(tagName, loc));
	}

	@Override
	public Object getTagsForMethod(String pluginId, MethodLocator loc, String tagName) {
		return extract(pluginId, new MethodKey(tagName, loc));
	}

	public class TagCreatorImpl implements TagCreator{
		
		private final IPath fileKey;
		private final String pluginId;
		
		public TagCreatorImpl(IPath key, String pluginId) {
			this.pluginId = pluginId;
			this.fileKey = key;
		}
		
		private void add(CompositeKey k, Object value){
			keyAssoc.compute(fileKey, (ignore , v) -> {
				if(v == null) v = Sets.newHashSet();
				v.add(k);
				return v;
			});
			entries.compute(k, (ignore, v) -> {
				if(v == null) v = Maps.newHashMap();
				v.put(pluginId, value);
				return v;
			});
		}
		
		@Override
		public void addAstNodeTag(ASTNode node, String tagName, Object tagValue) {
			add(new AstNodeKey(tagName, node), tagValue);			
		}

		@Override
		public void addMethodTag(MethodLocator loc, String tagName, Object tagValue) {
			add(new MethodKey(tagName, loc), tagValue);			
		}

		@Override
		public void clearAssosiatedLocalTags() {
			Set<CompositeKey> keys = keyAssoc.remove(fileKey);
			if(keys == null) return;
			for(CompositeKey k: keys){
				if(!k.isGlobalKey()){
					Map<String,Object> e = entries.get(k);
					e.remove(pluginId);
					if(e.isEmpty()) entries.remove(k);	
				}
			}			
		}
	}

	@Override
	public TagCreator getCreatorFor(FeedbackJavaFile file, PerformancePlugin plugin) {
		return new TagCreatorImpl(file.getFullPath(), plugin.getId());
	}

}
