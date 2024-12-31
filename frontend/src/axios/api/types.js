import axios from "../http-common.js";
const URL = '/types';

export async function getTypes() {
    return await axios.get(URL);
}

export function createType(type) {
    return axios.post(URL, type);
}

export async function saveType(id, type) {
    return await axios.put(`${URL}/${id}`, type);
}

export function deleteType(id) {
    return axios.delete(`${URL}/${id}`);
}