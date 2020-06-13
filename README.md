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

## Group members
- Nuno Rego 82137 [1 - Wordpress CM]
- Frederico Correia 82006 [3 - Covid Scientific Discoveries]
- João Pinto 79196 [4 - Covid Graph Spread]
- Tomás Godinho 65245 [5 - Covid Queries]
- Bernardo Sequeira 82223 [6 - Covid Evolution Diff]

## Errors and faults in the Implementation
### 1. Wordpress CM

&nbsp;There was a general problem with plugins: I couldn't find plugins that did exactly what was asked in the requirement. I found some that were payed and some that were free but I coulnd't find a way to make them work as they were supposed too.

- Covid Wiki isn't using a plugin, but regular Wordpress posts. However it allows for pretty much everyting that is asked in the requirement.
- Join Us: I could not find a way to make the plugin (Pie register) to allow the user to add Keywords of interest - he can only choose from the predefined ones.
- Covid Scientific Discoveries Repository: the admin can add files through the page but he can't delete/change them - I couldn't find a plugin that allowed that. To solve this I added a button that redirected to the Wordpress dashboard were the admin can delete or change the files directly.
- WebSite Analytics: I coulnd't find a plugin that allowed for analytics on the website to be displayed on a page. To solve this I put a button that redirected (only the admin user) to the wordpress dashboard where he can see the analytics. (I also added some analytics screenshots that display what it would look like to the user if the plugin worked on the page).

### 3. Covid Scientific Discoveries
- For JUnit to work there must be a PDF file named '178-1-53.pdf' inside /SIDSH-G12/java/covid-sci-discoveries/HTML/Covid_Scientific_Discoveries_Repository/

### 4. Covid Graph Spread

### 5. Covid Queries

### 6. Covid Evolution Diff


## Installation Steps
1. Guarantee that you have Docker installed on your machine (see https://docs.docker.com/get-docker/ for further explanation on its installation)
2. Simply click on the start file! (start.bat if you're on a Windows machine and start.sh if you're on a Linux machine)

## Wordpress Users

- Admin username: 'nuno-admin'
- Admin password: 'nuno-admin'
- Member username: 'nuno-member'
- Member password: 'nuno-member'

## Python API Explained
&nbsp;This simple Python API uses the Flask module to enable the Wordpress Website to "trigger" the Java Applications.
&nbsp;Using the requisite for Covid Scientific Discoveries, it works like this:
1. Python listens for a GET request on http://localhost:8001/covid-sci-discoveries;
2. When a user goes to the website and clicks the button with the link in the first point the GET request is sent to that endpoint;
3. When python receives the request it makes a system call to run the Java application and, when it's done, returns to the user the output: an HTML Page