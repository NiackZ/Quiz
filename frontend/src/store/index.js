import Vuex from 'vuex'
import auth from "./auth/auth.js";


export const store = new Vuex.Store({
    modules: {
        auth
    }
})