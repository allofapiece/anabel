import Builder from './builder'
import OAuth2Interceptor from './interceptor/interceptors/oauth2/OAuth2Interceptor'
import Interceptor from './interceptor/interceptors/Interceptor'
import AlertInterceptor from './interceptor/interceptors/AlertInterceptor'
import empty from 'is-empty'

const interceptors = {
    default: Interceptor,
    alert: AlertInterceptor,
    oauth: OAuth2Interceptor
}

export default class Api {
    constructor(store, include, exclude) {
        this.store = store
        this.builder = Builder.new()

        if (empty(include)) {
            console.log(Object.keys(interceptors))
            include = Object.keys(interceptors)
        }

        if (!empty(exclude)) {
            exclude.forEach(ex => include.splice(include.indexOf(ex), 1))
        }

        include.forEach(incl => this.builder.withInterceptor(new interceptors[incl](this.store)), this)

        //TODO made status 4xx as valid statuses to avoid console errors

        this.builder.withConfig({
            headers: {
                'Content-Type': 'application/json; charset=UTF-8',
            },
        })

        this.instance = this.builder.build()
    }
}
