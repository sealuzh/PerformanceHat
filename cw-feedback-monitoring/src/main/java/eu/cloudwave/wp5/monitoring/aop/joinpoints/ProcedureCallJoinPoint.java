package eu.cloudwave.wp5.monitoring.aop.joinpoints;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.cloudwave.wp5.common.model.Annotation;
import eu.cloudwave.wp5.common.constants.Ids;
import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.AnnotationImpl;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;
import eu.cloudwave.wp5.common.util.Splitters;
import eu.cloudwave.wp5.monitoring.properties.PropertiesLoader;

/**
 * This is a decorator for {@link ProceedingJoinPoint}'s that provides additional functionality to get the called
 * {@link Procedure}.
 */
public class ProcedureCallJoinPoint extends AbstractProceedingJoinPointDecorator implements ProceedingJoinPoint {

  private static final String OPENING_BRACKET = "(";
  private static final String CLOSING_BRACKET = ")";

  private PropertiesLoader propertiesLoader;

  private Procedure targetProcedure;

  public ProcedureCallJoinPoint(final ProceedingJoinPoint joinPoint) {
    super(joinPoint);
    propertiesLoader = new PropertiesLoader();
  }

  /**
   * Returns the {@link Procedure} targeted by this call (i.e. the call at this join point).
   * 
   * @return the {@link Procedure} targeted by this call (i.e. the call at this join point)
   */
  public Procedure getTargetProcedure() {
    if (targetProcedure == null) {
      targetProcedure = new ProcedureImpl(getTargetProcedureClassName(), getTargetProcedureName(), getTargetKind(), getTargetArguments(), getMyAnnotations());
    }
    return targetProcedure;
  }

  private final String getTargetProcedureClassName() {
    // if the procedure is of kind method, the target is NOT null and it should be considered to identify its real type
    if (getTargetKind().equals(ProcedureKind.METHOD)) {
      return getTarget().getClass().getName();
    }
    // If the procedure is a static method or a constructor, the target is null.
    // This is no problem, because its type is the same as the declaring type of the method invocation.
    return getSignature().getDeclaringType().getName();
  }

  private final String getTargetProcedureName() {
    return getTargetKind().equals(ProcedureKind.CONSTRUCTOR) ? getSignature().getDeclaringType().getSimpleName() : getSignature().getName();
  }

  private ProcedureKind getTargetKind() {
    ProcedureKind kind = ProcedureKind.METHOD;
    if (getKind().equals(ProceedingJoinPoint.CONSTRUCTOR_CALL)) {
      kind = ProcedureKind.CONSTRUCTOR;
    }
    else if (getTarget() == null) { // no constructor call but target is null -> static method
      kind = ProcedureKind.STATIC_METHOD;
    }
    return kind;
  }

  private List<String> getTargetArguments() {
    final String signatureAsText = getSignature().toLongString();
    final String argumentsAsText = signatureAsText.substring(signatureAsText.indexOf(OPENING_BRACKET) + 1, signatureAsText.indexOf(CLOSING_BRACKET));
    final Iterable<String> arguments = Splitters.onComma(argumentsAsText);
    return Lists.newArrayList(arguments);
  }

  public List<Annotation> getMyAnnotations() {

    return getAnnotations()
        .stream()
        .map((a) -> {
          Map<String, Object> members = Maps.newHashMap();

          // e.g. every MicroserviceClientRequest annotation has a 'from' attribute.
          // in this case method.getName() is 'from' and value is an identifier
            for (Method method : a.annotationType().getDeclaredMethods()) {
              Object value = Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT;
              try {
                value = method.invoke(a);
              }
              catch (Exception e) {}

              // @MicroserviceEndpoint
              if (a.annotationType().getName().contains(Ids.MICROSERVICE_ENDPOINT_ANNOTATION) && Ids.MICROSERVICE_ENDPOINT_ANNOTATION.equals(method.getName())
                  && Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT.equals(value)) {
                value = propertiesLoader.get(Ids.MICROSERVICE_IDENTIFIER_LABEL).get();
              }
              // @MicroserviceClientRequest
              // use service identifier from the project configuration if user did not override the service identifier
              else if (a.annotationType().getName().contains(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION) && Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_FROM_ATTRIBUTE.equals(method.getName())
                  && Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT.equals(value)) {
                value = propertiesLoader.get(Ids.MICROSERVICE_IDENTIFIER_LABEL).get();
              }

              members.put(method.getName(), value);
            }
            Annotation annotation = new AnnotationImpl(a.annotationType().getName(), members);
            return annotation;
          }).collect(Collectors.toList());
  }

  private List<java.lang.annotation.Annotation> getAnnotations() {
    Signature signature = getSignature();
    if (signature instanceof MethodSignature) {
      return Arrays.asList(((MethodSignature) getSignature()).getMethod().getAnnotations());
    }
    else if (signature instanceof ConstructorSignature) {
      return Arrays.asList(((ConstructorSignature) getSignature()).getConstructor().getAnnotations());
    }
    return Lists.newArrayList();
  }

}
