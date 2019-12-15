import Api from './api'

export default class CatalogApi extends Api {
    constructor(store) {
        super(store)

        this.instance = this.builder.withConfig({
            baseURL: '/api/catalogs',
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
}
