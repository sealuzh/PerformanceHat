package eu.cloudwave.wp5.common.model.impl;

import static eu.cloudwave.wp5.common.tests.assertion.Asserts.assertEquality;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import eu.cloudwave.wp5.common.model.Procedure;
import eu.cloudwave.wp5.common.model.ProcedureKind;
import eu.cloudwave.wp5.common.model.impl.ProcedureImpl;

public class ProcedureTest {

  private static final String CLASS = "MyClass";
  private static final String METHOD = "myMethod";
  private static final List<String> PARAMS_1 = Lists.newArrayList();
  private static final List<String> PARAMS_2 = Lists.newArrayList();
  private static final String OTHER_CLASS = "OtherClass";
  private static final String OTHER_METHOD = "otherMethod";
  private static final List<String> OTHER_PARAMS = Lists.newArrayList("Param");

  private static final Procedure PROC_EQUAL_ONE = new ProcedureImpl(CLASS, METHOD, ProcedureKind.METHOD, PARAMS_1, Lists.newArrayList());
  private static final Procedure PROC_EQUAL_TWO = new ProcedureImpl(CLASS, METHOD, ProcedureKind.METHOD, PARAMS_2, Lists.newArrayList());
  private static final Procedure PROC_DIFFERENT_ONE = new ProcedureImpl(OTHER_CLASS, METHOD, ProcedureKind.METHOD, PARAMS_1, Lists.newArrayList());
  private static final Procedure PROC_DIFFERENT_TWO = new ProcedureImpl(CLASS, OTHER_METHOD, ProcedureKind.METHOD, PARAMS_1, Lists.newArrayList());
  private static final Procedure PROC_DIFFERENT_THREE = new ProcedureImpl(CLASS, METHOD, ProcedureKind.METHOD, OTHER_PARAMS, Lists.newArrayList());

  @Test
  public void testEqualsAndHashCode() {
    assertEquality(ImmutableList.of(PROC_EQUAL_ONE, PROC_EQUAL_TWO), ImmutableList.of(PROC_DIFFERENT_ONE, PROC_DIFFERENT_TWO, PROC_DIFFERENT_THREE));
  }

}
