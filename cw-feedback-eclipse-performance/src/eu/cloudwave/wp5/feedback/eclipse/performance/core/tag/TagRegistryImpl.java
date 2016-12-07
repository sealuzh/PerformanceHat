package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

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

public class TagRegistryImpl implements TagRegistry{
	
	//For now we only accept exact matches
	//Todo; needs way to remove all AstKeys (to clean up after compile)
	private final Map<CompositeKey,Map<IPath,List<Object>>> entries = Maps.newHashMap();
	//will be needed by creator to clean old stuff where object is Datasource or the path/feedbackfile
	private final Map<IPath,Set<CompositeKey>> keyAssoc = Maps.newHashMap();
	
	private final List<Object> extract(CompositeKey key){
		return entries.getOrDefault(key, Collections.emptyMap()).values().stream().flatMap(v -> v.stream()).collect(Collectors.toList());
	}

	@Override
	public List<Object> getTagsForNode(ASTNode node, String tagName) {
		return extract(new AstNodeKey(tagName, node));
	}
	
	@Override
	public List<Object> getTagsForMethod(MethodLocator loc, String tagName) {
		return extract(new MethodKey(tagName, loc));
	}
	
	public class TagCreatorImpl implements TagCreator{
		
		private final IPath key;
		
		public TagCreatorImpl(IPath key) {
			this.key = key;
		}
		
		private void add(CompositeKey k, Object value){
			keyAssoc.compute(key, (ignore , v) -> {
				if(v == null) v = Sets.newHashSet();
				v.add(k);
				return v;
			});
			entries.compute(k, (ignore, v) -> {
				if(v == null) v = Maps.newHashMap();
				v.compute(key, (ignoreInner, vInner) -> {
					if(vInner == null) vInner = Lists.newLinkedList();
					vInner.add(value);
					return vInner;
				});
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
			Set<CompositeKey> keys = keyAssoc.remove(key);
			if(keys == null) return;
			for(CompositeKey k: keys){
				if(!k.isGlobalKey()){
					Map<IPath,List<Object>> e = entries.get(k);
					e.remove(key);
					if(e.isEmpty()) entries.remove(k);	
				}
			}			
		}
	}

	@Override
	public TagCreator getCreatorFor(FeedbackJavaFile file) {
		return new TagCreatorImpl(file.getFullPath());
	}

}
