import Api from './api'

const qs = require('querystring')

export default class AuthApi extends Api {
    constructor(store) {
        super(store, [], ['oauth'])

        this.instance = this.builder.withConfig({
            baseURL: '/oauth',
        }).build()
    }

    token(data) {
        return this.instance.post('token', qs.stringify(data), {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': `Basic ${btoa(`${data.client}:${data.secret}`)}`
            }
        })
    }

    revoke(data) {
        return this.instance.post(`revoke/${data.token}`, null, {
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
                'Authorization': 'Bearer ' + data.token
            }
        })
    }
}
