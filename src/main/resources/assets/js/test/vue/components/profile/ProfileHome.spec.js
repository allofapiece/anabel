import {createLocalVue, mount} from '@vue/test-utils'
import Vuex from 'vuex'
import Vuetify from 'vuetify'
import ProfileHome from 'vue/components/profile/ProfileHome.vue'
import router from 'router/router'
import chai from 'chai'
import userService from 'service/UserService'

const localVue = createLocalVue()

describe('ProfileHome.vue', () => {
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
        localVue.use(Vuex)
    })

    describe('Current profile', () => {
        it('should display full name and links for current profile', () => {
            ProfileHome.methods.getCurrentSlug = () => 'name1-lastname1'

            const wrapper = mount(ProfileHome, {localVue, store, vuetify, router})
            const text = wrapper.text()

            expect(text).to.be.include('Name1 Lastname1')
            expect(text).to.be.include('Edit profile')
            expect(text).to.be.include('Settings')
        })
    })

    describe('Another profile', () => {
        it('should display full name for another profile', (done) => {
            chai.spy.on(userService, 'getBySlug', () => Promise.resolve({
                data: {
                    id: 2,
                    firstName: 'Name2',
                    lastName: 'Lastname2',
                    slug: 'name2-lastname2'
                }
            }))

            ProfileHome.methods.getCurrentSlug = () => 'name2-lastname2'

            const wrapper = mount(ProfileHome, {localVue, store, vuetify, router})

            setTimeout(() => {
                const text = wrapper.text()

                expect(text).to.not.be.include('Edit profile')
                expect(text).to.not.be.include('Settings')

                chai.spy.restore(userService)
                done()
            }, 0)
        })
    })
})
