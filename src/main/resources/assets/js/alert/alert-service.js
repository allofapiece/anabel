import Alert from './alert'
import Vue from 'vue'

class AlertService {
    constructor() {
        this.queue = []
        this.period = 0
        this.current = false
        this.started = false
        this.tick = false

        this.state = Vue.observable({alert: this.current})

        if ((typeof alertMessage !== 'undefined') && (typeof alertType !== 'undefined')) {
            let alert = new Alert({
                message: alertMessage,
                type: alertType.toLowerCase(),
                time: 20000
            })

            if (typeof alertStrategy !== 'undefined') {
                alert.setComponent(alertStrategy, true)
            }

            if (typeof alertActionText !== 'undefined' && typeof alertActionLink !== 'undefined') {
                let action = {
                    text: alertActionText,
                    link: alertActionLink
                }

                if (typeof alertActionType !== undefined) {
                    action.type = alertActionType.toLowerCase()
                }

                alert.actions.push(action)
            }

            this.push(alert)
        }
    }

    push(alert) {
        if ((alert instanceof Alert) === false) {
            alert = new Alert(alert)
        }

        this.queue.push(alert)

        if (!this.started) {
            this.next()
        }
    }

    take() {
        return this.queue.shift()
    }

    next() {
        let that = this

        this.started = true
        this.setCurrent(false)

        setTimeout(() => {
            let alert = that.take()

            if (alert === undefined) {
                that.started = false
            } else {
                that.setCurrent(alert)
                that.tick = setTimeout(() => that.next(), alert.time)
            }
        }, this.period)
    }

    resetTick() {
        clearTimeout(this.tick)
        this.tick = false
    }

    setCurrent(current) {
        this.current = current
        this.state.alert = this.current

        if (this.tick) {
            this.resetTick()
        }
    }

    getCurrent() {
        return this.current
    }

    setState(state) {
        this.state = state
    }
}

export default new AlertService()
