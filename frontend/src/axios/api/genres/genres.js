import axios from "../../http-common.js";
const GENRE_URL = '/genres';

export async function getGenres() {
    return await axios.get(GENRE_URL);
}

export function createGenre(genre) {
    return axios.post(GENRE_URL, genre);
}

export async function saveGenre(genreId, genre) {
    return await axios.put(`${GENRE_URL}/${genreId}`, genre);
}

export function deleteGenre(genreId) {
    return axios.delete(`${GENRE_URL}/${genreId}`);
}