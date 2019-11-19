import UserSocialApi from 'api/user-social'
import store from 'store/store'

export class UserSocialService {
    constructor(props) {
        this.api = new UserSocialApi(store)
    }

    add(link, social, userId) {
        if (social instanceof Object) {
            social = social._links.self.href
        } else {
            social = `/${social}`
        }

        return this.api.post({
            link: link,
            user: userId || `/${store.getters['profile/profile'].id}`,
            socialNetwork: social
        })
    }

    delete(id) {
        this.api.delete(id).then(() => store.commit('profile/deleteSocial', id))
    }
}

export default new UserSocialService()
