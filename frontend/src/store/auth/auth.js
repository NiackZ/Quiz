import axios from '/src/axios/http-common'
import { ACCESS_TOKEN, REFRESH_TOKEN } from "/src/constants/constants.js";

const auth = {
    namespaced: true,
    state: {
        isAuth: false,
        loading: false
    },
    actions: {
        async enter({commit, dispatch}, data) {
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
                    dispatch('saveTokens', {accessToken, refreshToken});
                    commit('setAuthState', true);
                }
            } catch (error) {
                console.log('ERROR', error.response);
            }
        },
        async logout({commit, dispatch}) {
            try {
                await axios.post('/auth/logout')
                dispatch('deleteTokens');
                commit('setAuthState', false);
            } catch (error) {
                console.log(error);
            }
        },
        async validateToken({commit, dispatch}) {
            const token = localStorage.getItem(ACCESS_TOKEN);
            if (!!token) {
                commit('setLoadingState', true)
                try {
                    const response = await axios.post('/auth/validate-token');
                    console.log(response)
                    if (response.status === 200) {
                        commit('setAuthState', true)
                    }
                }
                catch (error) {
                    console.log('ERROR', error);
                    dispatch('deleteTokens');
                }
                commit('setLoadingState', false)
            }
        },
        saveTokens({ commit }, { accessToken, refreshToken }) {
            localStorage.setItem(ACCESS_TOKEN, accessToken);
            localStorage.setItem(REFRESH_TOKEN, refreshToken);
        },
        deleteTokens() {
            localStorage.removeItem(ACCESS_TOKEN);
            localStorage.removeItem(REFRESH_TOKEN);
        }
    },
    mutations: {
        setAuthState(state, isAuth) {
            console.log('isAuth ', isAuth)
            state.isAuth = isAuth;
        },
        setLoadingState(state, loading) {
            console.log('loading ', loading)
            state.loading = loading;
        }
    }
}

export default auth