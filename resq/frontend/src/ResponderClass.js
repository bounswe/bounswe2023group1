//Those are some unused classes and methods intended for
// a responder frontend profile but weren't been able to be deployed
// so they're to be stored here until they can be implemented
// at a near future


/*
class User{
    constructor(userID){
        this.userID=userID;
    }

}

class Responder extends User {
    constructor() {
        resources = [];
        skillset = [];
        tasks = [];
    }


    // Add a resource to the responder's resources list
    createResource(resource) {
        this.resources.push(resource);
    }

    // Add a skill to the responder's skillset
    createResource(skill) {
        this.skillset.push(skill);
    }


    // View all tasks assigned to this responder
    viewTasks() {
        return this.tasks;
    }

    // Accept a task and add it to the tasks list
    acceptTask(task) {
        this.updateTaskStatus(task, "accepted");
        this.tasks.push(task);
        task.setAsignee(this.userID);
    }

    // Decline a task by removing it from the tasks list
    declineTask(task) {
        this.updateTaskStatus(task, "denied");
        const index = this.tasks.indexOf(task);
        if (index > -1) {
            this.tasks.splice(index, 1);
        }
    }


    updateTaskStatus(task, status) {
        task.updateTaskStatus(status);
    }

    // Set a particular action as completed
    setCompleted(actionID, flag) {
        for (let task of this.tasks) {
            task.setCompleted(actionID, flag);
        }
    }

}


class Task {

    constructor(taskID, actions, description) {
        this.taskID = taskID;
        this.actions = actions;
        this.description = description;
        asigneeID = null;
        taskStatus = "notAccepted";
    }

    getTaskID(){
        return this.taskID;
    }

    setAsignee(asigneeID) {
        this.asigneeID = asigneeID;
        //SEND TO COORDINATOR
    }

    updateTaskStatus(taskStatus) {
        this.taskStatus = taskStatus;
        //SEND TO COORDINATOR
    }

    // View all actions of this task
    viewActions() {
        return this.actions;
    }

    // Set a particular action as completed
    setCompleted(actionID, flag) {
        for (let action of this.actions) {
            action.setCompleted(actionID, flag);
        }
    }

}

class Action {
    constructor(actionID, description) {
        this.actionID = actionID;
        this.description = description;
        this.isCompleted = false;
        this.isSelected = false;
        this.startLatitude = null;
        this.startLongitude = null;
        this.endLatitude = null;
        this.endLongitude = null;
    }

    //Get the coordinates fo start and end points
    getCoordinates(startLatitude, startLongitude, endLatitude, endLongitude){
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }

    //Select this action to do
    selectAction() {
        this.isSelected = true;
        //SEND TO COORDINATOR
    }

    //Cancel the selected action
    cancelAction() {
        this.isSelected = false;
    }

    // Set the action as completed
    actionComplete() {
        this.isCompleted = true;
        this.isSelected = false;

        //SEND TO VERIFY
    }

    // Check if the action is completed
    setCompleted(actionID, flag) {
        if (this.actionID === actionID && flag) {
            this.actionComplete();
        }
    }

    //Set a path for this action from start point to end point
    setActiionPath() {
        coordinates = [startLatitude, startLongitude, endLatitude, endLongitude];
        return coordinates;

        //SEND TO MAP
    }

}
*/
