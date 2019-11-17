import Chain from './interceptor/chain'
import axios from 'axios'
import merge from 'deepmerge'

export default class Builder {
    constructor(props) {
        this.interceptors = []
        this.config = {}
    }

    static new(props) {
        return new Builder(props)
    }

    withConfig(config) {
        this.config = merge(this.config, config)

        return this
    }

    withInterceptor(interceptor) {
        this.interceptors.push(interceptor)

        return this
    }

    withInterceptors(interceptors) {
        interceptors.forEach(this.withInterceptor, this)

        return this
    }

    build() {
        let chain = new Chain()

        chain.addInterceptors(this.interceptors)

        let instance = axios.create(this.config)

        instance.interceptors.request.use(
            config => chain.handleFulfilled(config, true),
            error => chain.handleRejected(error, true)
        )

        instance.interceptors.response.use(
            config => chain.handleFulfilled(config, false),
            error => chain.handleRejected(error, false)
        )

        return instance
    }
}
