import Cookies from 'js-cookie'

export default {
    namespaced: true,
    state: {
        authenticated: false,
        token: Cookies.get('token') !== undefined ? JSON.parse(Cookies.get('token')) : false,
        client: {
            client: process.env.VUE_APP_CLIENT_ID,
            secret: process.env.VUE_APP_SECRET,
            grant_type: 'password',
        },
        inProgress: false
    },
    getters: {
        token(state) {
            if (state.token) {
                return state.token
            }

            return false
        },
        inProgress(state) {
            return state.inProgress
        },
        client(state) {
            return state.client
        },
        authenticated(state) {
            return state.authenticated
        }
    },
    mutations: {
        token(state, token) {
            Cookies.set('token', token)
            state.token = token
        },
        authenticated(state, authenticated) {
            state.authenticated = authenticated
        },
        client(state, client) {
            state.client = client
        },
        inProgress(state, inProgress) {
            state.inProgress = inProgress
        }
    },
    actions: {}
}
