import Api from './api'

export default class ActionApi extends Api {
    constructor(store) {
        super(store)

        this.instance = this.builder.withConfig({
            baseURL: '/api/action',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            }
        }).build()
    }

    signup(data) {
        return this.instance.post('signup', data)
    }

    resendVerification(data) {
        return this.instance.post('reactivate', data)
    }

    takeAddress(data) {
        return this.instance.post('slug', data)
    }
}
