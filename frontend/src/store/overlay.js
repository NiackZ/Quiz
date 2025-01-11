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
            console.log("Новый статус оверлея: " + status)
            state.overlayLoading = status;
        },
    }
}

export default overlay