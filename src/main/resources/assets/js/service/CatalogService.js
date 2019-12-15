import store from 'store/store'
import CatalogApi from "../api/catalog";

export class CatalogService {
    constructor(props) {
        this.api = new CatalogApi(store)
    }

    add(data) {
        let profile = store.getters['profile/profile'],
            id

        if (profile) {
            id = profile.id
        }

        return this.api.post({
            ...data,
            user: `/${id}`
        })
    }
}

export default new CatalogService()
