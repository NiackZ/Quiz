import axios from '../axios/http-common.js';
import {getAnimeInfoForGrid, isEmpty} from "../utils/utils.js";

export default {
    namespaced: true,
    state: {
        animeResults: []
    },
    mutations: {
        setAnimeResults(state, results) {
            state.animeResults = results;
        }
    },
    actions: {
        async searchAnime({ commit }, query) {
            try {
                if (isEmpty(query)) {
                    commit("setAnimeResults", (await getAnimeInfoForGrid()).data);
                }
                else {
                    commit("setAnimeResults", (await axios.post("/anime/search", query)).data);
                }
            } catch (error) {
                console.error("Ошибка поиска:", error);
            }
        }
    },
    getters: {
        getAnimeResults: (state) => {
            return state.animeResults;
        }
    }
};
