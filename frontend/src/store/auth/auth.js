import axios from '/src/axios/http-common'
import { ACCESS_TOKEN, REFRESH_TOKEN } from "/src/constants/constants.js";

const auth = {
    namespaced: true,
    state: {
        isAuth: false
    },
    actions: {
        async enter({commit}, data) {
            try {
                const loginJson = {
                    username: data.username,
                    password: data.password
                };
                console.log(loginJson);
                const response = await axios.post('/auth/login', loginJson);
                console.log('OK', response.data);
                if (response.data[ACCESS_TOKEN] && response.data[REFRESH_TOKEN]) {
                    const accessToken = response.data[ACCESS_TOKEN];
                    const refreshToken = response.data[REFRESH_TOKEN];
                    commit('saveTokens', { accessToken, refreshToken });
                }
            } catch (error) {
                console.log('ERROR', error.response.data);
            }
        },
        async logout({commit}) {
            try {
                await axios.post('/auth/logout')
                commit('deleteTokens');
            } catch (error) {
                console.log(error.response?.data);
            }
        },
        async validateToken({commit}) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            if (!!token) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                };
                try {
                    //const response = await axios.post('/auth/validate-token', {}, config);
                    //console.log('OK', response);
                    // (response[ACCESS_TOKEN] && response[REFRESH_TOKEN]) {
                    //     commit('saveTokens', {
                    //         accessToken: response[ACCESS_TOKEN],
                    //         refreshToken: response[REFRESH_TOKEN]
                    //     });
                }
                catch
                    (error)
                {
                    console.log('ERROR', error);
                }
            }
        },
    },
    mutations: {
        saveTokens(state, { accessToken, refreshToken }) {
            localStorage.setItem(ACCESS_TOKEN, accessToken);
            localStorage.setItem(REFRESH_TOKEN, refreshToken);
            state.isAuth = true;
        },
        deleteTokens(state) {
            localStorage.removeItem(ACCESS_TOKEN);
            localStorage.removeItem(REFRESH_TOKEN);
            state.isAuth = false;
        }
    }
}

export default auth