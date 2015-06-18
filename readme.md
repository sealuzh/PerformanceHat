# PerformanceHat: A Feedback-driven development plugin for the Eclipse IDE

## License

Copyright 2015 Software Evolution and Architecture Lab, University of Zurich

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


## Introduction

This is a research project by the [Software Evolution and Architecture Lab](http://www.ifi.uzh.ch/seal.html) at the [Department of Computer Science](http://www.ifi.uzh.ch/index.html) of the [University of Zurich](http://www.uzh.ch/index_en.html). Interested users can check out the corresponding [research paper](https://peerj.com/preprints/985.pdf) to learn more about our research.

The aim of this project is to integrate runtime monitoring data from production deployments of the software into the tools developers utilize in their daily workflows (i.e., IDEs) to enable tighter feedback loops. We refer to this notion as **feedback-driven development (FDD)**.

In this project, the abstract FDD concept is instantiated in the form of a plugin for the Eclipse IDE.  


## Set-up and installing

### Requirements

Please make sure you have at least [Maven](https://maven.apache.org/) version 3.1 installed on your system and have a distribution of the [Eclipse IDE](https://eclipse.org/downloads/).

### Building

The code is structured as a [multi-module Maven](http://books.sonatype.com/mvnex-book/reference/multimodule.html) project.
The parent project incorporating the build process of all submodules is found in the folder **cw-feedback-eclipse-parent** .

To build the entire project, open a terminal in the **cw-feedback-eclipse-parent** directory. To execute the build defined in the **pom.xml** run:

`mvn install`

Make sure you have an internet connection, since Maven resolves dependencies over the network.

If the build is successfull, you will receive an output indicating that all of the submodules report `SUCCESS` and the overall parent build will also report a `BUILD SUCCESS` at the end.

If you receive errors during `mvn install` you can execute the command with the `-e` argument to see exception error messages or/and with the `-X` argument to set the log level of the command to DEBUG: `mvn install -X -e`.

This will go through all the necessary submodules, inspect their respective child **pom.xml** and resolve all dependencies such as Java libraries, other submodules etc. It also executes the build of the actual Eclipse plugin update site which can then be used to install the plugin in an instance of the Eclipse. This build is defined in the submodule under **cw-feedback-eclipse-p2updatesite** .

## Running

### Installing the plugin to your Eclipse IDE

The plugin update site will be built into the directory:

`cw-feedback-eclipse-p2updatesite/target/repository`

To install the produced Eclipse plugin into your local IDE instance, open Eclipse, open the **Help Menu**, select the entry **Install New Software**.

![Install New Software](https://cloud.githubusercontent.com/assets/4225724/7633066/2aa2b018-fa52-11e4-980f-ded8046976f0.png)

In the newly opened window, click on **Add**


![Add](https://cloud.githubusercontent.com/assets/4225724/7633099/5b737ccc-fa52-11e4-8e71-9596fa4b2c3a.png)

and then choose **Local..** on the dialog.

![Local](https://cloud.githubusercontent.com/assets/4225724/7633125/84ea33e8-fa52-11e4-83f7-1e024a0b32c1.png)

Now you can browse to the location of the `cw-feedback-eclipse-p2updatesite/target/repository` folder, and select it as a root folder for the plugin. Give it an appropriate name (such as PerformanceHat), click **OK**, and then check all checkboxes that appear,

![Select all](https://cloud.githubusercontent.com/assets/4225724/7633151/b7f5200e-fa52-11e4-8db6-0c7b2f5e7dd3.png)

and click on **Next** to proceed and finish the installation procedure.


### Running the Feedback Handler Server

The plugin needs a server as a data source for runtime monitoring data of deployed instances. We plan to develop connectors for services such as [NewRelic](http://newrelic.com/). For now, you can use the embedded server under the **cw-feedback-handler** submodule.

In order to run the embedded [Jetty](http://eclipse.org/jetty/) server instance you can run

``mvn -pl ../cw-feedback-handler jetty:run``

directly from the parent module in **cw-feedback-eclipse-parent**.

## Usage in an example application

To see the plugin in action, you will need an example application (target) that will be analyzed by the plugin to give performance hints while modifying the code of the example application.

The code for the example application is located in the  **cw-feedback-example-application** submodule. This module is build together with all other modules as described above. During the build, the monitoring code (found in **cw-feedback-eclipse-monitoring**) is weaved into the example code using [AspectJ](https://eclipse.org/aspectj/).

To use the plugin (installed by the instructions in the previous sections), you will have to do the following steps.

### Register the application

Run the feedback handler server (submodule **cw-feedback-handler**) by using the isntructions from previous sections. Then, open a browser and navigate to the URL `http://localhost:8080/monitoring/register?applicationId=YOUR_APPLICATION_ID` where your replace `YOUR_APPLICATION_ID` with an arbitrarily chosen name or identifier for the example application (no white spaces!). You should get a Message containing an access token for the example application. It will look like this:
``{"accessToken":"gvkr7bg4use0ponuunt3pekdjq"}``. Remember / write down this token.

### Configure Eclipse

In the eclipse instance where you installed the plugin, open **Window** > **Preferences** and look for the **Feedback-Driven Development** entry on the left side. Click on it, and enter `http://localhost:8080/` as Feedback Handler URL. The second entry on the properties page is optional and can be left empty.

### Configure Example Application

In Eclipse, right click on **cw-feedback-example-application**, then choose **Configure** in the context menu and click on **Enable Feedback Nature**. The Icon of the module in the project explorer of Eclipse should change now.

Now, you have to modify the file in **cw-feedback-example-application** under the path `src/main/resources/config.properties` and replace the **monitoring.app_id** placeholder with the application name / id you used in the registration step before. Also, replace the **monitoring.access_token** placeholder by the access token you got from the server and wrote downn.

Now, right click on **cw-feedback-example-application** and open **Properties**. Look for the **Feedback-Driven Development** entry on the left side, and enter the app id and access token from before again. Then, on the left side, under **Feedback-Driven Development**, you should see an entry named **Performance Hat**. Open that entry and enter some time values for the two options (for example 200 ms and 200 ms).

### Running the Example Applicatoin
Be sure that the Feedback Handler is still running at this point. Otherwise start it again prior to running the Example Application.

Now run the Example Application by executing the main method in **cw-feedback-example-application** in the class `uzh.ifi.seal.performancehat.example.Application`. After the webapplication has started, go to a browser and open the following two urls:
`http://localhost:9000/example`
`http://localhost:9000/users`

Don't worry if the pages take very long time to open, this is a wanted behavior.

While you have run those two pages, the Feedback Handler has recorded all the performance metrics behind those two requests. This means, the Feedback Handler has now data to feed into the plugin in order to display it to you in the IDE.

### Using the plugin
To see the performance plugin in action, open the class `uzh.ifi.seal.performancehat.example.controllers.ExampleController.java`. Do some change (add a new line or so) and save, in order to force Eclipse to rebuild it. After rebuilding it, spots in the code that exceed the performance limits which you set before in milliseconds, are highlighted in orange. You can hover over these spots and if everything has worked fine, you should see a popup telling you how long the respective method or loop takes to execute.
