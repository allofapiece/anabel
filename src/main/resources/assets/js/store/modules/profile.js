export default {
    namespaced: true,
    state: {
        profile: false,
    },
    getters: {
        profile(state) {
            return state.profile
        }
    },
    mutations: {
        profile(state, profile) {
            state.profile = profile
        },
        slug(state, slug) {
            state.profile.slug = slug
        },
    },
}
