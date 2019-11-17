export default class Chain {
    constructor(interceptors) {
        this.interceptors = []

        if (interceptors !== undefined) {
            this.interceptors = interceptors
        }
    }

    handleFulfilled(config, isRequest) {
        isRequest = isRequest === undefined ? true : !!isRequest

        return this.interceptors.length
            ? this.interceptors[this.interceptors.length - 1].onFulfilled(config, isRequest)
            : config;
    }

    handleRejected(error, isRequest) {
        isRequest = isRequest === undefined ? true : !!isRequest

        return this.interceptors.length
            ? this.interceptors[this.interceptors.length - 1].onRejected(error, isRequest)
            : error;
    }

    addInterceptor(interceptor) {
        if (this.interceptors.length) {
            interceptor.next = this.interceptors[this.interceptors.length - 1]
        }

        this.interceptors.push(interceptor)
    }

    addInterceptors(interceptors) {
        interceptors.forEach(this.addInterceptor, this)
    }
}
