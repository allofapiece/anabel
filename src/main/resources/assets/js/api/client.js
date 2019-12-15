import Api from './api'

export default class ClientApi extends Api {
    constructor(store) {
        super(store)

        this.instance = this.builder.withConfig({
            baseURL: '/api/clients',
        }).build()
    }

    post(data) {
        return this.instance.post('', data)
    }

    patch(id, data) {
        return this.instance.patch('' + id, data)
    }

    get(id) {
        return this.instance.get('' + id, {
            params: {
                projection: 'default'
            }
        })
    }

    getByServiceId(id) {
        return this.instance.get('/search/serviceId', {
            params: {
                id: id,
                projection: 'default'
            }
        })
    }
}
