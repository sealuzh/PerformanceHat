package eu.cloudwave.wp5.feedback.eclipse.performance.core.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaFile;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaProject;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.java.FeedbackJavaResourceFactory;
import eu.cloudwave.wp5.feedback.eclipse.performance.PerformancePluginActivator;
import eu.cloudwave.wp5.feedback.eclipse.performance.core.builders.PerformanceBuilder;

/**
 * Helper class for Impact Propagation
 * Warning uses Internal Eclipse stuff
 * TODO: Find solution without internals
 * @author Markus Knecht
 *
 */
@SuppressWarnings("restriction")
public class ImpactPropagator {

	/**
	 * Propagates the impact of a change in a Member (normally a method)
	 * By propagation reaanalysation is meant
	 * @param mem the member to propagate the impact from
	 * @throws CoreException
	 */
	public static void calculateImpact(IMember mem, IProgressMonitor monitor) throws CoreException{
		 //Queue of unprocessed members
		 Queue<IMember> open = Lists.newLinkedList();
		 open.add(mem); //Start with the source
		 //Set to remember already processed ones to prevent cycles
		 Set<IMember> visited = Sets.newHashSet();
		 //inedges used as intermediary step for the topological sort to get out most of one propagation
		 Map<IPath,Set<IPath>> inedges = Maps.newHashMap(); 
		 //just an association between Paths and Files (we work on paths because I do not know how equality on Ifile is defined)
		 Map<IPath,IFile> targets = Maps.newHashMap(); 
		 SubMonitor subMonitor = SubMonitor.convert(monitor);
		 //Create the internal CallHierarchy Lookup class
		 CallHierarchy hierarchy = new CallHierarchy();
		 //Limit the search Space to workspace
		 IJavaSearchScope searchScope = SearchEngine.createWorkspaceScope();
		 hierarchy.setSearchScope(searchScope);
		 
		 //As long as more dependencies are found continue
		 while(!open.isEmpty()){
			 //get next
			 IMember cur = open.poll();
			 //check if already done
			 if(visited.contains(cur)) continue;

			 //Look up Callers
			 MethodWrapper[] callerWrapper = hierarchy.getCallerRoots(new IMember[]{cur});
			 
			 //Collect the calling members and the calling files
			 ArrayList<IMember> methodCalls = new ArrayList<IMember>();
			 Set<IPath> files = Sets.newHashSet();
			 //boolean inter = false;
			 
			 for (int i1 = 0; i1 < callerWrapper.length; i1++) {
			     for(MethodWrapper wrapper: callerWrapper[i1].getCalls(subMonitor.setWorkRemaining(100).newChild(1))){
			    	 IMember member = wrapper.getMethodCall().getMember(); 
				     methodCalls.add(member);
				     //if(!inter && cur.getPath().equals(member.getPath())) inter = true;
				     files.add(member.getPath());
			     }
			 }

			 /*if(inter){
				 mult.compute(cur.getPath(), (k,v)-> )
			 }*/
			 //remembrer the file for a path
			 targets.putIfAbsent(cur.getPath(),(IFile)cur.getResource());
			 //Set the in edges
			 inedges.merge(cur.getPath(), files, (v1,v2) -> Sets.union(v1, v2));
			 //mark as visited
			 visited.add(cur);
			 //mark for further resolving
			 open.addAll(methodCalls);				 
				 
		 }
		 
		 
		 //Calc Outdegrees from in degrees
		 Map<IPath,Integer> outDegree = Maps.newHashMap(); 
		 //init to 0: ensures that all are in and we do not get nulls later
		 for(IPath m:inedges.keySet()){
			 outDegree.put(m, 0);
		 }
		 //accumulate calls
		 for(Set<IPath> ms: inedges.values()){
			 for(IPath m:ms){
				 outDegree.compute(m, (k,v) -> (v==null)?1:v+1); 
			 }
		 }
		 
		 List<IFile> jobs = Lists.newArrayList();
		 //Do the topological search iteratively (as long as their are nodes in the graph)
		 while (!outDegree.isEmpty()) {
			 //all the elems with smallest outDegree (we take non zeroes, so we can still calc cycles, but the info may not be accurate)
			 List<IPath> smallest = Lists.newArrayList();
			 int smallestNum = Integer.MAX_VALUE;
			 
			 //find smallests
			 for(Map.Entry<IPath,Integer> kv:outDegree.entrySet() ){
				 if(kv.getValue() == smallestNum){
					 smallest.add(kv.getKey());
				 } else if(kv.getValue() < smallestNum){
					 smallest.clear(); 
					 smallestNum = kv.getValue();
					 smallest.add(kv.getKey());
				 }
			 }
			 
			 //Remove all smallest and reanalyze them
			 for(IPath p:smallest){
				 outDegree.remove(p); 
				 jobs.add(targets.get(p));
				 //update outdegree
				 for(IPath ip: inedges.get(p)){
					 outDegree.computeIfPresent(ip, (k,v) -> v-1);
				 }
			 }

		 }
		 analyze(jobs,subMonitor);
	}
	
	//Helper for reanalyzing a file
	private static void analyze(List<IFile> files, IProgressMonitor monitor) throws CoreException{
		
		FeedbackJavaResourceFactory factory = PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
		//lookup File
		List<FeedbackJavaFile> jfiles = files.stream().map(f -> factory.create(f)).filter(f -> f.isPresent()).map(f -> f.get()).collect(Collectors.toList());
		if(jfiles.isEmpty()) return;
		
		//Lookup Project
		FeedbackProject  fp = jfiles.get(0).getFeedbackProject();
		Optional<? extends FeedbackJavaProject>  ofjP = factory.create(fp.getProject());
		if(!ofjP.isPresent()) throw new IllegalArgumentException("Project Fail");
		FeedbackJavaProject fjp = ofjP.get();
				
		//Do the analyzing
		PerformanceBuilder.processFile(fjp, jfiles, monitor);
		
	}
}
