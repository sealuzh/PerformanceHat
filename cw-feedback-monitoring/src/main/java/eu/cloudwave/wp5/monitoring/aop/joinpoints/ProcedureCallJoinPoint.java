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

  /**
   * Helper that returns all {@link java.lang.annotation.Annotation}s of the given call joint point needed for the
   * CostHat UseCase
   * 
   * @return list of all {@link java.lang.annotation.Annotation}
   */
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

  /**
   * We operate on our own annotation interface ({@link Annotation}). This helper returns all custom annotations
   * enriched with CostHat specific content.
   * 
   * @return list of all {@link Annotation}
   */
  public List<Annotation> getMyAnnotations() {
    List<java.lang.annotation.Annotation> javaLangAnnotations = getAnnotations();
    List<Annotation> customAnnotations = javaLangAnnotations
        .stream()
        .map(
            (a) -> {
              /*
               * Every annotation has members, e.g. MicroserviceClientMethodDeclaration has a 'caller' attribute
               * 
               * This is where we store those members.
               */
              Map<String, Object> members = Maps.newHashMap();

              /*
               * Loop through every member of the current annotation: method.getName() returns the identifier of the
               * member, e.g. 'caller'. One has to invoke the member in order to get the value.
               */
              for (Method method : a.annotationType().getDeclaredMethods()) {
                Object value = Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT;
                try {
                  value = method.invoke(a);
                }
                catch (Exception e) {}

                /*
                 * @MicroserviceMethodDeclaration: add service identifier from the properties if the user has not set
                 * the 'identifier' member
                 */
                if (a.annotationType().getName().contains(Ids.MICROSERVICE_ENDPOINT_ANNOTATION) && Ids.MICROSERVICE_ENDPOINT_ANNOTATION_IDENTIFIER_ATTRIBUTE.equals(method.getName())
                    && Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT.equals(value)) {
                  value = propertiesLoader.get(Ids.MICROSERVICE_IDENTIFIER_LABEL).get();
                }

                /*
                 * @MicroserviceClientMethodDeclaration: add service identifier from the properties if the user has not
                 * set the 'caller' member
                 */
                else if (a.annotationType().getName().contains(Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION) && Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_FROM_ATTRIBUTE.equals(method.getName())
                    && Ids.MICROSERVICE_CLIENT_REQUEST_ANNOTATION_ATTRIBUTE_DEFAULT.equals(value)) {
                  value = propertiesLoader.get(Ids.MICROSERVICE_IDENTIFIER_LABEL).get();
                }

                members.put(method.getName(), value);
              }
              Annotation newCustomAnnotation = new AnnotationImpl(a.annotationType().getName(), members);
              return newCustomAnnotation;
            }).collect(Collectors.toList());
    return customAnnotations;
  }
}
