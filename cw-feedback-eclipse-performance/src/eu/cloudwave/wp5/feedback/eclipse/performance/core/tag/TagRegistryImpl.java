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
import eu.cloudwave.wp5.feedback.eclipse.performance.extension.processor.ast.IAstNode;

/**
 * Implementation of the Tag Registry Logic
 * @author Markus
 */
@SuppressWarnings("unused")
public class TagRegistryImpl implements TagRegistry{
	
	//For now we only accept exact matches
	private final Map<CompositeKey,Map<String,Object>> entries = Maps.newHashMap();
	//will be needed by creator to clean old stuff where object is the feedback file path
	private final Map<IPath,Set<CompositeKey>> keyAssoc = Maps.newHashMap();
	
	//helper to get something out of the map safely
	private final Collection<Object> extract(CompositeKey key){
		return entries.getOrDefault(key, Collections.emptyMap()).values();
	}

	//helper to get something out of the map safely
	private final Object extract(String pluginId, CompositeKey key){
		return entries.getOrDefault(key, Collections.emptyMap()).get(pluginId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object> getTagsForNode(IAstNode node, String tagName) {
		return extract(new AstNodeKey(tagName, node.getEclipseAstNode()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getTagsForNode(String pluginId, IAstNode node, String tagName) {
		return extract(pluginId, new AstNodeKey(tagName, node.getEclipseAstNode()));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Object> getTagsForMethod(MethodLocator loc, String tagName) {
		return extract(new MethodKey(tagName, loc));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getTagsForMethod(String pluginId, MethodLocator loc, String tagName) {
		return extract(pluginId, new MethodKey(tagName, loc));
	}

	//Class that provides insert logic
	private class TagCreatorImpl implements TagCreator{
		private final IPath fileKey; 			//The File it is for
		private final String pluginId;			//The Plugin it is for
		
		public TagCreatorImpl(IPath key, String pluginId) {
			this.pluginId = pluginId;
			this.fileKey = key;
		}
		
		//Helper, that adds the key and adds a association with the file needed for later deletions
		private void add(CompositeKey k, Object value){
			//Add to keyLookup (needed for cleaning)
			keyAssoc.compute(fileKey, (ignore , v) -> {
				if(v == null) v = Sets.newHashSet();
				v.add(k);
				return v;
			});
			
			//Add the actual Tag
			entries.compute(k, (ignore, v) -> {
				if(v == null) v = Maps.newHashMap();
				v.put(pluginId, value);
				return v;
			});
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addAstNodeTag(IAstNode node, String tagName, Object tagValue) {
			add(new AstNodeKey(tagName, node.getEclipseAstNode()), tagValue);			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addMethodTag(MethodLocator loc, String tagName, Object tagValue) {
			add(new MethodKey(tagName, loc), tagValue);			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void clearAssosiatedLocalTags() {
			//get all keys associated with the associated file
			Set<CompositeKey> keys = keyAssoc.remove(fileKey);
			if(keys == null) return;
			//for each remove all tags with the associated plugin
			for(CompositeKey k: keys){
				//we don't remove global ones
				if(!k.isGlobalKey()){
					//get all
					Map<String,Object> e = entries.get(k);
					//remove the one from this plugin
					e.remove(pluginId);
					//if no more remove whole map
					if(e.isEmpty()) entries.remove(k);	
				}
			}			
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TagCreator getCreatorFor(FeedbackJavaFile file, PerformancePlugin plugin) {
		return new TagCreatorImpl(file.getFullPath(), plugin.getId());
	}

}
