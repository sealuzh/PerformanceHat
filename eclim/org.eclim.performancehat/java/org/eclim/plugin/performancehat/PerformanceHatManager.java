package org.eclim.plugin.performancehat;

import java.util.List;

import org.eclim.command.CommandLine;
import org.eclim.command.Error;
import org.eclim.plugin.core.project.ProjectManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;

public class PerformanceHatManager implements ProjectManager{

	public void create(IProject project, CommandLine commandLine) throws Exception {
		System.out.println("Performance_Hat_Create");
		// TODO Auto-generated method stub
	}

	public List<Error> update(IProject project, CommandLine commandLine) throws Exception {
		System.out.println("Performance_Hat_Update");
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(IProject project, CommandLine commandLine) throws Exception {
		System.out.println("Performance_Hat_Delete");
		// TODO Auto-generated method stub
		
	}

	public void refresh(IProject project, CommandLine commandLine) throws Exception {
		System.out.println("Performance_Hat_Refresch_P");

		// TODO Auto-generated method stub
		
	}

	public void refresh(IProject project, IFile file) throws Exception {
		System.out.println("Performance_Hat_Refresch_F");
		// TODO Auto-generated method stub
		
	}
}
