import store from 'store/store'
import ClientApi from "../api/client";

export class ClientService {
    constructor(props) {
        this.api = new ClientApi(store)
    }

    approve(id) {
        return this.api.patch(id, {
            status: 'ACTIVE'
        })
    }

    getByServiceId(id) {
        return this.api.getByServiceId(id)
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

export default new ClientService()
