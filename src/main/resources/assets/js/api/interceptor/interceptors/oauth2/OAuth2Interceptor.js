import axios from 'axios'
import Interceptor from '../Interceptor'
import InterceptorError from '../../InterceptorError'
import mapper from './mapper'

const qs = require('querystring')

export default class OAuth2Interceptor extends Interceptor {
    constructor(store) {
        super()
        this.store = store
        this.inProgress = false
    }

    onFulfilled(config, isRequest) {
        if (isRequest) {
            const token = this.store.getters['auth/token']

            if (token) {
                config.headers.Authorization = `Bearer ${token.access_token}`;
            }
        }

        return this.nextFulfilled(config, isRequest);
    }

    onRejected(error, isRequest) {
        if (!isRequest && error.config && error.response) {
            if (error.response.status === 401 && this.store.getters['auth/token']) {
                const fun = () => this.updateToken()
                    .then((response) => this.resendRequest(error.config, response.data))
                    .catch(() => this.nextReject(error, isRequest))

                //TODO think about another architectural solution
                if (this.store.getters['auth/inProgress']) {
                    return this.delay(1000, 0)
                        .then(() => this.resendRequest(error.config, this.store.getters['auth/token']))
                        .catch(() => fun())
                }

                return fun()
            } else if (error.response.status === 400 && error.response.data.error) {
                if (!error.response.data.alerts) {
                    error.response.data.alerts = []
                }

                error.response.data.alerts.push(mapper.map(error.response.data))
            }
        }

        return this.nextReject(error, isRequest)
    }

    updateToken() {
        this.store.commit('auth/inProgress', true)

        this.store.subscribe((mutation, state) => {
            if (mutation.type === 'auth/token') {
                console.log("fff")
                this.store.commit('auth/inProgress', false)
            }
        })

        let token = this.store.getters['auth/token']

        if (!token || !token.refresh_token) {
            return Promise.reject(new InterceptorError(this))
        }

        const client = this.store.getters['auth/client']

        return axios.post('/oauth/token', qs.stringify({
            grant_type: 'refresh_token',
            refresh_token: token.refresh_token
        }), {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': `Basic ${btoa(`${client.client}:${client.secret}`)}`
            }
        })
    }

    resendRequest(config, token) {
        config.headers.Authorization = `Bearer ${token.access_token}`
        config.url = this.correctUrl(config.baseURL, config.url)

        this.store.commit('auth/token', token)

        return axios.request(config)
    }

    correctUrl(baseUrl, url) {
        return url.substring(url.indexOf(baseUrl) + baseUrl.length + 1)
    }

    delay(ms, attempt) {
        return new Promise(function (resolve, reject) {
            setTimeout(resolve, ms);
        }).then(() => {
            if (this.store.getters['auth/inProgress']) {
                if (attempt < 5) {
                    return this.delay(ms, ++attempt)
                } else {
                    return Promise.reject()
                }
            }

            return Promise.resolve()
        })
    }
}
