import {createLocalVue, mount} from '@vue/test-utils'
import Vuex from 'vuex'
import Vue from 'vue'
import Vuetify from 'vuetify'
import Nav from 'vue/components/Nav.vue'
import router from 'router/router'
import {filter, wrap} from '../../internal/util/wrapperUtil'
import chai from 'chai'
import authService from 'service/AuthService'

const localVue = createLocalVue()

describe('Nav.vue', () => {
    let store
    let vuetify

    beforeEach(() => {
        vuetify = new Vuetify()
        Vue.use(Vuex)

        chai.spy.on(authService, 'logout', () => {
            store.commit('profile/profile', false)
        })
    })

    afterEach(() => {
        chai.spy.restore(authService)
    })

    describe('Unauthorized profile', () => {
        beforeEach(() => {
            store = new Vuex.Store({
                modules: {
                    profile: {
                        namespaced: true,
                        state: {
                            profile: false
                        }
                    }
                }
            })
        })

        it('should display signup and signin buttons if not authorized', () => {
            const wrapper = mount(Nav, {localVue, store, vuetify, router})

            let links = wrapper.findAll('a')

            expect(links.length).to.equal(2)
            expect(links.at(0).attributes().href).to.be.equal('/signin')
            expect(links.at(1).attributes().href).to.be.equal('/signup')

            let buttons = wrapper.findAll('.v-btn__content')

            expect(buttons.length).to.equal(2)
            expect(buttons.at(0).text()).to.be.string('Sign In')
            expect(buttons.at(1).text()).to.be.string('Sign Up')
        })
    })

    describe('Authorized profile', () => {
        beforeEach(() => {
            store = new Vuex.Store({
                modules: {
                    profile: {
                        namespaced: true,
                        state: {
                            profile: {
                                firstName: 'Mike',
                                lastName: 'Green',
                                slug: 'mike'
                            }
                        },
                        mutations: {
                            profile(state, profile) {
                                state.profile = profile
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
        })

        it('should display profile and logout button', () => {
            const wrapper = mount(Nav, {localVue, store, vuetify, router})

            const i = wrapper.findAll('i')
            const links = wrapper.findAll('a')

            expect(links.filter(
                (elem) => filter.classes(elem, 'i', 'mdi-account-circle')
            ).length).to.be.equal(1)
            expect(wrap.do(wrapper).selector('.v-btn__content').string('Logout').one()).to.be.true
            expect(wrap.do(links).selector('.v-btn__content').string('Logout').one()).to.be.true

            expect(i.length).to.be.equal(1)
            expect(i.at(0).classes('mdi-account-circle')).to.be.true

            const logoutButton = wrapper.findAll('button').filter((btn) => btn.text().includes('Logout'))
            expect(logoutButton).to.be.lengthOf(1)
            logoutButton.trigger('click')

            expect(authService.logout).to.have.been.called.once

            let text = wrapper.text();
            expect(text.includes('Sign In'))
            expect(text.includes('Sign Up'))
        })
    })
})
