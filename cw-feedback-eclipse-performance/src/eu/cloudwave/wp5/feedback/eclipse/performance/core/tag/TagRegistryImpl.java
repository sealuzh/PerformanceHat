package eu.cloudwave.wp5.feedback.eclipse.performance.core.tag;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.dom.ASTNode;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;

public class TagRegistryImpl implements TagRegistry{
	
	//wow, java does and make it easy to declare ADT's I miss Haskell
	// If you dont wnat to read all the inner classes they are (in Haskell)
	// CompositeKey = AstNodeKey String AstNode | MethodKey String MethodLocator | ParamKey String MethodLocator int
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

		@Override
		public String toString() {
			return "AstNodeKey [tagName=" + tagName + ", node=" + node + "]";
		}
		
		
	}
	
	//For now we only accept exact matches
	//Todo; needs way to remove all AstKeys (to clean up after compile)
	private final Map<CompositeKey,Map<Object,List<Object>>> entries = Maps.newHashMap();
	//will be needed by creator to clean old stuff where object is Datasource or the path/feedbackfile
	private final Map<Object,Set<CompositeKey>> keyAssoc = Maps.newHashMap();

	private final FeedbackProject project;

	public TagRegistryImpl(FeedbackProject project) {
		this.project = project;
	}
	
	private final List<Object> extract(CompositeKey key){
		return entries.getOrDefault(key, Collections.emptyMap()).values().stream().flatMap(v -> v.stream()).collect(Collectors.toList());
	}

	@Override
	public List<Object> getTagsForNode(ASTNode node, String tagName) {
		return extract(new AstNodeKey(tagName, node));
	}
	
	public class TagCreatorImpl implements TagCreator{
		
		private final Object key;
		
		public TagCreatorImpl(Object key) {
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
		public void clearAssosiatedTags() {
			Set<CompositeKey> keys = keyAssoc.remove(key);
			if(keys == null) return;
			for(CompositeKey k: keys){
				Map<Object,List<Object>> e = entries.get(k);
				e.remove(key);
				if(e.isEmpty()) entries.remove(k);
			}			
		}

	}

	@Override
	public TagCreator getCreatorFor(FeedbackJavaFile file) {
		return new TagCreatorImpl(file.getFullPath());
	}

}
