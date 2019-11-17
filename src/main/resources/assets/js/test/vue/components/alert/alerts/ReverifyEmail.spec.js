import {createLocalVue, mount} from '@vue/test-utils'
import Vuetify from 'vuetify'
import ReverifyEmail from 'vue/components/alert/alerts/ReverifyEmail.vue'
import chai from 'chai'
import authService from 'service/AuthService'
import alertService from 'alert/alert-service'
import router from 'router/router'

const localVue = createLocalVue()

describe('ReverifyEmail.vue', () => {
    let vuetify

    beforeEach(() => {
        vuetify = new Vuetify()

        chai.spy.on(authService, 'resendVerification', () => Promise.resolve())
        chai.spy.on(alertService, 'next')
        chai.spy.on(router)
    })

    afterEach(() => {
        chai.spy.restore(authService)
        chai.spy.restore(alertService)
        chai.spy.restore(router)
    })

    it('should resend verification on click', (done) => {
        const wrapper = mount(ReverifyEmail, {localVue, vuetify, router})
        wrapper.vm.$route.query.token = 'test-token'

        wrapper.find('.v-btn').trigger('click')

        expect(authService.resendVerification).to.have.been.called.once
            .with('test-token')

        setTimeout(() => {
            expect(alertService.queue).to.be.empty
            expect(alertService.next).to.have.been.called.once

            done()
        }, 0)
    })
})
