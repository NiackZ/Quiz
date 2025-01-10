import axios from "../http-common.js";
const URL = '/studios';

export async function getStudios() {
    return await axios.get(URL);
}

export function createStudio(studio) {
    return axios.post(URL, studio);
}

export async function saveStudio(id, studio) {
    return await axios.put(`${URL}/${id}`, studio);
}

export function deleteStudio(id) {
    return axios.delete(`${URL}/${id}`);
}