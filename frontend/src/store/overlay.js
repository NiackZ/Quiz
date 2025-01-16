const overlay = {
    namespaced: true,
    state: {
        overlayLoading: false
    },
    actions: {
        setLoading({ commit }, status) {
            commit("setOverlayLoading", status);
        }
    },
    mutations: {
        setOverlayLoading(state, status) {
            state.overlayLoading = status;
        },
    }
}

export default overlay