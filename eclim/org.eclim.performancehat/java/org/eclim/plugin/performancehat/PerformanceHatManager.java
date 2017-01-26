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

/**
 * Class to make FeedbackProjects known to eclim
 */
public class PerformanceHatManager implements ProjectManager{

	public void create(IProject project, CommandLine commandLine) throws Exception {
		//Nothing Special todo here
	}

	public List<Error> update(IProject project, CommandLine commandLine) throws Exception {
		//Nothing Special todo here
		return null;
	}

	public void delete(IProject project, CommandLine commandLine) throws Exception {
		//Nothing Special todo here
	}

	public void refresh(IProject project, CommandLine commandLine) throws Exception {
		//Nothing Special todo here
	}

	public void refresh(IProject project, IFile file) throws Exception {
		//Nothing Special todo here
	}
}
