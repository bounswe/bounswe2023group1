# Table of Contents

1. Software Requirements Specification
2. Software Design Documents (UML Diagrams)
   - 2.1 Use Case Diagrams
   - 2.2 Class Diagrams
   - 2.3 Sequence Diagrams
3. Scenarios and Mockups
4. Project Plan
5. Communication Plan
6. RAM (Responsibility Assignment Matrix)
7. Weekly Reports and Any Additional Meeting Notes
    - 7.1 Project Development Weekly Progress Report 1
    - 7.2 Project Development Weekly Progress Report 2
    - 7.3 Project Development Weekly Progress Report 3
    - 7.4 Project Development Weekly Progress Report 4
8. Milestone Review
9. Individual Contributions

# 1. Software Requirements Specification
You can also see our Requirements [here](https://github.com/bounswe/bounswe2023group1/wiki/Requirements)

**Version**: 0.2.3

**Date**: 2023/04/08

**Prepared by** : 

   [Group 1](https://github.com/bounswe/bounswe2023group1)
* [Alperen Dağı](https://github.com/bounswe/bounswe2023group1/wiki/Alperen-Dağı)
* [Orkan Akısu](https://github.com/bounswe/bounswe2023group1/wiki/Orkan-Akısu)
* ~~[Cem Sarpkaya](https://github.com/bounswe/bounswe2023group1/wiki/Cem-Sarpkaya)~~
* [Çağrı Gülbeycan](https://github.com/bounswe/bounswe2023group1/wiki/Çağrı-Gülbeycan)
* [Elif Tokluoğlu](https://github.com/bounswe/bounswe2023group1/wiki/Elif-Tokluoğlu)
* [Furkan Bülbül](https://github.com/bounswe/bounswe2023group1/wiki/Furkan-Bülbül)
* [Harun Reşid Ergen](https://github.com/bounswe/bounswe2023group1/wiki/Harun-Reşid-Ergen)
* [Ilgaz Er](https://github.com/bounswe/bounswe2023group1/wiki/Ilgaz-Er)
* [Kübra Aksu](https://github.com/bounswe/bounswe2023group1/wiki/Kübra-Aksu)
* [Muhammet Ali Topcu](https://github.com/bounswe/bounswe2023group1/wiki/Muhammet-Ali-Topcu)
* ~~[Sezer Çot](https://github.com/bounswe/bounswe2023group1/wiki/Sezer-Çot)~~
* [Volkan Öztürk](https://github.com/bounswe/bounswe2023group1/wiki/Volkan-Öztürk)
* [Orkan Akısu](https://github.com/bounswe/bounswe2023group1/wiki/Orkan-Akısu)
* [Elif Tokluoğlu](https://github.com/bounswe/bounswe2023group1/wiki/Elif-Tokluoğlu)
* [Hüseyin Türker Erdem](https://github.com/bounswe/bounswe2023group1/wiki/Hüseyin-Türker-Erdem)

**Prepared for**:

Boğaziçi University - CMPE 352 Fundamentals of Software Engineering - Spring 2023

**Revision History**

| Name| Date | Reason for Change | Version | Issue
| -- | -- | -- | -- | -- |
| 1 | 2023/03/21 | Adding initial version | 0.1 | - |
| 2 | 2023/03/28 | Adding initial glossary | 0.2 | [73](https://github.com/bounswe/bounswe2023group1/issues/73) |
| 3 | 2023/04/02 | Adding user role: admin | 0.2.1 | [66](https://github.com/bounswe/bounswe2023group1/issues/66) |
| 4 | 2023/04/08 | Eliminating conflicts in requirements and formalizing according to SRS, Glossary revision | 0.2.2 | [79](https://github.com/bounswe/bounswe2023group1/issues/79) [88](https://github.com/bounswe/bounswe2023group1/issues/88) [113](https://github.com/bounswe/bounswe2023group1/issues/113) |
| 5 | 2023/04/10 | Resolving inconsistencies between mockups, use case diagrams and requirements. | 0.2.3 |[104](https://github.com/bounswe/bounswe2023group1/issues/104) [119](https://github.com/bounswe/bounswe2023group1/issues/119)|
| 6 | 2023/10/10 | Reviewing and enhancing requirements due to requirements language syntax and former feedback from the last semester. Inconsistencies are eradicated with overall design (mockups, diagrams), any ambiguities are eliminated with regards to the syntax of requirements for clear understanding. | 0.3 |[240](https://github.com/bounswe/bounswe2023group1/issues/240) []()|
## Glossary

- **Account**: A personal profile created by a user to access the system's features and services.
- **Action**: A specific tasks or activities that need to be taken in response to a event.
- **Admin**: A user role responsible for the maintaining the system.
- **Automatic authentication**: A verification mechanism that automatically verifies a user's identity or credentials.
- **Ban**: A feature that blocks a user from accessing the system due to non-compliance with the system's policies or inappropriate behavior, including system abuse or misinformation.
- **Coordinator**: A user role responsible for managing and coordinating the response efforts. Coordinator is the user that has the most permissions.
- **Due date**: A deadline set for a task to be completed.
- **Event**: A specific occurrence or situation that requires action.
- **Facilitator**: A user role responsible for providing accurate information on the needs and situation of the victims and communities. Also verifies the actions that are conducted by responders.
- **Feedback**: Comments or suggestions given by the facilitator or responder about a specific action or task.
- **Information**: A knowledge or data that is communicated or received about a particular subject or event(A fire broke out, a hospital opened etc.).
- **Live statistics**: Statistics that admins can see. They include the number of users, the number of actions completed, in-progress, on-hold, etc.
- **Manual authentication**: A verification mechanism that requires a human to verify a user's identity or credentials.
- **Multilingual**: A system or platform that supports multiple languages.
- **Need**: A specific requirements or functionalities that are required to be included in the system.
- **Resource**: Refers to both labor(medical support, driving, etc.) and materials(food, clothing, tents, etc.).
- **Request**: A user-generated submission that requires a response or action from another user.
- **Responder**: A user role for individuals who provide assistance to the victims, such as delivering supplies or offering medical care.
- **Status**: A label that indicates the current state of a task or request, such as completed, in progress, or rejected.
- **Sufficient Information**: Consists of standard information such as contact information and information change according to the type of resource(s) that the responder wants to provide.
- **Task**: A specific action that needs to be performed by the responders to fulfill a user's request.
- **To-do list**: A list of tasks or actions that need to be completed, often used for tracking progress.
- **Translation feature**: A feature that automatically translates text from one language to another.
- **User**: A user that uses the system with different purposes designated by respective role.
- **Verification mechanism**: A process to authenticate and verify the identity and credentials of the (mainly) facilitator user role.
- **Verify**: The process of accepting or validating a user's request or action by a coordinator or facilitator or responder.
- **Victim**: A user role for individuals affected by a disaster (multiple disasters) that require assistance & support and possibly have some needs.


# Requirements

## 1. Functional Requirements

### 1.1 User Requirements

- 1.1.1 User Registration
     > - Users shall be able to create an account on the system. Considering emergency situations, account creation is not mandatory situation.
- 1.1.2 User Roles
     > - Users shall have at least one of the roles below:

  - 1.1.2.1 Victim
     > - 1.1.2.1.1 Any user should be able to assume the role of a victim after providing the necessary information to the system.
     > - 1.1.2.1.2 Victims must have the capability to report their current situation and needs.
     > - 1.1.2.1.3 Victims should be able to access critical locations (Help centers, soup kitchens etc.)
     > - 1.1.2.1.4 Victim shall be able to get notifications and directions whenever any relevant asistance is available for him/her.

  - 1.1.2.2 Coordinator
     > - 1.1.2.2.1 Coordinators must be assigned by administrators.
    > - 1.1.2.2.2 Only administrators can unassign coordinators.
    > - 1.1.2.2.3 Coordinators should have the authority to suspend non-coordinator or non-administrator users.
    > - 1.1.2.2.4 Coordinators can request responders to take specific actions with providing all necessary details.
    > - 1.1.2.2.5 Coordinators shall be able to create tasks consisting of to-do action lists that can be marked as completed by responders, allowing for easy progress tracking of the tasks
    > - 1.1.2.2.6 Coordinators have the ability to remove assignees from tasks.
    > - 1.1.2.2.7 Coordinators shall receive notifications for new responders signing up for specific needs. 
    > - 1.1.2.2.8 Coordinators shall be able to view all user profiles, actions, and information provided by facilitators.
    > - 1.1.2.2.9 Coordinators shall be able to view, delete or reply to the requests.
    > - 1.1.2.2.10 Coordinators can share and update information they provide.
    > - 1.1.2.2.11 Coordinators shall be able to delete or update information shared by facilitators.
    > - 1.1.2.2.12 Coordinators shall be able to view, delete or reply to reported problems.
    > - 1.1.2.2.13 Coordinators shall be able to send requests to the admins.

  - 1.1.2.3 Facilitator
     > - 1.1.2.3.1 Responders and victims can apply for a facilitator role.
    > - 1.1.2.3.2 Facilitator role requires an additional verification process.
    > - 1.1.2.3.3 The facilitator role shall be granted through automatic authentication by the system, or through manual authentication by a coordinator or an admin.
    > - 1.1.2.3.4 Facilitators shall be able to create requests.
    > - 1.1.2.3.5 Facilitators shall be able to create resources.
    > - 1.1.2.3.6 Facilitators can view victims' needs and include them in their requests.
    > - 1.1.2.3.7 Facilitators can update requests with trackable changes.
    > - 1.1.2.3.8 Facilitator shall be able to provide feedback on any ongoing actions sent by the coordinators.
    > - 1.1.2.3.9 Facilitators should verify action requests from responders.
    > - 1.1.2.3.10 Facilitators shall be able to share and update information they provide.

  - 1.1.2.4 Responder
    > - 1.1.2.4.1 Responders must provide information about the resources they can offer and add them to their profiles.
    > - 1.1.2.4.2 Any user can become a responder after providing sufficient information to the system.
    > - 1.1.2.4.3 Responders can accept or decline task assignments from coordinators.
    > - 1.1.2.4.4 Responders can comment on their actions using the sections on the windows provided for their actions.
    > - 1.1.2.4.5 Responders can update the status of their tasks as: not started, in progress, completed.
    > - 1.1.2.4.6 Responders shall be able to view all information provided by facilitators.
    > - 1.1.2.4.7 Responders can check off items in to-do lists provided by coordinators to track their actions.
    > - 1.1.2.4.8 Responders shall be able to see their current and previous tasks. 


  - 1.1.2.5 Admin
    > - 1.1.2.5.1 Admins shall be able to assign coordinator and facilitator roles to designated users.
    > - 1.1.2.5.2 Administrators can revoke user roles in cases of abuse or misuse.
    > - 1.1.2.5.3 Administrators can handle bug reports, respond to them, and apply fixes.
    > - 1.1.2.5.4 Administrators can provide technical assistance to coordinators.
    > - 1.1.2.5.5 Administrators can manually verify documents when needed.
    > - 1.1.2.5.6 Administrators can dynamically expand the system based on user needs and feedback.
    > - 1.1.2.5.7 Admin roles are exclusively granted by project owners.
    > - 1.1.2.5.8 Administrators can send notifications to other administrators.
    > - 1.1.2.5.9 Administrators have access to live system statistics.

- 1.1.3. Location Services
    > - Users shall be able to view the map and share their current location information on the map.
- 1.1.4. Information Filtering
    > - Users shall be able to search and filter the information provided by facilitators.
- 1.1.5. Disaster Reporting
    > - Users can report warnings about current disasters.

### 1.2 System Requirements

- Disaster Requirements

- 1.2.1 Multi-hazard support
   > - 1.2.1.1 The system shall provide multi-hazard support. The system shall support various types of disasters and emergencies, including natural disasters (e.g. earthquakes, floods, hurricanes), man-made disasters (e.g. explosions, fires, terrorist attacks), and public health emergencies (e.g. pandemics, epidemics). The system must be flexible enough to accommodate different needs and response strategies based on the disaster type.

- 1.2.2 Multilingual Support
    > - 1.2.2.1. The platform must support multiple languages to cater to a diverse user base.

- 1.2.3 Resource Management 

  - 1.2.3.1. Digital Resources
    > - 1.2.3.1.2. Resources must be digitized and quantified in the system.

  - 1.2.3.2. Categorization of resources
    > - 1.2.3.2.1. To facilitate distribution, sent resources should be categorized in detail, including the contents of each box, quantity, shoe/clothing size, etc. 

  - 1.2.3.3. Semantic relations between resources
     > - 1.2.3.3.1. Resources should have semantic relationships for efficient categorization.

  - 1.2.3.4. Dynamic needs
    > - 1.2.3.4.1. The platform should be flexible enough to adapt to the changing needs of disaster areas, depending on the location and stage of the disaster. This includes different needs in urban and rural areas, as well as different needs for surviving the disaster and educational needs.

- 1.2.4. Event Creation

  - 1.2.4.1. Creating New Events
    > - 1.2.4.1.1. The system shall allow users to create new events to notify other users of new supplies, requests for help, or other relevant information.
    > - 1.2.4.1.2. The system shall require users to provide details such as the event type (e.g., new supply arrival, urgent medical need), the event location, and any additional relevant information.
    > - 1.2.4.1.3 The system shall allow people to prioritize events.
    > - 1.2.4.1.4. The system shall ensure that all required fields are filled in before an event can be created.

  - 1.2.4.2. Target Audience
    > - 1.2.4.2.1. The system shall provide a feature for users to specify the target audience for the event (e.g., all users, users within a certain distance of the event location, and users with specific skills or resources).

  - 1.2.4.3. Duration and Visibility
    > - 1.2.4.3.1. The system shall allow users to specify a duration or expiration date for the event, after which it will no longer be visible to users.

  - 1.2.4.4. Event Filtering
    > - 1.2.4.4.1. The system shall allow users to filter events based on various criteria, such as event type, location, and target audience.
  
  - 1.2.4.5. Event Categorization
    > - 1.2.4.5.1. The system shall provide a feature to categorize the events based on different types of events such as supply notifications, medical needs, etc.
  
- 1.2.5. Actions

  - 1.2.5.1 Creating new actions
    > - 1.2.5.1.1. The system shall allow authorized users to create new actions in response to disaster events.
    > - 1.2.5.1.2. The system shall allow authorized users to provide action details such as the action type, the target group, and the due date.
    > - 1.2.5.1.3. The system shall ensure that all required fields are filled in before an action can be created.
    > - 1.2.5.1.4. The system shall provide a feature to categorize the actions based on different types of actions such as transportation, supply delivery, medical aid, etc.

  - 1.2.5.2. Task Assignment:
    > - 1.2.5.2.1. The system shall allow authorized users to assign specific tasks to other users.
    > - 1.2.5.2.2. The system shall allow authorized users to set deadlines for task completion.

  - 1.2.5.3. Prioritization
    > - 1.2.5.3.1. The system shall provide a feature to prioritize actions based on their urgency and impact on the affected population.

  - 1.2.5.4. Linking Actions to Events
    > - 1.2.5.4.1. The system shall allow authorized users to link the action to the corresponding event.
    > - 1.2.5.4.2. The system shall notify users who have expressed interest in the event.

  - 1.2.5.5 Actions Filtering 
    > - 1.2.5.5.1. The system shall allow users to filter actions based on various criteria, such as action type, location, and target audience.

- 1.2.6. Action Tracking

  - 1.2.6.1. Tracking action progress
    > - 1.2.6.1.1. The system shall allow authorized users to track the progress of each action and its associated tasks.
    > - 1.2.6.1.2. The system shall provide a feature to notify users when tasks are overdue or completed.
    > - 1.2.6.1.3. The system shall allow authorized users to update the status of each action and its associated tasks.
   
  - 1.2.6.2. Generating reports
    > - 1.2.6.2.1. The system shall provide a feature to generate reports on the overall progress of the disaster response efforts, including completed and pending actions, as well as their impact on the affected population.

- 1.2.7. Map-based operations
    > - 1.2.7.1. The platform should have a map-based interface that displays the warnings, where disasters occur most, actions, events taken during the disaster, 
    > - 1.2.7.2 The platform should have map can have various filtering options like location, category etc..
    > - 1.2.7.3 The platform should allow creating path on the map.


## 2. Non Functional Requirements

### 2.1 Security and Privacy

- 2.1.1. Data protection regulations
  > - 2.1.1.1 The platform shall prioritize and fully comply with applicable data protection regulations, including but not limited to the General Data Protection Regulation (GDPR) in Europe and the KVKK (Kişisel Verilerin Korunması Kanunu) in Turkey, to ensure the lawful and ethical handling of user data.

- 2.1.2. Personal information protection and confidentiality
  > - 2.1.2.1 The platform shall implement robust measures to safeguard the personal information and contact details of individuals affected by disasters, ensuring their privacy and confidentiality are maintained at all times. This includes encryption, access controls, and secure data storage practices.


# 2. Software Design Documents (UML Diagrams)
## 2.1 Use Case Diagrams
You can see our Use Case Diagrams [here](https://github.com/bounswe/bounswe2023group1/wiki/Use-Case-Diagrams)
## 2.2 Class Diagrams
You can see our Class Diagrams [here](https://github.com/bounswe/bounswe2023group1/wiki/Class-Diagrams)
## 2.3 Sequence Diagrams
You can see our Sequence Diagrams [here](https://github.com/bounswe/bounswe2023group1/wiki/Sequence-Diagrams)

# 3. Scenarios and Mockups
# 4. Project Plan
# 5. Communication Plan
| Platform | Purpose(s) | Time | Participants |
| :------- | :--------- | :--- | :----------- |
|Face to Face|Weekly Labs|Tuesdays 15.00-17.00|All Group Members|
|Discord|Online Extra Meetings & Project Review|Any time|All Group Members and Groups for Sub-teams|
|Discord|Online Extra Meetings & Project, Tasks and Code Review|Any time|All Group Members|
|Github|Documenting issues, reviewing prs, by so having communication from github sometimes best communication|Any time|All Group Members|
|Whatsapp|Instant messaging & Decision making|Any time|All Group Members and Groups for Sub-teams|

***

## Weekly Lab Meetings

1. Determining the report wroter person for the lab
   1. Will ask for volunteer person
   1. If there isn’t any, it will be randomly assigned among the ones who haven’t done yet
1. Conversation about the previous week’s feedbacks, works done or not completed, additional tasks not planned before and next week's plan. 
   Also different sub-teams find the opportunity to meet together. 
1. Discussion about the unfinished tasks (if any). Those are important and we examine why they are not finished and if there is any problem. 
   Also they are added to the next week's schedule.
1. Going over the last week's lab report and extracting the main tasks item by item 
1. Analyzing project and deciding what to do next and making evaluations to understand the details and expected durations of the tasks
1. After all tasks are determined, those tasks are assigned to the people 
1. Determining deadlines for the tasks
1. As project goes on, additional meetings other than lab became necessary so we usually have meetings with our subgroups and when the problems or confusions arise, we also make meetings with other sub-groups.
# 6. RAM (Responsibility Assignment Matrix)

# 7. Weekly Reports and Any Additional Meeting Notes
## 7.1 roject Development Weekly Progress Report 1

**Team Name:** Rapid Response (Disaster Response Platform)  
**Date:** 03.10.2023

### Progress Summary
**This week** focused on deciding on the general structures of the teams. We set up the teams for the back-end, the front-end, and the mobile. **Our objective for the following week** re-verify project requirements, decide on front-end, back-end, and mobile technologies, ensure cross-team technology compatibility, and review project diagrams for clarity and accuracy.
### What was planned for the week? How did it go?

| Description | Issue | Assignee | Due | PR | Estimated Duration | Actual Duration | 
| -------- | ----- | -------- | --- | --- | --- | --- |


### Completed tasks that were not planned for the week

| Description  | Issue | Assignee | Due | PR |
| -------- | ----- | -------- | --- | --- |

### Planned vs. Actual

### Your plans for the next week
| Description | Issue | Assignee | Due | Estimated Duration |
| --- | --- | --- | --- | --- |
| Re-verify the requirements | [#251](https://github.com/bounswe/bounswe2023group1/issues/251) | Team | 10.10.2023 | 1hr | 1.5hr |
| Decide on what technologies are going to be used for the front-end | [#248](https://github.com/bounswe/bounswe2023group1/issues/248) | Front-Team | 10.10.2023 | 1hr |
| Decide on what technologies are going to be used for the back-end | [#247](https://github.com/bounswe/bounswe2023group1/issues/247) | Backend-Team | 10.10.2023 | 1hr |
| Decide on what technologies are going to be used for the mobile | [#249](https://github.com/bounswe/bounswe2023group1/issues/249) | Mobile | 10.10.2023 | 1hr |
| Decide on project name | [#250](https://github.com/bounswe/bounswe2023group1/issues/250) | Team | 10.10.2023 | 15mins |
| Cross-team technology sync - verify that these technologies are compatible | [#251](https://github.com/bounswe/bounswe2023group1/issues/251) | Team | 10.10.2023 | 1hr | 15mins |
| Review the class diagram , use case diagram, sequence diagram | [#243](https://github.com/bounswe/bounswe2023group1/issues/243), [#242](https://github.com/bounswe/bounswe2023group1/issues/242), [#244](https://github.com/bounswe/bounswe2023group1/issues/244)| Team | 10.10.2023 | 4hr | 5hr |
| Reorganize the home page of wiki| [#253](https://github.com/bounswe/bounswe2023group1/issues/253)| Team | 10.10.2023| 30mins | 

### Risks
- Extended user roles (e.g., facilitator, responder) may require additional fields that we did not consider in the initial design. We will address these issues as they arise.
- We may face compatibility issues when integrating different technologies for the front-end, back-end, and mobile aspects of the project. 

### Participants
- Alperen Dağı
- Ali Topcu
- Çağrı Gülbeycan
- Elif Tokluoğlu
- Furkan Bülbül
- Harun Reşid Ergen
- Kübra Aksu
- Orkan Akısu
- Volkan Öztürk
- Türker Erdem

## 7.2 Project Development Weekly Progress Report 2

**Team Name:** ResQ (Disaster Response Platform)  
**Date:** 10.10.2023

### Progress Summary 
We have started revising the planning including the requirements, mock-ups, and diagrams. Various mistakes were fixed, some were discussed and many of them are planned to fixed this week. Newcomer friends had access to the GitHub and our communication channels. Separate teams had discussion about their tech stack. Cross-team sync on tech stacks are ensured to be safe. Last but not least as a team we have decided our project name __**ResQ**__ !

### What was planned for the week? How did it go?

| Description | Issue | Assignee | Due | PR | Estimated Duration | Actual Duration | 
| -------- | ----- | -------- | --- | --- | --- | --- |
| Re-verify the requirements | [#240](#) | Team | 10.10.2023 | -- |  1hr | 4hr |
| Decide on what technologies are going to be used for the front-end | [#248](#) | Front-Team | 10.10.2023 | -- | 1hr | 1hr |
| Decide on what technologies are going to be used for the back-end | [#247](#) | Backend-Team | 10.10.2023 | -- | 1hr | 1hr |
| Decide on what technologies are going to be used for the mobile | [#249](#) | Mobile Team | 10.10.2023 | -- | 1hr | 1hr |
| Decide on project name | [#250](#) | Team | 10.10.2023 | -- | 1hr | 20mins |
| Cross-team technology sync - verify that these technologies are compatible | [#251](#) | Team | 10.10.2023 | -- | 1hr | 15mins |
| Review the class diagram , use case diagram, sequence diagram | [#242](#), [#243](#), [#244](#) | Team | 10.10.2023 | -- | 1hr | 2hr |
| Reorganize the home page of wiki | [#253](#) | Team | 10.10.2023 | -- | 1hr | 30mins |




### Completed tasks that were not planned for the week

| Description  | Issue | Assignee | Due | PR |
| -------- | ----- | -------- | --- | --- |
| Welcome the newcomers | -- | Team | 10.10.2023 | -- |
| Edited sidebar | -- | Team | 10.10.2023 | -- |


### Planned vs. Actual
- We planned to decide on the front-end, back-end, and mobile technologies by this week ([#247](#), [#248](#), [#249](#)). Each team discussed separately and decided the tech stack. Mainly we made decision based on the prior experience. <br>
- **Back-end team** will be using Java, Spring Boot, and PostgreSQL stack. <br>
- **Front-end team** will be using JavaScript and React.
- **Mobile team** will be using Android Studio, Kotlin.
- We had updates on requirements, diagrams. However, we could not finish to revise all.



### Your plans for the next week
| Description | Issue | Assignee | Due | Estimated Duration |
| --- | --- | --- | --- | --- |
| Initial code setup in Spring Boot | [#255](#) | Ali, Furkan | 17.10.2023 | 30 mins|
| Swagger Api documentation integration | [#255](#) | Ali, Furkan |  17.10.2023 |30 mins|
| Logger integration| [#255](#) | Ali, Furkan |17.10.2023 | 1 hours|
| Implementation of exception handling| [#255](#) | Back-end Team| 17.10.2023 | 1 hours|
| Practicing Spring Boot in details| [#257](#) | Volkan, Türker | 17.10.2023 | 2 hr |
| Examining the practice app as an end-to-end example | [#256](#) | Back-end Team | 17.10.2023 | 1 hr |
| Start designing database relations while revising class diagrams| [#259](#259) |Back-end Team | 17.10.2023 | 2 hr |
| Create a project timeline for back-end | [#260](#260) | Back-end Team | 12.10.2023 | 3 hr |
| Create a project timeline for front-end | [#261](#261) | Front-end Team | 12.10.2023 | 3 hr |
| Create a project timeline for mobile | [#262](https://github.com/bounswe/bounswe2023group1/issues/262) | Mobile Team | 12.10.2023 | 3 hr |
| Continue on the revising diagrams | [#242](#242), [#243](#243), [#244](#244) | Team | 17.10.2023 | 3 hr |
| Reset the branches that are used for practice app, and create new base branches| [#263](#263)| Team | 17.10.2023 | 1 hr|
| Reviewing mock-ups to create a base for front-end development| [#271](#271) | Front-end Team | 17.10.2023 | 2 hr |
| Study react | [#258](#258) | Front-end Team | 17.10.2023 | 3 hr |
| Initial setup of front-end codebase| [#264](#264) | Front-end Team | 17.10.2023 | 3 hr |
| Research the MVVM architecture, dependency injection | [#265](https://github.com/bounswe/bounswe2023group1/issues/265) | Mobile Team | 17.10.2023 | 3 hr |
| Adding mobile tags | [#267](https://github.com/bounswe/bounswe2023group1/issues/267) | Mobile Team | 17.10.2023 | 15mins |
| Study on Jetpack Compose sample tutorials| [#266](https://github.com/bounswe/bounswe2023group1/issues/266) | Mobile Team | 17.10.2023 | 2 hr |
| Start creating page design | [#270](https://github.com/bounswe/bounswe2023group1/issues/270) | Mobile Team | 17.10.2023| 3 hr |
| Reviewing mock-ups considering Mobile design| [#269](https://github.com/bounswe/bounswe2023group1/issues/269) | Front-end Team | 17.10.2023 | 2 hr |
| Update RAM | [#268](#268) | Team | 17.10.2023 | 3 hr |
| Update Sidebar to seperate courses as cmpe352 and cmpe451| [#239](#) | Elif | 17.10.2023 | 1 hr |


### Risks
- Creating database design may require extensive effort due to possible vagueness in class diagrams.
- Mobile team may encounter a hardship to create a standard on design.


### Participants
- Alperen Dağı
- Ali Topcu
- Çağrı Gülbeycan
- Elif Tokluoğlu
- Furkan Bülbül
- Harun Reşid Ergen
- Ilgaz Er
- Kübra Aksu
- Orkan Akısu
- Volkan Öztürk
- Türker Erdem

## Project Development Weekly Progress Report 3

**Team Name:** ResQ (Disaster Response Platform)  
**Date:** 17.10.2023

### Progress Summary 
Initial code setup for teams done. 
The project timeline is established and monitored according to the "project plan" in the wiki. It may evolve as the project progresses, with necessary adjustments made as needed. 
The technologies that will be used in the project is determined as follows:
 - JS & React for frontend
 - Spring for backend framework
 - PostgreSQL for database
 - Kotlin and Jetpack Compose for mobile

Teams practiced these technologies.
The project branches were decided to be feature, dev, main, hotfix, release, and bugfix. 
Technologies compatibility cross check is done and everything works together.
Additional figma designes are created for mobile. Frontend team decided to update web mockups for the consistrency between mobile and web. 

### What was planned for the week? How did it go?

| Description | Issue | Assignee | Due | PR | Estimated Duration | Actual Duration | 
| -------- | ----- | -------- | --- | --- | --- | --- |
| Initial code setup in Spring Boot | [#255](#) | Ali, Furkan | 17.10.2023 | [#272](#) | 30 mins| 1 hr|
| Swagger Api documentation integration | [#255](#) | Ali, Furkan |  17.10.2023 | [#272](#) |30 mins| 1 hr|
| Logger integration| [#255](#) |  Ali, Furkan | 17.10.2023 | [#272](#) |30 mins| 30 mins|
| Implementation of exception handling| [#255](#) | Ali, Furkan | 17.10.2023 | [#272](#) |30 mins| 30 mins|
| Practicing Spring Boot in details| [#257](#) | Volkan, Türker, Furkan | 17.10.2023 | -- | 2 hr| 2 hr|
| Examining the practice app as an end-to-end example | [#256](#) | Back-end Team | 17.10.2023 | -- | 1 hr| 1 hr|
| Start designing database relations while revising class diagrams| [#259](#259) |Back-end Team | 17.10.2023 | -- | 2 hr | 2 hr |
| Create a project timeline for back-end | [#260](#260) | Back-end Team | 12.10.2023 | -- | 2 hr | 2 hr |
| Create a project timeline for front-end | [#261](#261) | Kübra, Ilgaz | 12.10.2023 | -- | 3 hr | 3 hr |
| Create a project timeline for mobile | [#262](https://github.com/bounswe/bounswe2023group1/issues/262) | Mobile Team | 17.10.2023 | -- | 2 hr | 2 hr |
| Continue on the revising diagrams | [#242](#242), [#243](#243), [#244](#244) | Back-end Team | 17.10.2023 | -- | 1 hr | 1 hr|
| Reset the branches that are used for practice app, and create new base branches. Planned, not done. | [#263](#263)| Team | 17.10.2023 | -- | 1 hr | ongoing |
| Reviewing mock-ups to create a base for front-end development| [#271](#271) | Front-end Team | 17.10.2023 | --| 2 hr | 2 hr|
| Study react | [#258](#258) | Front-end Team | 17.10.2023 | -- | 3 hr | 3 hr|
| Initial setup of front-end codebase| [#264](#264) | Front-end Team | 17.10.2023 | -- | 3 hr | 3 hr|
| Research the MVVM architecture, dependency injection | [#265](https://github.com/bounswe/bounswe2023group1/issues/265) | Mobile Team | 17.10.2023 | -- | 2 hr | 2 hr |
| Adding mobile tags | [#267](https://github.com/bounswe/bounswe2023group1/issues/267) | Mobile Team | 17.10.2023 | -- | 15 min | 15 min |
| Study on Jetpack Compose sample tutorials| [#266](https://github.com/bounswe/bounswe2023group1/issues/266) | Mobile Team | 17.10.2023 | -- | 2 hr | 2 hr |
| Start creating page design | [#270](https://github.com/bounswe/bounswe2023group1/issues/270) | Harun | 17.10.2023 | -- | 6 hr | 6 hr |
| Reviewing mock-ups considering Mobile design| [#269](https://github.com/bounswe/bounswe2023group1/issues/269) | Front-end Team | 17.10.2023 | -- | 2 hr | ongoing |
| Update Sidebar to seperate courses as Cmpe352 and Cmpe451| [#239](#) | Kübra, Elif | 17.10.2023 | 1 hr | 1 hr|


### Completed tasks that were not planned for the week

| Description  | Issue | Assignee | Due | PR |
| -------- | ----- | -------- | --- | --- |
| Initial authentication and authorization for each user roles on backend | [#273](#273) | Ali | 24.10.2023| [#274](#274)| 

### Planned vs. Actual
- **Back-end team** did necessary tasks, additionally started to implement initial authentication and authorization for each user roles. <br>
- **Front-end team** completed project setup and revised mockups but still mockup revision not completed. Because mockups need to be updated to be in harmony with mobile. 
- **Mobile team** everything went just like planned.

### Your plans for the next week
| Description | Issue | Assignee | Due | Estimated Duration |
| --- | --- | --- | --- | --- |
| Update RAM | [#268](#268) | Team | 24.10.2023 | 3 hr |
| Continue on the revising diagrams | [#242](#242), [#243](#243), [#244](#244) | Back-end Team | 24.10.2023 | 2 hr | 
| Reviewing mock-ups considering Mobile design| [#269](https://github.com/bounswe/bounswe2023group1/issues/269) | Front-end Team | 17.10.2023 | 2 hr | 
| Reset the branches that are used for practice app, and create new base branches. Planned, not done. | [#263](#263)| Team | 17.10.2023 | 1 hr | 
| Login screen, registration | [#275](https://github.com/bounswe/bounswe2023group1/issues/275) | Harun | 24.10.2023| 10 hr|
| Main screen navigation bar | [#277](https://github.com/bounswe/bounswe2023group1/issues/277) | Alperen | 24.10.2023 | 10 hr |
| User profile screen | [#278](https://github.com/bounswe/bounswe2023group1/issues/278) | Çağrı | 24.10.2023 | 10 hr |
| Setting and notification page screen | [#280](https://github.com/bounswe/bounswe2023group1/issues/280) | Elif | 24.10.2023 | 10 hr |
| Deployment of backend | [#281](https://github.com/bounswe/bounswe2023group1/issues/281) | Back-end Team | 24.10.2023 | 4 hr |
| Frontend Investigate and Implement Authentication Stack | [#282](https://github.com/bounswe/bounswe2023group1/issues/282) | Front-end Team | 24.10.2023 | 10 hr |
| Design API for victim functionalities | [#283](https://github.com/bounswe/bounswe2023group1/issues/283) | Back-end Team | 24.10.2023 | 4 hr |
| Frontend Login and Registration Pages | [#279](https://github.com/bounswe/bounswe2023group1/issues/279) | Front-end Team | 24.10.2023 | 4 hr |
| Frontend Implement Draft Map Page | [#284](https://github.com/bounswe/bounswe2023group1/issues/284) | Front-end Team | 24.10.2023 | 6 hr |
| Implement location services and disaster reporting mechanism | [#285](https://github.com/bounswe/bounswe2023group1/issues/285) | Back-end Team | 24.10.2023 | 3 hr |

### Risks
- Backend team may have encounter problem with deployment.
- Mobile team may encounter a hardship at time management.
- Frontend team may encounter a hardship at time management because it is needed to learn tech before tasks.

### Participants
- Alperen Dağı
- Ali Topcu
- Çağrı Gülbeycan
- Elif Tokluoğlu
- Furkan Bülbül
- Harun Reşid Ergen
- Ilgaz Er
- Kübra Aksu
- Orkan Akısu
- Volkan Öztürk

## Project Development Weekly Progress Report 4

**Team Name:** ResQ (Disaster Response Platform)  
**Date:** 24.10.2023

### Progress Summary
**This week** focused on updating design documents and establishing the project's core structure. We improved the codebase for the back-end, the front-end, and the mobile in a way that they work consistently . **Our objective for the following week** will be to improve our code to satisfy more requirements and prepare for the milestone presentation.

### What was planned for the week? How did it go?

| Description | Issue | Assignee | Due | PR | Actual Duration | Estimated Duration |
| --- | --- | --- | --- | --- | --- | --- |
| Update RAM | [#268](https://github.com/bounswe/bounswe2023group1/issues/268) | Team | 24.10.2023 |--| 3hr | 3 hr |
| Continue on the revising diagrams | [#242](https://github.com/bounswe/bounswe2023group1/issues/242), [#243](https://github.com/bounswe/bounswe2023group1/issues/243), [#244](https://github.com/bounswe/bounswe2023group1/issues/244) | Back-end Team | 24.10.2023 | -- |3hr| 2 hr | 
| Reviewing mock-ups considering web page design| [#269](https://github.com/bounswe/bounswe2023group1/issues/269) | Front-end Team | 24.10.2023 | -- |1hr| 2 hr | 
| Reset the branches that are used for practice app, and create new base branches. Planned, not done. | [#263](https://github.com/bounswe/bounswe2023group1/issues/263)| Team | 24.10.2023 | -- || 1 hr | 
| Login screen, registration | [#275](https://github.com/bounswe/bounswe2023group1/issues/275) | Harun | 24.10.2023| -- | 8hr | 10 hr|
| Main screen navigation bar | [#277](https://github.com/bounswe/bounswe2023group1/issues/277) | Alperen | 24.10.2023 | -- | 5hr | 10 hr |
| User profile screen | [#278](https://github.com/bounswe/bounswe2023group1/issues/278) | Çağrı | 24.10.2023| -- | 6hr | 10 hr |
| Setting and notification page screen | [#280](https://github.com/bounswe/bounswe2023group1/issues/280) | Elif | 24.10.2023 | -- | 1hr | 10 hr |
| Deployment of backend | [#281](https://github.com/bounswe/bounswe2023group1/issues/281) | Back-end Team | 24.10.2023| -- |1hr | 4 hr |
| Frontend Investigate and Implement Authentication Stack | [#282](https://github.com/bounswe/bounswe2023group1/issues/282) | Front-end Team | 24.10.2023 |-- | 6hr | 10 hr |
| Design API for victim functionalities | [#283](https://github.com/bounswe/bounswe2023group1/issues/283) | Back-end Team | 24.10.2023| -- | 3hr| 4 hr |
| Frontend Login and Registration Pages | [#279](https://github.com/bounswe/bounswe2023group1/issues/279) | Front-end Team | 24.10.2023| -- |6hr | 4 hr |
| Frontend Implement Draft Map Page | [#284](https://github.com/bounswe/bounswe2023group1/issues/284) | Front-end Team | 24.10.2023| -- | 4hr| 6 hr |
| Implement location services via keeping location info on database  | [#285](https://github.com/bounswe/bounswe2023group1/issues/285) | Back-end Team | 24.10.2023 | -- |1hr | 3 hr |


### Completed tasks that were not planned for the week

| Description  | Issue | Assignee | Due | PR |
| -------- | ----- | -------- | --- | --- |
| Class diagram revisit | [#243](https://github.com/bounswe/bounswe2023group1/issues/243) | Volkan | 24.10.2023|
| Implementation of database relations | [#259](https://github.com/bounswe/bounswe2023group1/issues/259)| Ali | 24.10.2023| [#287] |

### Planned vs. Actual
- We planned to finish ([#275](https://github.com/bounswe/bounswe2023group1/issues/275), [#277](https://github.com/bounswe/bounswe2023group1/issues/277), [#278](https://github.com/bounswe/bounswe2023group1/issues/278), [#280](https://github.com/bounswe/bounswe2023group1/issues/280), [#281](https://github.com/bounswe/bounswe2023group1/issues/281))  issues last week, but they are a little more challenging than we anticipated so we will be still working on them even though we didn't plan that way.

### Your plans for the next week
| Description | Issue | Assignee | Due | Estimated Duration |
| --- | --- | --- | --- | --- |
| Main screen navigation bar continue | [#277](https://github.com/bounswe/bounswe2023group1/issues/277) | Alperen | 31.10.2023 | 5 hr |
| User profile screen continue| [#278](https://github.com/bounswe/bounswe2023group1/issues/278) | Çağrı | 31.10.2023 | 4 hr |
| Setting and notification page screen continue| [#280](https://github.com/bounswe/bounswe2023group1/issues/280) | Elif | 31.10.2023 | 9 hr |
| Login screen, registration continue | [#275](https://github.com/bounswe/bounswe2023group1/issues/275) | Harun | 31.10.2023 | 2 hr|
| Deployment of backend continue| [#281](https://github.com/bounswe/bounswe2023group1/issues/281) | Türker | 31.10.2023 | 3 hr |
| Creat User case scenario |[#298](https://github.com/bounswe/bounswe2023group1/issues/298) | Mobile Team | 31.10.2023| 8hr|
| Design API for victim functionalities | [#283](https://github.com/bounswe/bounswe2023group1/issues/283) | Volkan | 31.10.2023 | 3hr|
| Reviewing mock-ups considering web page design continue| [#269](https://github.com/bounswe/bounswe2023group1/issues/269) | Front-end Team | 31.10.2023  |4hr|
| Design API for responder functionalities|[#296](https://github.com/bounswe/bounswe2023group1/issues/296) | Furkan | 31.10.2023 | 4 hr |
| Creat User case scenario |[#299](https://github.com/bounswe/bounswe2023group1/issues/299)  | Frontend Team | 31.10.2023| 12hr|
| Implement jwt based authentication for login and queries|[#300](https://github.com/bounswe/bounswe2023group1/issues/300)  | Kübra | 31.10.2023| 12hr|
| Frontend Implement Draft Map Page | [#284](https://github.com/bounswe/bounswe2023group1/issues/284) | Ilgaz | 31.10.2023 | 10hr |
| Finalizing category tree | [#297](https://github.com/bounswe/bounswe2023group1/issues/297) | Ali | 31.10.2023 | 3hr |

### Risks
- Extended user roles (e.g., facilitator, responder) may require additional fields that we did not consider in the initial design. We will address these issues as they arise.
- It is a great possibility that frontend team can face challenges at jwt bassed authentiction because it requires new technical learning process.
- The map library we have chosen does not natively integrate with react. It has proven difficult to integrate it with react, which is why the issue is ongoing. These difficulties might persist. 
...

### Participants
- Alperen Dağı
- Ali Topcu
- Çağrı Gülbeycan
- Elif Tokluoğlu
- Furkan Bülbül
- Harun Reşid Ergen
- Ilgaz Er
- Kübra Aksu
- Orkan Akısu
- Volkan Öztürk


# 8. Milestone Review
# 9. Individual Contributions
Çağrı Gülbeycan - [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/%C3%87a%C4%9Fr%C4%B1-G%C3%BClbeycan-CMPE-451-Milestone-1-Individual-Contribution-Report)  

Muhammet Ali Topcu - [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/Muhammet-Ali-Topcu's-Individual-Contribution-Report)  

Harun Reşid Ergen - [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/Harun-Re%C5%9Fid-Ergen-Milestone-1-Individual-Contribution-Report) 

Alperen Dağı - [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/Alperen-Da%C4%9F%C4%B1-CMPE-451-Milestone-1-Individual-Contribution-Report)

Furkan Bülbül - [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/Furkan-Bülbül-CMPE-451-Milestone-1-Individual-Contribution-Report)

Orkan Akısu- [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/Orkan-Akisu's-Individual-Report-for-Milestone-1)

Volkan Öztürk - [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/Volkan-%C3%96zt%C3%BCrk-CMPE-451-Milestone-1-Individual-Contribution-Report)  

Ilgaz Er - [Individual Contribution Report](https://github.com/bounswe/bounswe2023group1/wiki/Ilgaz-Er-CMPE-451-Milestone-1-Individual-Contribution-Report)
