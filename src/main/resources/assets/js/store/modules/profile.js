export default {
    namespaced: true,
    state: {
        profile: false,
    },
    getters: {
        profile(state) {
            return state.profile
        },
        socials(state) {
            return state.profile.socials
        }
    },
    mutations: {
        profile(state, profile) {
            state.profile = profile
        },
        slug(state, slug) {
            state.profile.slug = slug
        },
        socials(state, socials) {
            state.profile.socials = socials
        },
        social(state, social) {
            state.profile.socials.push(social)
        },
        deleteSocial(state, id) {
            const i = state.profile.socials.findIndex(social => social.id === id)

            if (i !== -1) {
                state.profile.socials.splice(i, 1)
            }
        }
    },
}
