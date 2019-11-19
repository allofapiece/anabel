export default {
    namespaced: true,
    state: {
        socials: {}
    },
    getters: {
        socials(state) {
            return state.socials
        },
        byName: (state) => (name) => {
            for (let i = 0; i < state.socials.length; i++) {
                if (state.socials[i].name === name) {
                    return state.socials[i]
                }
            }

            return false
        }
    },
    mutations: {
        socials(state, socials) {
            state.socials = socials
        },
    },
}
