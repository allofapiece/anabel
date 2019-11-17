import Builder from './builder'
import OAuth2Interceptor from './interceptor/interceptors/oauth2/OAuth2Interceptor'
import Interceptor from './interceptor/interceptors/Interceptor'
import AlertInterceptor from './interceptor/interceptors/AlertInterceptor'

export default class Api {
    constructor(store) {
        this.store = store
        this.builder = Builder.new()
            .withInterceptor(new Interceptor())
            .withInterceptor(new AlertInterceptor())
            .withInterceptor(new OAuth2Interceptor(this.store))

            //TODO made status 4xx as valid statuses to avoid console errors
            .withConfig({
                headers: {
                    'Content-Type': 'application/json charset=UTF-8',
                },
            })

        this.instance = this.builder.build()
    }
}
