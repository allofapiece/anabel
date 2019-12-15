import Api from './api'
import store from "../store/store";

export default class ServiceApi extends Api {
    constructor(store) {
        super(store)

        this.instance = this.builder.withConfig({
            baseURL: '/api/products',
        }).build()
    }

    post(data) {
        let profile = store.getters['profile/profile'],
            id

        if (profile) {
            id = profile.id
        }

        return this.instance.post('', {
            ...data,
            user: `/${id}`
        })
    }

    get(id) {
        return this.instance.get('' + id, {
            params: {
                projection: 'default'
            }
        })
    }
}
