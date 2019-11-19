import Api from './api'

export default class SocialApi extends Api {
    constructor(store) {
        super(store, [], ['oauth'])

        this.instance = this.builder.withConfig({
            baseURL: '/api/socials',
        }).build()
    }

    getAll() {
        return this.instance.get();
    }

    get(id) {
        return this.instance.get(id)
    }

    delete(id) {
        return this.instance.delete(id)
    }
}
