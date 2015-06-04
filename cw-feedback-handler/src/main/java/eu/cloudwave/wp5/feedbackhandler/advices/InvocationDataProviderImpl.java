/*******************************************************************************
 * Copyright 2015 Software Evolution and Architecture Lab, University of Zurich
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package eu.cloudwave.wp5.feedbackhandler.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.feedbackhandler.aggregations.AggregatedInvocation;
import eu.cloudwave.wp5.feedbackhandler.repositories.ProcedureExecutionRepository;

/**
 * Implementation of {@link InvocationDataProvider}.
 */
@Service
public class InvocationDataProviderImpl implements InvocationDataProvider {

  @Autowired
  private ProcedureExecutionRepository procedureExecutionRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isNewlyInvoked(String invokedClassName, String invokedMethodName, String callerClassName, String callerMethodName) {
    Optional<AggregatedInvocation> invocation = procedureExecutionRepository.getCallersOfInvokedMethod(invokedClassName, invokedMethodName, callerClassName, callerMethodName);

    if (invocation.isPresent()) {
      // check if given caller exists in monitoring data
      java.util.Optional<ProcedureExecution> givenCallerMethod = invocation.get().getCallers().stream()
          .filter(procedure -> (procedure.getProcedure().getClassName().equals(callerClassName) && procedure.getProcedure().getName().equals(callerMethodName))).findAny();

      // if given caller exists in monitoring data -> invoked method was already called by caller -> method is NOT new
      return givenCallerMethod.isPresent() ? false : true;
    }

    // new invocation is true if we cannot find the given invocation in our monitoring data
    else {
      return true;
    }
  }
}
