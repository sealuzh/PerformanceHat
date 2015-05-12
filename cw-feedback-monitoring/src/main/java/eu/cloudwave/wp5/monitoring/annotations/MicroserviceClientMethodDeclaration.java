package eu.cloudwave.wp5.monitoring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eu.cloudwave.wp5.common.constants.Ids;

/**
 * Annotating a method with this interface indicates that the method is a client method used to send requests to another
 * service and the developer is interested in its costs.<br />
 * <br />
 * Important: the name of the annotation has to be equal to {@link Ids#MICROSERVICE_CLIENT_REQUEST_ANNOTATION}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MicroserviceClientMethodDeclaration {

  /**
   * Microservice Client Identifier <br />
   * <br />
   * Important: the name of the attribute (= 'caller') has to be equal to
   * {@link Ids#MICROSERVICE_CLIENT_REQUEST_ANNOTATION_FROM_ATTRIBUTE}
   * 
   * @return string with the identifier
   */
  public String caller() default Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT;

  /**
   * Microservice identifier of the recipient of this request <br />
   * <br />
   * Important: the name of the attribute (= 'callee') has to be equal to
   * {@link Ids#MICROSERVICE_CLIENT_REQUEST_ANNOTATION_TO_ATTRIBUTE}
   * 
   * @return
   */
  public String callee();

}
