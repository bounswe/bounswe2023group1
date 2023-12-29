# CMPE451 2023 - Final Project Deliverables

## Group Milestone Report

### Video Demonstration
- **Description:** Maximum of 5 minutes video showcasing the system with both web and mobile UIs.
- **Link:** [Video URL](#)

## Executive Summary
- **Project Status:** Summary of the project status in terms of requirements.

### Status of Deliverables
| Deliverable             | Status    |
|-------------------------|-----------|
| [Deliverable Name]      | [Status]  |
| [Another Deliverable]   | [Status]  |
*And so on for each deliverable*

## Final Release Notes

## Management
- **Changes Implemented:** Description of changes executed based on previous milestones.
- **Impact of Changes:** Specific impacts of these changes.

### Reflections on Final Milestone Demo
- **Lessons Learned:** [Details]
- **Improvements:** What could have been done differently in earlier stages for a better outcome. Reasons for these views.

## Progress Based on Teamwork

### Summary of Work by Team Members
| Team Member | Work Performed            | Status   |
|-------------|----------------------------|----------|
| [Name]      | [Work Summary]             | [Status] |
*And so on for each team member*

## API Endpoints
- **Documentation:** [Link to API Documentation](#)
- **Example Calls:**
  - Call 1: [Details]
  - Call 2: [Details]
  - Call 3: [Details]

## User Interface / User Experience
- **Source Code Links:** [Link to Repository](#)
- **Screenshots:**
  - Web UI: ![Web UI Screenshot](#)
  - Mobile UI: ![Mobile UI Screenshot](#)

## Annotations
- **Status:** Description of the status of annotations in the project.
- **Standards Used:** Details of the standards implemented.

## Scenarios
- **Extensive Scenario:** [Detailed Description]
- **Feature Completion:** Details of the work completed related to this scenario.

## Use and Maintenance
- **Verification:** Steps taken to assure manuals are correct.
- **Alternative Installation Tests:** Results from installing on different devices.

## Project Artifacts

### Manuals
- **User Manual:** [Link to User Manual](#)
- **System Manual:** [Link to System Manual](#)

### Software Requirements Specification (SRS)
- **Link:** [SRS Document](#)

### Software Design Documents
- **UML Diagrams:** [Link to UML Diagrams](#)

### User Scenarios and Mockups
- **Link:** [Scenarios and Mockups](#)

## Research
- **Project Plan:** [Link to Project Plan](#)

### Unit Tests Reports
- **Link:** [Unit Tests](#)

## Software
- **Deployment:** Dockerized and deployed software details.
- **Hostname/IPs:** Details of the web application, API, and annotation service.
- **Release Version:** 0.9.0
- **Release Description:** Group <X> CMPE451 2023 Fall Final Release
- **Tag Name:** final-submission-g<X>
- **Build Instructions:** [Docker Build Instructions](#)

### Data for Testing
- **Database Dump/Script:** [Link or Details](#)

# Use and Maintenance
## Project Artifacts
### User Manual
- User creates an account via providing email&password to the system.
- System grants default "Victim" role to the user.
- User can login with his/her credentials to the system.
- The user can access services for which he has the required role.
### System Manual  
#### To deploy the backend of the application to the EC2 server, follow the instructions below
- First, clone the repository with command "git clone https://github.com/bounswe/bounswe2023group1"
- Place the config folders to the relevant places given below:
  - Place project_env and project_env_prod into the folder bounswe2023group1\resq\backend\resq
  - Place anno_env and anno_env_prod into the folder bounswe2023group1\resq\backend\annotation
  - Place deploy.sh, annoDeploy.sh and aws-key.pem to the outer folder which containts bounswe2023group1.
  - deploy.sh is a script file which builds the docker image of the resq service, pushes it to the remote repository, sshs into ec2, pulls the latest image, and runs it in the docker container of the ec2 instance.
  - annoDeploy.sh is a script file which builds the docker image of the annotation service, pushes it to the remote repository, sshs into ec2, pulls the latest image, and runs it in the docker container of the ec2 instance.
  - Note that, if IP addresses of ec2 instances change, you need to update the ssh command which includes ec2-user@<IP_ADDRESS>. In case of ec2 instance stop & starts, IP addresses automatically change.

#### To access logs of the services in docker container of the ec2 instance
- ssh -i ./aws-key.pem ec2-user@<IP_ADDRESS>
- sudo docker ps
- sudo docker exec -it <container_name_or_id> /bin/bash
- cd ./config/resq/log
- tail -f resq (OR cat resq)
