import Api from './api'

export default class ProfileApi extends Api {
    constructor(store) {
        super(store)

        this.instance = this.builder.withConfig({
            baseURL: '/api/users',
        }).build()
    }

    patch(id, data) {
        return this.instance.patch('' + id, data)
    }

    get(id) {
        return this.instance.get(id)
    }

    getSocials(id) {
        return this.instance.get(`${id}/socials`, {
            params: {
                projection: 'withSocialNetwork'
            }
        })
    }

    getBySlug(slug) {
        return this.instance.get(slug)
    }

    current() {
        return this.instance.get('current')
    }
}
