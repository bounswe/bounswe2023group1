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

- **Extensive Scenario:** Frontend
- The demonstration will follow the user story of Ahmet, a victim, and Ayşe, a responder offering some resources.

1. Ayşe is logging into the application to deliver her shopping for the victims at the disaster area. She brought 100 baby foods (resource- material resources) and wants to bring them to the resource gathering center so they can be delivered by a truck driver (another resource - human resource). She will start the demonstration in a logged-in state. She first navigates to the create resource page to create the resources she have in detail and she fills the resource address step with the information of the gathering center that she delivered the resources. Then, she chooses type of need which in this case is material resource and she types a part of what she is looking for (food) and it was recommended to her as baby food. So she founds the exact resource she was looking for. Then, she was able to add the quantity and the description of the resource she have. Afterwards, she navigates to the main page.And she can see her resource at list and map. 

2. Cem has previously logged into the application. Now, he finds himself on the main map page, where he sees different resources and disaster response centers which are defined with annotations such as category, short description, long description, date and location as longitude and latitude. There will be also metadata for annotation implementation of those centers. He then filters resources and annotations by type and opens the details of one of them. He then decides to create a request for some food for his family of four. Using the screens, he declares his family’s needs. He adds to the request that he has gluten intolerance and his wife has diabetes. Afterwards, he navigates to the own requests page in his profile to view his latest request and ensure he has written everything correctly. After a while, he refreshes to see a notification on the top that says a new sooıup kitchen near them.

- **Feature Completion:**
- **Victim:**
- Viewing resources on the map
- Creating request
- Viewing own requests
- **Responder:**
- Viewing assigned tasks on map and in list, accept or deny
- Creating resource
- Viewing own resources
- Notification page

- **Extensive Scenario:** Mobile
- Ahmet learned about the "ResQ" app from a friend and thought it could be helpful, especially since he lives in an earthquake-prone area. So, he downloaded the app on his phone.
When he opened the app, he had two choices on the login screen. Since he didn't have an account, he picked 'Create an account' and filled in his details, like email and password.
Ahmet didn't actively use the app since there wasn't any emergency, until one night an earthquake happened. Ahmet immediately opened the app. Once he signed in, he saw a map with pins showing where people needed help. Since Ahmet's building got damaged and it was dangerous to go inside, Ahmet needed food. He decided to add his own request for food.
Clicking on "Add Request" led him to a new screen. There, he chose 'Food' from a list and set his request priority to 'High' because he really needed help.
After making his request, Ahmet wanted to check his profile. On his "Victim User Profile," he found his name, birth date, and role as 'Victim.' There was also a list called 'My Requests' where he could see the request he just made.
Now, Ahmet waited, hopeful that someone would respond to his request. He knew there were kind people on the app always ready to help.
A few hours later, Ahmet got a notification. Someone nearby saw his request and was bringing him food. Feeling grateful, he was glad he used the "ResQ" app.

- **Feature Completion:**
- Settings page
- Notification page
- **Victim:**
- Viewing resources on the map
- Creating request
- Viewing own requests
- Resource creation for 


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
