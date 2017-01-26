/**
 * Copyright (C) 2016
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.eclim.plugin.performancehat;

import org.eclim.Services;

import org.eclim.plugin.AbstractPluginResources;
import org.eclim.plugin.core.project.ProjectManagement;
import org.eclim.plugin.core.project.ProjectNatureFactory;

/**
 * Implementation of AbstractPluginResources for FeedbackNaturePlugin.
 *
 * @author Markus Knecht
 */
public class PluginResources
  extends AbstractPluginResources
{
  /**
   * Name that can be used to lookup this PluginResources from
   * {@link Services#getPluginResources(String)}.
   */
  public static final String NAME = "org.eclim.performancehat";
  public static final String NATURE = "eu.cloudwave.wp5.feedback.eclipse.performance.core.natures.PerformanceProjectNature";//Ids.NATURE;
  
  /**
   * Fuction called when eclipse initializes the plugin
   */
  @Override
  public void initialize(String name)
  {
	super.initialize(name);
    // add the nature alias
    ProjectNatureFactory.addNature("performancehat", NATURE);
	//Make the nature known to eclim
    ProjectManagement.addProjectManager(NATURE, new PerformanceHatManager());
    
  }

  @Override
  protected String getBundleBaseName()
  {
    return "org/eclim/plugin/performancehat/messages";
  }
}
