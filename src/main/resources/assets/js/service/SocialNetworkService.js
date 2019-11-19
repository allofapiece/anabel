import SocialApi from 'api/social'
import store from 'store/store'

export class SocialNetworkService {
    constructor(props) {
        this.api = new SocialApi(store)
    }

    sync() {
        this.api.getAll().then(
            response => store.commit('social/socials', response.status === 200 ? response.data._embedded.socials : false)
        )

        return true;
    }
}

export default new SocialNetworkService()
