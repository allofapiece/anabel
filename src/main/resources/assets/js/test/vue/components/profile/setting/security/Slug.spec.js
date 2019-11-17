import {createLocalVue, mount} from '@vue/test-utils'
import Vuex from 'vuex'
import Vue from 'vue'
import Vuetify from 'vuetify'
import Slug from 'vue/components/profile/setting/security/Slug.vue'
import userService from 'service/UserService'
import chai from 'chai'

const localVue = createLocalVue()

describe('Slug.vue', () => {
    let vuetify
    let store = new Vuex.Store({
        modules: {
            profile: {
                namespaced: true,
                state: {
                    profile: {
                        id: 1,
                        firstName: 'Name1',
                        lastName: 'Lastname1',
                        slug: 'name1-lastname1'
                    }
                },
                getters: {
                    profile(state) {
                        return state.profile
                    }
                }
            }
        }
    })

    beforeEach(() => {
        vuetify = new Vuetify()
        Vue.use(Vuex)
    })

    describe('Mounted', () => {
        it('display slug in text field', () => {
            chai.spy.on(userService, 'takeAddress', () => Promise.resolve({
                status: 200
            }))

            const wrapper = mount(Slug, {localVue, store, vuetify})

            expect(wrapper.vm.$data.slug).to.equal('name1-lastname1')

            wrapper.find('form').trigger('submit')

            expect(userService.takeAddress).to.have.been.called.once
                .with('name1-lastname1')

            chai.spy.restore(userService)
        })
    })
})
