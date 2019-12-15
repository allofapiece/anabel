import Api from './api'

export default class ServiceApi extends Api {
    constructor(store) {
        super(store)

        this.instance = this.builder.withConfig({
            baseURL: '/api/services',
        }).build()
    }

    post(data) {
        return this.instance.post('', data)
    }

    get(id) {
        return this.instance.get('' + id, {
            params: {
                projection: 'default'
            }
        })
    }

    getByUserId(id) {
        return this.instance.get('search/userId', {
            params: {
                id: id
            }
        })
    }
}
