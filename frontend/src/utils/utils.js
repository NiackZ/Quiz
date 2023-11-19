import axios from '/src/axios/http-common'

export async function checkRights(userId, rights) {
    return await axios.post('/rights/check-rights', {userId, rights});
}

export async function getTypes() {
    return await axios.get('/types');
}

export async function getStudios() {
    return await axios.get("/studios");
}

export async function getGenres() {
    return await axios.get("/genres");
}

export async function getStatuses() {
    return await axios.get("/statuses");
}