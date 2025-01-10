import axios from "../http-common.js";
const URL = '/statuses';

export async function getStatuses() {
    return await axios.get(URL);
}

export function createStatus(studio) {
    return axios.post(URL, studio);
}

export async function saveStatus(id, studio) {
    return await axios.put(`${URL}/${id}`, studio);
}

export function deleteStatus(id) {
    return axios.delete(`${URL}/${id}`);
}