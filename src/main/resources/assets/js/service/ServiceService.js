import store from 'store/store'
import ServiceApi from "../api/service";

export class ServiceService {
    constructor(props) {
        this.api = new ServiceApi(store)
    }

    get(id) {
        return this.api.get(id)
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


    sync() {
        if (store.getters['service/services'].length === 0) {
            store.subscribe((mutation) => {
                if (mutation.type === 'profile/profile') {
                    console.log(mutation.payload.id)
                    this.api.getByUserId(mutation.payload.id).then(
                        response => store.commit('service/services',
                            response.status === 200 ? response.data.content : false)
                    )
                }
            })
        }

        return true;
    }
}

export default new ServiceService()
