import Vuex from 'vuex'
import count from "./count/count.js";


export const store = new Vuex.Store({
    modules: {
        count
    }
})