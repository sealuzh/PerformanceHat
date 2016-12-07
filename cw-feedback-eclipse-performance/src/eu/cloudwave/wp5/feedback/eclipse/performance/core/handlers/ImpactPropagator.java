package eu.cloudwave.wp5.feedback.eclipse.performance.core.handlers;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodCall;
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


public class ImpactPropagator {

	public static void calculateImpact(IMember mem) throws CoreException{
		 Queue<IMember> open = Lists.newLinkedList();
		 open.add(mem);
		 Set<IMember> visited = Sets.newHashSet();
		 Map<IPath,Set<IPath>> inedges = Maps.newHashMap(); 
		 Map<IPath,IFile> targets = Maps.newHashMap(); 
		 List<IPath> paths = Lists.newArrayList();

		 while(!open.isEmpty()){
			 IMember cur = open.poll();
			 if(visited.contains(cur)) continue;
			 CallHierarchy hierarchy = new CallHierarchy();
			 IJavaSearchScope searchScope = SearchEngine.createWorkspaceScope();
			 
			 hierarchy.setSearchScope(searchScope);
			 ArrayList<IMember> methodCalls = new ArrayList<IMember>();
			 Set<IPath> files = Sets.newHashSet();
			 MethodWrapper[] callerWrapper = hierarchy.getCallerRoots(new IMember[]{cur});
			 ArrayList<MethodWrapper> callsWrapper = new ArrayList<MethodWrapper>();
			 for (int i1 = 0; i1 < callerWrapper.length; i1++) {
			     callsWrapper.addAll(Arrays.asList(callerWrapper[i1].getCalls(new NullProgressMonitor())));
			 }

			 //boolean inter = false;
			 for (int i1 = 0; i1 < callsWrapper.size(); i1++){
				 IMember member = callsWrapper.get(i1).getMethodCall().getMember(); 
			     methodCalls.add(member);
			     //if(!inter && cur.getPath().equals(member.getPath())) inter = true;
			     files.add(member.getPath());
			 }
			 /*if(inter){
				 mult.compute(cur.getPath(), (k,v)-> )
			 }*/
			 targets.putIfAbsent(cur.getPath(),(IFile)cur.getResource());
			 inedges.merge(cur.getPath(), files, (v1,v2) -> Sets.union(v1, v2));
			 visited.add(cur);
			 open.addAll(methodCalls);
		 }
		 
		 Map<IPath,Integer> outDegree = Maps.newHashMap(); 
		 for(IPath m:inedges.keySet()){
			 outDegree.put(m, 0);
		 }
		 
		 for(Set<IPath> ms: inedges.values()){
			 for(IPath m:ms){
				 outDegree.compute(m, (k,v) -> (v==null)?1:v+1); 
			 }
		 }
		 
		 while (!outDegree.isEmpty()) {
			 List<IPath> smallest = Lists.newArrayList();
			 int smallestNum = Integer.MAX_VALUE;
			 for(Map.Entry<IPath,Integer> kv:outDegree.entrySet() ){
				 if(kv.getValue() == smallestNum){
					 smallest.add(kv.getKey());
				 } else if(kv.getValue() < smallestNum){
					 smallest.clear();
					 smallestNum = kv.getValue();
					 smallest.add(kv.getKey());
				 }
			 }
			 for(IPath p:smallest){
				 outDegree.remove(p); 
				 analyze(targets.get(p));
				 for(IPath ip: inedges.get(p)){
					 outDegree.computeIfPresent(ip, (k,v) -> v-1);
				 }
			 }

		 }
		 
	}
	
	private static void analyze(IFile file) throws CoreException{
		FeedbackJavaResourceFactory factory = PerformancePluginActivator.instance(FeedbackJavaResourceFactory.class);
		Optional<? extends FeedbackJavaFile> ojfFile = factory.create(file);  
		if(!ojfFile.isPresent())  throw new IllegalArgumentException("File Fail:"+file.getFullPath());
		FeedbackJavaFile jfFile = ojfFile.get();
		FeedbackProject  fp = jfFile.getFeedbackProject();
		Optional<? extends FeedbackJavaProject>  ofjP = factory.create(fp.getProject());
		if(!ofjP.isPresent()) throw new IllegalArgumentException("Project Fail");;
		FeedbackJavaProject fjp = ofjP.get();
		PerformanceBuilder.processFile(fjp, jfFile);
	}
}
