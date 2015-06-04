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
package eu.cloudwave.wp5.feedback.eclipse.tests.fixtures.samples;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import eu.cloudwave.wp5.feedback.eclipse.tests.fixtures.base.FeedbackProjectFixture;

/**
 * A sample project that acts as a fixtures for test cases.
 */
public class SampleProject extends FeedbackProjectFixture {

  private static final String PROJECT_NAME = "SampleProject";
  private static final String PACKAGE_NAME = "eu.cloudwave.wp5.samples.fixture";
  private static final String FIXTURE_MONITORING_JAR = "fixtures/cw-feedback-monitoring-0.0.1-SNAPSHOT.jar";

  public static SampleJavaFile APP = new SampleJavaFile("App");
  public static SampleJavaFile SAMPLE_SERVICE = new SampleJavaFile("SampleService");

  private IPackageFragment packageFragment;

  public SampleProject() throws CoreException, UnsupportedEncodingException, IOException {
    super(PROJECT_NAME);
    create();
  }

  private final void create() throws CoreException, UnsupportedEncodingException, IOException {
    packageFragment = createPackage(PACKAGE_NAME);
    createTypeFromFile(APP);
    createTypeFromFile(SAMPLE_SERVICE);
    addJar(FIXTURE_MONITORING_JAR);
  }

  private IType createTypeFromFile(final SampleJavaFile javaFile) throws JavaModelException, UnsupportedEncodingException, IOException {
    return createTypeFromFile(packageFragment, javaFile.getName(), javaFile.getContentPath());
  }

  public static class SampleJavaFile {

    private static final String SLASH = "/";
    private static final String SOURCES = "sources";

    private static final String JAVA_FILE_EXTENSION = ".java";
    private static final String TXT_FILE_EXTENSION = ".txt";

    private final String nameWithoutFileExtension;

    public SampleJavaFile(final String nameWithoutFileExtension) {
      this.nameWithoutFileExtension = nameWithoutFileExtension;
    }

    public String getName() {
      return nameWithoutFileExtension + JAVA_FILE_EXTENSION;
    }

    public String getContentPath() {
      return SOURCES + SLASH + nameWithoutFileExtension + TXT_FILE_EXTENSION;
    }

  }

}
