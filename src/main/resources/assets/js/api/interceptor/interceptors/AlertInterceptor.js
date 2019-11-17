import alertService from 'alert/alert-service'
import Interceptor from './Interceptor'
import validateHelper from 'validate/helper'
import Alert from 'alert/alert'

export default class AlertInterceptor extends Interceptor {
    onFulfilled(config, isRequest) {
        if (!isRequest && config.data) {
            this.retrieveAlerts(config.data)
        }

        return this.nextFulfilled(config, isRequest)
    }

    onRejected(error, isRequest) {
        if (!isRequest && error.response && error.response.status && error.response.data) {
            if (error.response.status === 422) {
                validateHelper.mapTypeErrors(error.response.data)
                    .map(error => new Alert({
                        message: error,
                        time: 20000,
                        type: 'error'
                    }))
                    .forEach(alertService.push, alertService)
            }

            if (error.response.data.alerts) {
                error.response.data.alerts.forEach(alertService.push, alertService)
            }
        }

        return this.nextReject(error, isRequest)
    }

    retrieveAlerts(data) {
        if (data && data.alerts) {
            data.alerts.forEach(alertService.push, alertService)
        }
    }
}
