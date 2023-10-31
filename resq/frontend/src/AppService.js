import axios from 'axios';

const USER_API_BASE_URL = "http://16.16.63.194/resq/api/v1/user";
const AUTH_API_BASE_URL = "http://16.16.63.194/resq/api/v1/auth";
const ACTION_API_BASE_URL = 'http://16.16.63.194/resq/api/v1/action'; 
const CATEGORY_API_BASE_URL = 'http://16.16.63.194/resq/api/v1/categorytreenode'; 
const NEED_API_BASE_URL = 'http://16.16.63.194/resq/api/v1/need';
const REQUEST_API_BASE_URL = 'http://16.16.63.194/resq/api/v1/request'; 
const RESOURCE_API_BASE_URL = 'http://16.16.63.194/resq/api/v1/resource';
const TASK_API_BASE_URL = 'http://16.16.63.194/resq/api/v1/task';


class AppService {

    constructor() {
        this.token = null;
    }
    
    setToken(token) {
        this.token = token;
    }

    postRequestRole(userId, role) {
        const requestBody = {
            userId: userId,
            role: role,
        };
        return axios.post(`${USER_API_BASE_URL}/requestRole`, requestBody);
    }

    getUserInfo(userId) {
        return axios.get(`${USER_API_BASE_URL}/getUserInfo?userId=${userId}`);
    }

    getAllAccess() {
        return axios.get(`${USER_API_BASE_URL}/all`);
    }
    
    getUserAccess() {
        return axios.get(`${USER_API_BASE_URL}/user`);
    }
    
    getFacilitatorAccess() {
        return axios.get(`${USER_API_BASE_URL}/facilitator`);
    }
    
    getAdminAccess() {
        return axios.get(`${USER_API_BASE_URL}/admin`);
    }
    
    getResponderAccess() {
        return axios.get(`${USER_API_BASE_URL}/responder`);
    }
    
    getVictimAccess() {
        return axios.get(`${USER_API_BASE_URL}/victim`);
    }
    
    getCoordinatorAccess() {
        return axios.get(`${USER_API_BASE_URL}/coordinator`);
    }

    signup(registerUserRequest) {
        return axios.post(`${AUTH_API_BASE_URL}/signup`, registerUserRequest);
    }
    
    signin(loginUserRequest) {
        return axios.post(`${AUTH_API_BASE_URL}/signin`, loginUserRequest, {
          headers: {
            Authorization: `Bearer ${this.token}`,
          },
        });
    }

    viewActions(taskId) {
        return axios.get(`${ACTION_API_BASE_URL}/viewActions?taskId=${taskId}`);
    }

    getMainCategories() {
        return axios.get(`${CATEGORY_API_BASE_URL}/getMainCategories`);
    }
    
    getSubCategoryByName(name) {
        return axios.get(`${CATEGORY_API_BASE_URL}/getSubCategoryByName?name=${name}`);
    }

    viewNeedsByFilter(filterParams) {
        return axios.get(`${NEED_API_BASE_URL}/viewNeedsByFilter`, { params: filterParams });
    }
    
    createNeed(userId, createNeedRequest) {
        return axios.post(`${NEED_API_BASE_URL}/createNeed?userId=${userId}`, createNeedRequest);
    }
    
    viewAllNeeds() {
        return axios.get(`${NEED_API_BASE_URL}/viewAllNeeds`);
    }
    
    viewNeed(userId, needId) {
        return axios.get(`${NEED_API_BASE_URL}/viewNeed?userId=${userId}&needId=${needId}`);
    }
    
    viewNeedsByUserId(userId) {
        return axios.get(`${NEED_API_BASE_URL}/viewNeedsByUserId?userId=${userId}`);
    }
    
    deleteNeed(userId, needId) {
        return axios.post(`${NEED_API_BASE_URL}/deleteNeed?userId=${userId}&needId=${needId}`);
    }
    
    updateNeed(updateNeedRequest, userId, needId) {
        return axios.post(`${NEED_API_BASE_URL}/updateNeed?userId=${userId}&needId=${needId}`, updateNeedRequest);
    }

    viewRequestsByUser(userId) {
        return axios.get(`${REQUEST_API_BASE_URL}/viewRequestsByUser?userId=${userId}`);
    }
    
    viewRequestsByLocation(longitude, latitude) {
        return axios.get(`${REQUEST_API_BASE_URL}/viewRequestsByLocation?longitude=${longitude}&latitude=${latitude}`);
    }
    
    createRequest(userId, createReqRequest) {
        return axios.post(`${REQUEST_API_BASE_URL}/createRequest?userId=${userId}`, createReqRequest);
    }
    
    viewAllRequests() {
        return axios.get(`${REQUEST_API_BASE_URL}/viewAllRequests`);
    }

    createResource(createResourceRequest) {
        return axios.post(`${RESOURCE_API_BASE_URL}/createResource`, createResourceRequest);
    }

    createTask(createTaskRequest) {
        return axios.post(`${TASK_API_BASE_URL}/createTask`, createTaskRequest);
    }
    
    acceptTask(taskId, userId) {
        return axios.post(`${TASK_API_BASE_URL}/acceptTask?taskId=${taskId}&userId=${userId}`);
    }
    
    viewAllTasks(userId) {
        return axios.get(`${TASK_API_BASE_URL}/viewTasks?userId=${userId}`);
    }

}

export default new AppService()