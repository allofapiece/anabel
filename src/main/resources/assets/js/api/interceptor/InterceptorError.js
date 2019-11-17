export default class InterceptorError extends Error {
    constructor(interceptor, ...params) {
        super(...params)
        this.interceptor = interceptor
    }
}
