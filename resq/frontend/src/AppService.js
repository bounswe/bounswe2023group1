import axios from 'axios';
import {RootNode} from "./CategoryTree";

const API_BASE_URL = 'https://51.20.190.55'
const USER_API_BASE_URL = API_BASE_URL + '/resq/api/v1/user';
const AUTH_API_BASE_URL = API_BASE_URL + '/resq/api/v1/auth';
const ACTION_API_BASE_URL = API_BASE_URL + '/resq/api/v1/action';
const CATEGORY_API_BASE_URL = API_BASE_URL + '/resq/api/v1/categorytreenode';
const NEED_API_BASE_URL = API_BASE_URL + '/resq/api/v1/need';
const REQUEST_API_BASE_URL = API_BASE_URL + '/resq/api/v1/request';
const RESOURCE_API_BASE_URL = API_BASE_URL + '/resq/api/v1/resource';
const TASK_API_BASE_URL = API_BASE_URL + '/resq/api/v1/task';
const USER_INFO_API_BASE_URL = API_BASE_URL + '/resq/api/v1/profile';

export async function postRequestRole(userId, role) {
    const queryParams = new URLSearchParams({userId, role}).toString();
    const url = `${USER_API_BASE_URL}/requestRole?${queryParams}`;

    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    try {
        const response = await axios.post(url, null, config);
        return response.data;
    } catch (error) {
        throw error;
    }
}

/*
export async function postRequestRole(userId, role) {
    const requestBody = {
        userId: userId,
        role: role,
    };
    return axios.post(`${USER_API_BASE_URL}/requestRole`, requestBody);
}
*/

export async function getUserInfo(userId) {
    const {data} = await axios.get(`${USER_API_BASE_URL}/getUserInfo?userId=${userId}`);
    return {id: userId, ...data}
}

export function getAllAccess() {
    return axios.get(`${USER_API_BASE_URL}/all`);
}

export function getUserAccess() {
    return axios.get(`${USER_API_BASE_URL}/user`);
}

export function getFacilitatorAccess() {
    return axios.get(`${USER_API_BASE_URL}/facilitator`);
}

export function getAdminAccess() {
    return axios.get(`${USER_API_BASE_URL}/admin`);
}

export function getResponderAccess() {
    return axios.get(`${USER_API_BASE_URL}/responder`);
}

export function getVictimAccess() {
    return axios.get(`${USER_API_BASE_URL}/victim`);
}

export function getCoordinatorAccess() {
    return axios.get(`${USER_API_BASE_URL}/coordinator`);
}

export function signup(registerUserRequest) {
    return axios.post(`${AUTH_API_BASE_URL}/signup`, registerUserRequest);
}

export function signin(loginUserRequest, token) {
    return axios.post(`${AUTH_API_BASE_URL}/signin`, loginUserRequest, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    }).then(response => {
        return {
            token: response.data.jwt,
            userId: response.data.id,
            role: response.data.role
        };
    }).catch(error => {
        console.error('Sign in failed:', error);
        throw error;
    });
}


export function viewActions(taskId) {
    return axios.get(`${ACTION_API_BASE_URL}/viewActions?taskId=${taskId}`);
}

export function getMainCategories() {
    return axios.get(`${CATEGORY_API_BASE_URL}/getMainCategories`);
}

export function getSubCategoryByName(name) {
    return axios.get(`${CATEGORY_API_BASE_URL}/getSubCategoryByName?name=${name}`);
}

export function viewNeedsByFilter(filterParams) {
    return axios.get(`${NEED_API_BASE_URL}/viewNeedsByFilter`, {params: filterParams});
}

export function createNeed(userId, createNeedRequest) {
    return axios.post(`${NEED_API_BASE_URL}/createNeed?userId=${userId}`, createNeedRequest);
}

export function viewAllNeeds() {
    return axios.get(`${NEED_API_BASE_URL}/viewAllNeeds`);
}

export function viewNeed(userId, needId) {
    return axios.get(`${NEED_API_BASE_URL}/viewNeed?userId=${userId}&needId=${needId}`);
}

export function viewNeedsByUserId(userId) {
    return axios.get(`${NEED_API_BASE_URL}/viewNeedsByUserId?userId=${userId}`);
}

export function deleteNeed(userId, needId) {
    return axios.post(`${NEED_API_BASE_URL}/deleteNeed?userId=${userId}&needId=${needId}`);
}

export function updateNeed(updateNeedRequest, userId, needId) {
    return axios.post(`${NEED_API_BASE_URL}/updateNeed?userId=${userId}&needId=${needId}`, updateNeedRequest);
}

export function viewRequestsByUser(userId) {
    return axios.get(`${REQUEST_API_BASE_URL}/viewRequestsByUser?userId=${userId}`);
}

export function viewRequestsByLocation(longitude, latitude) {
    return axios.get(`${REQUEST_API_BASE_URL}/viewRequestsByLocation?longitude=${longitude}&latitude=${latitude}`);
}

export function createRequest(userId, createReqRequest) {
    return axios.post(`${REQUEST_API_BASE_URL}/createRequest?userId=${userId}`, createReqRequest);
}

export function viewAllRequests() {
    return axios.get(`${REQUEST_API_BASE_URL}/viewAllRequests`);
}

export function createResource(createResourceRequest, file) {
    const form = new FormData();
    form.append("createResourceRequest", new Blob([JSON.stringify(createResourceRequest)], { type : 'application/json' }))
    form.append("file", file)
    return axios.post(`${RESOURCE_API_BASE_URL}/createResource`, form);
}

export function getAllResources() {
    return axios.get(`${RESOURCE_API_BASE_URL}/filterByDistance?latitude=39.5&longitude=34.5&distance=10000`);
}

export async function getCategoryTree() {
    const {data} = await axios.get(`${CATEGORY_API_BASE_URL}/getMainCategories`);

    return new RootNode(data)
}

export function createTask(createTaskRequest) {
    return axios.post(`${TASK_API_BASE_URL}/createTask`, createTaskRequest);
}

export function acceptTask(taskId) {
    return axios.post(`${TASK_API_BASE_URL}/acceptTask?taskId=${taskId}`);
}

export function completeTask(taskId) {
    return axios.post(`${TASK_API_BASE_URL}/completeTask?taskId=${taskId}`);
}

export function completeAction(actionId) {
    return axios.post(`${ACTION_API_BASE_URL}/completeAction?actionId=${actionId}`);
}

export function createAction(action) {
    return axios.post(`${ACTION_API_BASE_URL}/createAction`, action);
}

export function addResources(body) {
    return axios.post(`${TASK_API_BASE_URL}/addResources`, body);
}

export function addRequests(body) {
    return axios.post(`${TASK_API_BASE_URL}/addRequestToTask`, body);
}

export function getAllUsers() {
    return Promise.all([...Array(40).keys()]
        .map(function (i) {
            return getUserInfo(i).catch(() => null);
        }))
        .then(results => results.filter(a => a))
}

export async function viewAllTasks(userId) {
    const {data} = await axios.get(`${TASK_API_BASE_URL}/viewTasks?userId=${userId}`);
    return data
}

export function updateProfile(userId, profileData) {
    return axios.post(`${USER_INFO_API_BASE_URL}/updateProfile?userId=${userId}`, profileData
    );
}
