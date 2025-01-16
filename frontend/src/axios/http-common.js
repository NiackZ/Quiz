import axios from "axios";
import {ACCESS_TOKEN, REFRESH_TOKEN} from "../constants/constants.js";
import {store} from "../store/index.js";

const $api =  axios.create({
    baseURL: "http://localhost:8080/api/",
    headers: {
        "Content-Type": "application/json"
    }
})
$api.interceptors.request.use( async (config) => {
    localStorage.getItem(ACCESS_TOKEN)
        ? config.headers.Authorization = `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
        : config.headers.Authorization = null;
    await store.dispatch("overlay/setLoading", true);
    return config
})

$api.interceptors.response.use( async (response) => {
    await store.dispatch("overlay/setLoading", false);
    return response;
}, async (error) => {
    const originalRequest = error.config
    if (error.response.status === 401 && originalRequest && !originalRequest._isRetry) {
        originalRequest._isRetry = true;
        originalRequest.headers.Authorization = null; //иначе рекурсия
        try {
            const refreshToken = localStorage.getItem(REFRESH_TOKEN);
            if (refreshToken) {
                const response = await $api.post('/auth/token', {refreshToken});
                if (response.data) {
                    localStorage.setItem(ACCESS_TOKEN, response.data[ACCESS_TOKEN]);
                    localStorage.setItem(REFRESH_TOKEN, response.data[REFRESH_TOKEN]);
                }
                return $api(originalRequest);
            }
        } catch (error) {
            console.log('Пользователь не авторизован!');
        }
    }
    await store.dispatch("overlay/setLoading", false);
    throw error;
})

export default $api