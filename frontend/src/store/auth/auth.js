import axios from '/src/axios/http-common'
import {TOKEN} from "../../constants/constants.js";

const auth = {
    namespaced: true,
    state: {
        isAuth: false
    },
    mutations: {
        async validateToken (state, payload) {
            if (!!payload.token) {
                const config = {
                    headers: {
                        Authorization: `Bearer ${payload.token}`
                    }
                };
                try {
                    const response = await axios.post('/auth/validate-token', {}, config)
                    console.log('OK', response);
                    if (response.data.token) localStorage.setItem(TOKEN, response.data.token)
                } catch (error) {
                    console.log('ERROR', error);
                }
            }
        }
    }
}

export default auth