import { createLocalVue, mount } from '@vue/test-utils'
import Vuetify from 'vuetify'
import AlertComponent from 'vue/components/alert/Alert.vue'
import Alert from 'alert/alert'
import chai from 'chai'
import alertService from 'alert/alert-service'
import router from 'router/router'

const localVue = createLocalVue()

describe('Alert.vue', () => {
    let vuetify

    beforeEach(() => {
        vuetify = new Vuetify()

        chai.spy.on(router)
    })

    afterEach(() => {
        chai.spy.restore(router)
    })

    it('should display alert component', () => {
        alertService.state.alert = new Alert({
            component: 'ReverifyEmail'
        })

        const wrapper = mount(AlertComponent, {localVue, vuetify})
        expect(wrapper.text()).to.include('Your mail has been expired')

        alertService.state.alert = false
    })

    it('should resolve component', () => {
        alertService.state.alert = new Alert({
            strategy: 'alert.signup.expired'
        })

        const wrapper = mount(AlertComponent, {localVue, vuetify})
        expect(wrapper.text()).to.include('Your mail has been expired')

        alertService.state.alert = false
    })
})
