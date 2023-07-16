import Vuex from 'vuex'
import count from "./count/count.js";
import auth from "./auth/auth.js";


export const store = new Vuex.Store({
    modules: {
        count,
        auth
    }
})