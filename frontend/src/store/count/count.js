const count = {
    namespaced: true,
    state: {
        count: 0
    },
    mutations: {
        increment (state) {
            console.log('+1')
            state.count++
        }
    }
}

export default count