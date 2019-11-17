import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import Vuetify from 'vuetify'
import SignIn from 'vue/components/auth/SignIn.vue'
import router from 'router/router'
import 'validate/vee-validate'
import chai from 'chai'
import authService from 'service/AuthService'

const localVue = createLocalVue()

const sandbox = chai.spy.sandbox()

describe('SignIn.vue', () => {
    let vuetify

    beforeEach(() => {
        vuetify = new Vuetify()
        sandbox.on(router, 'push', () => Promise.resolve(true))
    })

    afterEach(() => {
        sandbox.restore()
    })

    it('should be empty after initializing', () => {
        const wrapper = shallowMount(SignIn, {localVue, vuetify, router, sync: false})

        expect(wrapper.vm.$data.invalid).to.be.true
        expect(wrapper.vm.$data.email).to.be.empty
        expect(wrapper.vm.$data.password).to.be.empty
    })

    it('should call login method', (done) => {
        chai.spy.on(authService, 'login', () => Promise.resolve(true))

        const wrapper = mount(SignIn, {localVue, vuetify, router})

        wrapper.vm.$data.invalid = false
        wrapper.vm.$data.email = 'mike@gmail.com'
        wrapper.vm.$data.password = 'Qqqq1111'

        wrapper.find('form').trigger('submit')

        setTimeout(() => {
            expect(authService.login).to.have.been.called.once
            expect(router.push).to.have.been.called.once.with('/feed')

            chai.spy.restore(authService)

            done()
        }, 0)

    })
})
