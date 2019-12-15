export default {
    namespaced: true,
    state: {
        services: []
    },
    getters: {
        services(state) {
            return state.services
        },
    },
    mutations: {
        services(state, services) {
            state.services = services
        },
        addService(state, service) {
            state.services.push(service)
        }
    },
}
