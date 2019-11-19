import Api from './api'

export default class SocialApi extends Api {
    constructor(store) {
        super(store)

        this.instance = this.builder.withConfig({
            baseURL: '/api/user-socials',
        }).build()
    }

    post(data) {
        return this.instance.post('', data)
    }

    delete(id) {
        return this.instance.delete('/' + id)
    }
}
