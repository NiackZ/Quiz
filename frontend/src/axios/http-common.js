import axios from "axios";
import {ACCESS_TOKEN, REFRESH_TOKEN} from "../constants/constants.js";

const $api =  axios.create({
    baseURL: "http://localhost:8080/api/",
    headers: {
        "Content-Type": "application/json"
    }
})
$api.interceptors.request.use( (config) => {
    localStorage.getItem(ACCESS_TOKEN)
        ? config.headers.Authorization = `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
        : config.headers.Authorization = null;
    return config
})

$api.interceptors.response.use( (response) => {
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
                if (response.data[ACCESS_TOKEN]) {
                    console.log("Установлен новый ACCESS_TOKEN");
                    localStorage.setItem(ACCESS_TOKEN, response.data[ACCESS_TOKEN]);
                }
                return $api(originalRequest);
            }
        } catch (error) {
            console.log('Пользователь не авторизован!');
        }
    }
    else console.log('prev req');
    throw error;
})

export default $api