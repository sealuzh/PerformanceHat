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

* [Maven](https://maven.apache.org/) at least version 3.1
* [MongoDB](https://www.mongodb.org/) installed and running
* [Eclipse IDE](https://eclipse.org/downloads/) installed
* An internet connection to resolve Maven dependencies


### Building

The code is structured as a [multi-module Maven](http://books.sonatype.com/mvnex-book/reference/multimodule.html) project.

* Parent project is **cw-feedback-eclipse-parent**
* Build everything: In **cw-feedback-eclipse-parent** , run `mvn install`


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



* The plugin needs a server as a data source for runtime monitoring data of deployed instances.
* This component is located in the **cw-feedback-handler** submodule.
* Run it with `mvn jetty:run` in **cw-feedback-handler**

## Usage in an example application

### Requirements

* Completed previous steps of this readme
* **cw-feedback-handler** is still running (`mvn jetty:run`)

To see the plugin in action, you will need an example application (target) that will be analyzed by the plugin to give performance hints while modifying the code of the example application.
* The code for the example application is located in the **cw-feedback-example-application** submodule.

To use the plugin in the example application do the following steps

* Run `mvn install` in **cw-feedback-example-application**
* Choose an application id for your example application
* Open `http://localhost:8080/monitoring/register?applicationId=YOUR_APPLICATION_ID` in the browser and replace `YOUR_APPLICATION_ID` with the application id you chose
* Note / write down the access token you get from the step above
* In Eclipse: open **Window** > **Preferences** and look for the **Feedback-Driven Development** entry on the left side. Click on it, and enter `http://localhost:8080/` as Feedback Handler URL.
* In Eclipse: right click on **cw-feedback-example-application**, then choose **Configure** in the context menu and click on **Enable Feedback Nature**.
* Modify `src/main/resources/config.properties` in **cw-feedback-example-application**: Replace the **monitoring.app_id** placeholder with the application name / id you used in the registration step before. Also, replace the **monitoring.access_token** placeholder by the access token you got from the server and wrote down.
* In Eclipse: right click on **cw-feedback-example-application** and open **Properties**. Look for the **Feedback-Driven Development** entry on the left side, and enter the app id and access token from before again. Then, on the left side, under **Feedback-Driven Development**, you should see an entry named **Performance Hat**. Open that entry and enter some time values for the two options (for example 200 ms and 200 ms).


Be sure that the Feedback Handler is still running at this point. Otherwise start it again prior to running the Example Application.

* run the Example Application by executing the main method in **cw-feedback-example-application** in the class `uzh.ifi.seal.performancehat.example.Application`

* After the webapplication has started, go to a browser and open the following two urls:
`http://localhost:9000/example`
`http://localhost:9000/users`

Don't worry if the pages take very long time to open, this is a wanted behavior.

While you have run those two pages, the Feedback Handler has recorded all the performance metrics behind those two requests. This means, the Feedback Handler has now data to feed into the plugin in order to display it to you in the IDE.

### Using the plugin
* To see the performance plugin in action, open the class `uzh.ifi.seal.performancehat.example.controllers.ExampleController.java`.
* Do some change (add a new line or so) and save, in order to force Eclipse to rebuild it.
* After rebuilding it, spots in the code that exceed the performance limits which you set before in milliseconds, are highlighted in orange.
* You can hover over these spots and if everything has worked fine, you should see a popup telling you how long the respective method or loop takes to execute.
