import resolve from './strategy'

export default class Alert {
    constructor(props) {
        this.type = 'success'
        this.time = 10000
        this.icon = false
        this.actions = []
        this.component = false
        this.params = {}

        if (typeof props === 'string') {
            this.message = props
        } else {
            this.message = props.message
            this.type = props.type ? props.type.toLowerCase() : this.type
            this.time = props.time || this.time
            this.icon = props.icon || this.icon
            this.actions = props.actions || this.actions
            this.component = props.strategy ? resolve(props.strategy) : (props.component || this.component)
            this.params = props.params || this.params
        }
    }

    setComponent(component, needResolve) {
        needResolve = needResolve || true

        this.component = needResolve ? resolve(component) : component
    }
}
