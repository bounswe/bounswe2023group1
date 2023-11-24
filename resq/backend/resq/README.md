# ResQ

ResQ is REST based web service for the Disaster Response Application ResQ developed by CMPE451-GroupA1

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

- JDK 17.0.2
- Maven 3.8.6
- Intellij or Eclipse IDE

### Config Directory Structure

Application gets the actual path from `resq.appdir` property and checks for **resq** directory under that path.

Under main directory structure is:
- **conf**
     - **appparam.txt**: application configuration items are located under this file (web service url, username and pass etc.)
     - **logConf.xml**: log configuration file.
- **log**: Application writes all logs under this directory

### Installing

First clone all files into local repo. 

Open the IDE and import all projects with import existing Maven project option(Check Maven settings before this step). 

From Run Configurations Menu add new SpringBootApplication Run Configuration.

In the configuration, add the config folder with environment variable named as resq.appdir
(For example: resq.appdir=C:\Users\alitp\OneDrive\Masaüstü\remoterepo\project_env. In my system, project_env includes the resq folder which is the config folder.)

You may need to change some settings in appparam.txt file according to your system, passwords etc.
### Installing with Docker

First clone all files into local repo.

Place the config folder named as project_env under the resq folder(under the directory bounswe2023group1\resq\backend\resq, resides in the same directory with src.).
Then, run the following commands:

docker build -t resq:latest -f Dockerfile.local .  
docker-compose up

## Integrations
### Database Connections

| Database Type | Production       | Test |
|:-------------:|------------------| --- |
|  PostgreSQL   | _schema_: resqdb | _schema_: resqdb |

You should change the database connection settings based on your PostgreSQL server username and password in appparam.txt.

## Project Specific Information 

Application context root is /resq/api/v1/. You can reach the application via  
https://localhost:8081/resq/api/v1/.
To reach the API documentation:  
https://localhost:8081/resq/api/v1/swagger-ui.html
