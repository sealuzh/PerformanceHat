package eu.cloudwave.wp5.common.dto.costs;

public class InitialInvocationCheckDto {

  private String invokedClassName;
  private String invokedMethodName;

  private String callerMethodName;
  private String callerClassName;

  public InitialInvocationCheckDto(String invokedClassName, String invokedMethodName, String callerClassName, String callerMethodName) {
    this.invokedClassName = invokedClassName;
    this.invokedMethodName = invokedMethodName;

    this.callerClassName = callerClassName;
    this.callerMethodName = callerMethodName;
  }

  public String getInvokedClassName() {
    return invokedClassName;
  }

  public void setInvokedClassName(String invokedClassName) {
    this.invokedClassName = invokedClassName;
  }

  public String getInvokedMethodName() {
    return invokedMethodName;
  }

  public void setInvokedMethodName(String invokedMethodName) {
    this.invokedMethodName = invokedMethodName;
  }

  public String getCallerMethodName() {
    return callerMethodName;
  }

  public void setCallerMethodName(String callerMethodName) {
    this.callerMethodName = callerMethodName;
  }

  public String getCallerClassName() {
    return callerClassName;
  }

  public void setCallerClassName(String callerClassName) {
    this.callerClassName = callerClassName;
  }
}
