package eu.cloudwave.wp5.feedback.eclipse.performance.core.builders;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.performance.extension.PerformancePlugin;


public class DependencyOrderer {
	
	static class UnsatisfiedDependencyGraphException extends RuntimeException{}
	
	private static List<Integer> addOrCreateList(List<Integer> list, int value){
		if(list == null) list = Lists.newArrayList();
		list.add(value);
		return list;
	}
	
	//orders a list of dependency so that if a needs something provided in b, b is before a in the list
	//if possible it takes optional dependencies into account, if they lead to a cycle or are not present, they may be ignored
	//its basically a topological search
	public static List<PerformancePlugin> order(List<PerformancePlugin> input){
		int markers = input.size();
		
		//Check if everithing is avaiable
		Set<String> allProvided = Sets.newHashSet();
		for(PerformancePlugin m:input) allProvided.addAll(m.getProvidedTags());
		for(PerformancePlugin m:input) if(!allProvided.containsAll(m.getRequiredTags())) throw new UnsatisfiedDependencyGraphException();
		
		//build the graph structure (only the necessary parts)
		Map<Integer, PerformancePlugin> open = Maps.newHashMap();
		int[] outDegree = new int[markers];
		int[] optOutDegree = new int[markers];
		
		Map<String,List<Integer>> dependencies = Maps.newHashMap();
		Map<String,List<Integer>> optDependencies = Maps.newHashMap();
		
		int c = 0;
		for(PerformancePlugin m:input) {
	    	final int cur = c;
		    for(String dep: m.getRequiredTags()) dependencies.compute(dep, (String k, List<Integer> list) -> addOrCreateList(list,cur));
		    for(String dep: m.getOptionalRequiredTags()) optDependencies.compute(dep, (String k, List<Integer> list) -> addOrCreateList(list,cur));
		    open.put(c++,m);
		}
		
		for(PerformancePlugin m: open.values()){
			 for(String prov: m.getProvidedTags()){
				 for(int d: dependencies.getOrDefault(prov, Collections.emptyList())) outDegree[d]++;
				 for(int d: optDependencies.getOrDefault(prov, Collections.emptyList())) optOutDegree[d]++;
			 }
		}
		
		//do the topological search
		List<PerformancePlugin> result = Lists.newArrayList();
		while (!open.isEmpty()) {
			int cand = -1;
			int optOut = Integer.MAX_VALUE;
			for(Integer i:open.keySet()){
				if(outDegree[i] == 0 && optOutDegree[i] < optOut){
					cand = i;
					optOut = optOutDegree[i];
				}
			}
			if(cand == -1) throw new UnsatisfiedDependencyGraphException();
			PerformancePlugin m = open.remove(cand);
			result.add(m);
			for(String prov: m.getProvidedTags()){
				 for(int d: dependencies.getOrDefault(prov, Collections.emptyList())) outDegree[d]--;
				 for(int d: optDependencies.getOrDefault(prov, Collections.emptyList())) optOutDegree[d]--;
			}
			
		}
		
		return result;
	}
}
