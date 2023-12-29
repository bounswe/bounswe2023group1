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
