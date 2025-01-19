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

export async function getStatuses() {
    return await axios.get("/statuses");
}

export async function getMarks() {
    return await axios.get("/marks");
}

export function getAnimeInfoForGrid() {
    return axios.get("/anime/short");
}

export async function getAnimeInfo(id) {
    return await axios.get(`/anime/${id}`);
}

export async function updateUser(data) {
    return await axios.put(`/users/${data.id}`, data);
}

export function isEmpty(obj) {
    return obj === null || obj === undefined;
}

export function isNotEmpty(obj) {
    return !isEmpty(obj);
}

export async function encodeImage(file) {
    return isEmpty(file)
        ? null
        : new Promise((resolve, reject) => {
        const reader = new FileReader();

        reader.onloadend = () => {
            const base64String = reader.result.split(',')[1];
            const dataToSend = {
                fileName: file.name,
                base64Image: base64String
            }
            resolve(dataToSend);
        };

        reader.onerror = () => {
            reject(new Error('Ошибка при кодировании изображения'));
        };

        reader.readAsDataURL(file);
    });
}