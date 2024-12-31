import axios from "../http-common.js";
const URL = '/genres';

export async function getGenres() {
    return await axios.get(URL);
}

export function createGenre(genre) {
    return axios.post(URL, genre);
}

export async function saveGenre(id, genre) {
    return await axios.put(`${URL}/${id}`, genre);
}

export function deleteGenre(id) {
    return axios.delete(`${URL}/${id}`);
}