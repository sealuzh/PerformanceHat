package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformanceHatExtension;

/**
 * This class provides functionality to make a topological ordering on a dependency graph.
 * It is specialized on a graph where the nodes are PerformancePlugins and the edges are uses Tag consumed/produced relationships (from consumer to producer)
 * It also supports Optional edges which are only satisfied if possible without producing a cycle
 * @author Markus Knecht
 *
 */
public class DependencyOrderer {
	
	@SuppressWarnings("serial")
	/**
	 * Exception thrown if no topological order exist that satisfies all dependencies (producer before consumer)
	 * @author Markus Knecht
	 */
	static class UnsatisfiedDependencyGraphException extends RuntimeException{}
	
	//helper to make handling lists in maps easier
	private static List<Integer> addOrCreateList(List<Integer> list, int value){
		if(list == null) list = Lists.newArrayList();
		list.add(value);
		return list;
	}
	
	//orders a list of dependency so that if a needs something provided in b, b is before a in the list
	//if possible it takes optional dependencies into account, if they lead to a cycle or are not present, they may be ignored
	//its basically a topological search
	public static List<PerformanceHatExtension> order(List<PerformanceHatExtension> input){
		int markers = input.size();
		
		//Check if everithing is avaiable
		Set<String> allProvided = Sets.newHashSet();
		for(PerformanceHatExtension m:input) allProvided.addAll(m.getProvidedTags());
		for(PerformanceHatExtension m:input) if(!allProvided.containsAll(m.getRequiredTags())) throw new UnsatisfiedDependencyGraphException();
		
		//build the graph structure (only the necessary parts)
		//We use ints to identify nodes, makes it easier, in addition this represents the nodes not yet removed from the graph
		Map<Integer, PerformanceHatExtension> nodes = Maps.newHashMap();
		//out degrees of the nodes
		int[] outDegree = new int[markers];
		int[] optionalOutDegree = new int[markers];
		
		//Mappings from Tag -> Consumers
		Map<String,List<Integer>> dependencies = Maps.newHashMap();
		Map<String,List<Integer>> optionalDependencies = Maps.newHashMap();
		
		//Calculate initial nodes and its dependencies
		int c = 0;
		for(PerformanceHatExtension m:input) {
	    	final int cur = c;
		    for(String dep: m.getRequiredTags()) dependencies.compute(dep, (String k, List<Integer> list) -> addOrCreateList(list,cur));
		    for(String dep: m.getOptionalRequiredTags()) optionalDependencies.compute(dep, (String k, List<Integer> list) -> addOrCreateList(list,cur));
		    nodes.put(c++,m);
		}
		
		//Calculate each nodes out degree (splited in optional and mandatory)
		for(PerformanceHatExtension m: nodes.values()){
			 for(String prov: m.getProvidedTags()){
				 for(int d: dependencies.getOrDefault(prov, Collections.emptyList())) outDegree[d]++;
				 for(int d: optionalDependencies.getOrDefault(prov, Collections.emptyList())) optionalOutDegree[d]++;
			 }
		}
		
		//do the topological search
		List<PerformanceHatExtension> sortedNodes = Lists.newArrayList();
		while (!nodes.isEmpty()) {
			int cand = -1;					//canditate for removal
			int optOut = Integer.MAX_VALUE; //curents candidates optional outdegree
			for(Integer i:nodes.keySet()){
				//if outdegree == 0 its save to remove, optional outdegree must not be 0 but 0 would be optimal, so we remove the one nearest to 0
				if(outDegree[i] == 0 && optionalOutDegree[i] < optOut){
					cand = i;
					optOut = optionalOutDegree[i];
				}
			}
			//if they is no node with out degree 0 (in mandatory part) then we have a cycle and need to throw
			if(cand == -1) throw new UnsatisfiedDependencyGraphException();
			//remove the candidate from the graph and add it to the sorted List
			PerformanceHatExtension m = nodes.remove(cand);
			sortedNodes.add(m);
			//Update outDegree of all nodes, which depend on the current node
			for(String prov: m.getProvidedTags()){
				 for(int d: dependencies.getOrDefault(prov, Collections.emptyList())) outDegree[d]--;
				 for(int d: optionalDependencies.getOrDefault(prov, Collections.emptyList())) optionalOutDegree[d]--;
			}
			
		}
		
		//return the Sorted Plugins
		return sortedNodes;
	}
}
