/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedback.eclipse.performance.extension.basic.feedbackhandler;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Inject;

import eu.cloudwave.wp5.feedback.eclipse.base.core.preferences.FeedbackPreferences;
import eu.cloudwave.wp5.feedback.eclipse.base.resources.core.FeedbackProject;

/**
 * Implementation of {@link FeedbackHandlerEclipseClient}.
 */
public class FeedbackHandlerEclipseClientImpl implements FeedbackHandlerEclipseClient {

  private static final String EMPTY = "";

  @Inject
  private FeedbackHandlerClientFactory feedbackHandlerClientFactory;

  private FeedbackHandlerClient feedbackHandlerClient;

  private static class MethodCacheKey{
	  public final String id;
	  public final String token;
	  public final String className;
	  public final String procedureName;
	  public final String[] arguments;	
	  
	  public MethodCacheKey(FeedbackProject project, String className, String procedureName, String[] arguments) {
		  this.id = project.getApplicationId();
		  this.token = project.getAccessToken();
		  this.className = className;
		  this.procedureName = procedureName;
		  this.arguments = arguments;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(arguments);
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((procedureName == null) ? 0 : procedureName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodCacheKey other = (MethodCacheKey) obj;
		if (!Arrays.equals(arguments, other.arguments))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (procedureName == null) {
			if (other.procedureName != null)
				return false;
		} else if (!procedureName.equals(other.procedureName))
			return false;
		return true;
	}  
	
  }
  
  private static class MethodCacheEntry{
	  final boolean hasExecTime;
	  public final double execTime;
	  final boolean hasColSize;
	  public final double colSize;
	  public final double[] agrColSize; 
	  
	  public MethodCacheEntry(Double execTime, Double colSize, double[] argColSize){
		  this.hasExecTime = execTime != null;
		  this.execTime = hasExecTime?execTime:0;
		  this.hasColSize = colSize != null;
		  this.colSize = hasColSize?colSize:0;
		  this.agrColSize = argColSize;
	  }

	public Double getColSize() {
		if(hasColSize) return colSize;
		return null;
	}

	public Double getColSize(int i) {
		if(i < agrColSize.length && agrColSize[i] != -1 ) return agrColSize[i];
		return null;
	}
	
	public Double getExecTime() {
		if(hasExecTime) return execTime;
		return null;
	}
  }

  private final LoadingCache<MethodCacheKey, MethodCacheEntry> methodCache;
  
  public FeedbackHandlerEclipseClientImpl() {
	  methodCache = CacheBuilder.newBuilder()
			    .concurrencyLevel(1)
			    .maximumSize(10000)
			    .expireAfterWrite(10, TimeUnit.MINUTES)
			    .build(
			        new CacheLoader<MethodCacheKey, MethodCacheEntry>() {
			          public MethodCacheEntry load(MethodCacheKey key) {
			            return fetch(key);
			          }
			        });
  }
    
  private MethodCacheEntry fetch(MethodCacheKey key){
	  Double[] res = feedbackHandlerClient().collectionSizesAndExecTime(key.token, key.id, key.className, key.procedureName, key.arguments);
	  Double time = res[0];
	  Double size = res[1]; 
	  double[] args = new double[key.arguments.length];
	  for(int i = 0; i < args.length; i++){
		  args[i] = (res[i+2]==null)?-1:res[i+2];
	  }
	  return new MethodCacheEntry(time, size, args);
  }

  @Override
  public Double avgExecTime(final FeedbackProject project, final String className, final String procedureName, final String[] arguments) {
	  try {
		  return methodCache.get(new MethodCacheKey(project, className, procedureName, arguments)).getExecTime();
	  } catch (ExecutionException e) {
		  e.printStackTrace();
	  }
	  return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double collectionSize(final FeedbackProject project, final String className, final String procedureName, final String[] arguments, final Integer number) {
	  	try {
		  	if(number != null) return methodCache.get(new MethodCacheKey(project, className, procedureName, arguments)).getColSize(number);
	  		return methodCache.get(new MethodCacheKey(project, className, procedureName, arguments)).getColSize();
	  	} catch (ExecutionException e) {
	  		e.printStackTrace();
	  	}
	  	return null;
  }

  private FeedbackHandlerClient feedbackHandlerClient() {
    if (feedbackHandlerClient == null) {
      final String rootUrl = FeedbackPreferences.getString(FeedbackPreferences.FEEDBACK_HANDLER__URL);
      feedbackHandlerClient = feedbackHandlerClientFactory.create(rootUrl);
    }
    return feedbackHandlerClient;
  }

  private String property(final FeedbackProject project, final String key) {
    final IEclipsePreferences properties = project.getFeedbackProperties();
    return properties.get(key, EMPTY);
  }
  
}
