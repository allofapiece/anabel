import {createLocalVue, mount, shallowMount} from '@vue/test-utils'
import Vuex from 'vuex'
import Vue from 'vue'
import Vuetify from 'vuetify'
import SignUp from 'vue/components/auth/SignUp.vue'
import router from 'router/router'
import 'validate/vee-validate'
import chai from 'chai'
import authService from 'service/AuthService'

const localVue = createLocalVue()

const sandbox = chai.spy.sandbox()

describe('SignUp.vue', () => {
    let store
    let vuetify

    beforeEach(() => {
        vuetify = new Vuetify()
        Vue.use(Vuex)
        sandbox.on(router, 'push', () => Promise.resolve(true))
    })

    afterEach(() => {
        sandbox.restore()
    })

    it('should be empty after initializing', () => {
        const wrapper = shallowMount(SignUp, {localVue, store, vuetify, router, sync: false})

        expect(wrapper.vm.$data.invalid).to.be.true
        expect(wrapper.vm.$data.email).to.be.empty
        expect(wrapper.vm.$data.firstName).to.be.empty
        expect(wrapper.vm.$data.lastName).to.be.empty
        expect(wrapper.vm.$data.password).to.be.empty
        expect(wrapper.vm.$data.confirmedPassword).to.be.empty
    })

    it('should call signup method', (done) => {
        chai.spy.on(authService, 'signup', () => Promise.resolve(true))

        const wrapper = mount(SignUp, {localVue, store, vuetify, router, sync: false})

        wrapper.vm.$data.invalid = false
        wrapper.vm.$data.email = 'mike@gmail.com'
        wrapper.vm.$data.firstName = 'John'
        wrapper.vm.$data.lastName = 'Fedor'
        wrapper.vm.$data.password = 'Qqqq1111'
        wrapper.vm.$data.confirmedPassword = 'Qqqq1111'

        wrapper.find('form').trigger('submit')

        setTimeout(() => {
            expect(authService.signup).to.have.been.called.once
                .with({
                    email: 'mike@gmail.com',
                    firstName: 'John',
                    lastName: 'Fedor',
                    password: 'Qqqq1111',
                    confirmedPassword: 'Qqqq1111'
                })
            expect(router.push).to.have.been.called.once.with('/signin')

            chai.spy.restore(authService)

            done()
        }, 0)
    })

    it('should display server errors', (done) => {
        chai.spy.on(authService, 'signup', (some) => Promise.reject({
            response: {
                status: 422,
                data: [{
                    property: 'firstName',
                    message: 'First Name Error',
                    type: 'FIELD'
                }]
            }
        }))

        const wrapper = mount(SignUp, {localVue, store, vuetify, router})

        wrapper.find('form').trigger('submit')

        setTimeout(() => {
            console.log(wrapper.text())
            expect(router.push).to.not.have.been.called
            expect(wrapper.text().includes('First Name Error')).to.be.true

            chai.spy.restore(authService)

            done()
        }, 0)
    })
})
