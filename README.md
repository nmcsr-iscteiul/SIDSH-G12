# G12 | SID Software House (SIDSH) - Continuous Delivery Project
<img src="/images/image.png"/>

## Project Description
&nbsp;With this project SIDSH intends to adopt a software development discipline for the company (group) to
build software in a way that it can be released to production at any time.
In other words, the company wants:
- Software to be deployable throughout its lifecycle;
- Teams to prioritize keeping the software deployable over working on new features;
- Anybody can get fast, automated feedback on the production readiness of the software
products, any time somebody makes a change to them;
- Perform push button deployments of any version of the software, to any customer
environment, on demand. 

&nbsp;SIDSH has to automate the full software production pipeline, including the build process, testing,
releases, configuration changes, etc.

&nbsp;Software configuration and versions management must be used to control everything, including code
configuration, scripts, documentation, etc.

&nbsp;The company adopts the software production principle of ‘Done’ means released, i.e., a feature is done
only when it is in production.

## Python API Explained
&nbsp;This simple Python API uses the Flask module to enable the Wordpress Website to "trigger" the Java Applications.
&nbsp;Using the requisite for Covid Scientific Discoveries, it works like this:
1. Python listens for a GET request on http://localhost:8001/covid-sci-discoveries;
2. When a user goes to the website and clicks the button with the link in the first point the GET request is sent to that endpoint;
3. When python receives the request it makes a system call to run the Java application and, when it's done, returns to the user the output: an HTML Page
