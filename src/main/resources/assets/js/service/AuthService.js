import store from 'store/store'
import AuthApi from '../api/auth'
import ActionApi from '../api/action'
import router from '../router/router'
import userService from "./UserService";

export class AuthService {
    constructor(props) {
        this.authApi = new AuthApi(store)
        this.actionApi = new ActionApi(store)
    }

    async login(data) {
        let client = store.getters['auth/client']

        return this.authApi.token({...client, ...data})
            .then((response) => {
                if (response.status !== 200) {
                    return false
                }

                store.commit('auth/token', response.data)
                store.commit('auth/authenticated', true)

                return userService.sync()
            })
            .catch((response) => false)
    }

    async signup(data) {
        return this.actionApi.signup(data)
    }

    logout() {
        this.authApi.revoke({token: store.getters['auth/token'].access_token})

        store.commit('auth/token', false)
        store.commit('auth/authenticated', false)
        store.commit('profile/profile', false, {root: true})

        router.push('/signin')
    }

    async resendVerification(token) {
        return this.actionApi.resendVerification({token: token})
    }
}

export default new AuthService();
