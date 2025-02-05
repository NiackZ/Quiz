import Vuex from 'vuex'
import auth from "./auth/auth.js";
import overlay from "./overlay.js";
import search from "./search.js";


export const store = new Vuex.Store({
    modules: {
        auth,
        overlay,
        search
    }
})