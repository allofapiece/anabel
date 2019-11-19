import ProfileApi from 'api/profile'
import ActionApi from 'api/action'
import store from 'store/store'

export class UserService {
    constructor(props) {
        this.api = new ProfileApi(store)
        this.actionApi = new ActionApi(store)
    }

    getBySlug(slug) {
        return this.api.getBySlug(slug)
    }

    patch(id, data) {
        return this.api.patch(id, data)
    }

    takeAddress(slug) {
        return this.actionApi.takeAddress({'slug': slug})
    }

    sync() {
        this.api.current().then(
            response => store.commit('profile/profile', response.status === 200 ? response.data : false)
        )

        return true;
    }

    getSocials(id) {
        return this.api.getSocials(id)
    }



    async syncSocials() {
        this.getSocials(store.getters['profile/profile'].id).then(data => {
            store.commit('profile/socials', data.data._embedded['user-socials'])
        })
    }
}

export default new UserService()
