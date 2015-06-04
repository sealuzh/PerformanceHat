package eu.cloudwave.wp5.feedbackhandler.aggregations;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import eu.cloudwave.wp5.common.model.ProcedureExecution;
import eu.cloudwave.wp5.feedbackhandler.constants.DbTableNames;

@Document(collection = DbTableNames.PROCEDURE_EXECUTIONS)
public class AggregatedInvocation {

  /**
   * Method name of the given invoked method, for example to check if this method was already invoked by the given
   * caller
   */
  private String invokedMethodName;

  /**
   * Class name of the given invoked class
   */
  private String invokedClassName;

  /**
   * List of distinct callers of the given invoked method
   */
  @DBRef
  private List<ProcedureExecution> callers;

  public AggregatedInvocation() {}

  public AggregatedInvocation(String invokedMethodName, String invokedClassName, List<ProcedureExecution> callers) {
    this.invokedMethodName = invokedMethodName;
    this.invokedClassName = invokedClassName;
    this.callers = callers;
  }

  public String getInvokedMethodName() {
    return invokedMethodName;
  }

  public void setInvokedMethodName(String invokedMethodName) {
    this.invokedMethodName = invokedMethodName;
  }

  public String getInvokedClassName() {
    return invokedClassName;
  }

  public void setInvokedClassName(String invokedClassName) {
    this.invokedClassName = invokedClassName;
  }

  public List<ProcedureExecution> getCallers() {
    return callers;
  }

  public void setCallers(List<ProcedureExecution> callers) {
    this.callers = callers;
  }

}
