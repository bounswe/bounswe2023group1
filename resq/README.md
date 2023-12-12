# ResQ

ResQ is REST based web service for the Disaster Response Application ResQ developed by CMPE451-GroupA1

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

- Docker and Docker-Compose

### Config Directory Structure

The backend application gets the actual path from `resq.appdir` property and checks for **resq** directory under that path.

Under main directory structure is:
- **conf**
     - **appparam.txt**: application configuration items are located under this file (web service url, username and pass etc.)
     - **logConf.xml**: log configuration file.
- **log**: Application writes all logs under this directory

### Installing with Docker

First clone all files into local repo.

Place the config folder named as project_env under the directory bounswe2023group1\resq\backend\resq.
Then, run the following commands:

docker-compose up

## Project Specific Information 

The local buld of the frontend application is available on http://localhost:3000

Production frontend URL: https://resq.org.tr


Backend application context root is /resq/api/v1/. You can reach the backend application via  
https://localhost:8081/resq/api/v1/.
To reach the API documentation:  
https://localhost:8081/resq/api/v1/swagger-ui.html


Production backend URL: https://api.resq.org.tr/resq/api/v1

Production backend API Documentation:  
https://api.resq.org.tr/resq/api/v1/swagger-ui/index.html