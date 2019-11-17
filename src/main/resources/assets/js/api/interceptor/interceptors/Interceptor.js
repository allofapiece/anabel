export default class Interceptor {
    constructor() {
        this.next = null
    }

    nextFulfilled(config, isRequest) {
        return this.next ? this.next.onFulfilled(config, isRequest) : config
    }

    nextReject(error, isRequest) {
        return this.next ? this.next.onRejected(error, isRequest) : error
    }

    onFulfilled(config, isRequest) {
        return this.nextFulfilled(config, isRequest)
    }

    onRejected(error) {
        return Promise.reject(error)
    }
}
