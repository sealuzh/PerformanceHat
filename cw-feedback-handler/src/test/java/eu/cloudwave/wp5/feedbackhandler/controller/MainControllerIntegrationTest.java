package eu.cloudwave.wp5.feedbackhandler.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;

public class MainControllerIntegrationTest extends AbstractBaseControllerIntegrationTest {

  private static final String ROOT_CONTENT = "Server is running.";

  @Test
  public void test() throws Exception {
    assertTextResponseOk(get("/"), ROOT_CONTENT);
  }
}
