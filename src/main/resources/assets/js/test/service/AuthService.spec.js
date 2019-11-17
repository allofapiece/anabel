import authService, {AuthService} from 'service/AuthService'
import userService from 'service/UserService'
import AuthApi from 'api/auth'
import ActionApi from 'api/action'
import chai from 'chai'
import store from 'store/store'
import router from 'router/router'

describe('AuthService', () => {
    it('should import correctly', () => {
        expect(authService).to.be.instanceOf(AuthService)
        expect(authService.authApi).to.be.instanceOf(AuthApi)
        expect(authService.actionApi).to.be.instanceOf(ActionApi)
    })

    describe('#login', () => {
        it('should retrieve token and sync profile', (done) => {
            let sandbox = chai.spy.sandbox()
            sandbox.on(authService.authApi, 'token', () => Promise.resolve({
                status: 200,
                data: {
                    access_token: 'test-token'
                }
            }))
            sandbox.on(userService, 'sync', () => true)
            store.commit('auth/client', {client: 'test-client'})

            authService.login({'username': 'test'})

            setTimeout(() => {
                expect(authService.authApi.token).to.have.been.called.once
                    .with({'username': 'test', client: 'test-client'})
                expect(userService.sync).to.have.been.called.once

                let token = store.getters['auth/token']

                expect(token).to.be.an('object')
                    .that.to.have.property('access_token')
                    .that.to.equal('test-token')

                expect(store.getters['auth/authenticated']).to.be.true

                store.commit('auth/token', false)
                store.commit('auth/authenticated', false)
                store.commit('auth/client', false)

                sandbox.restore()
                done()
            }, 0)
        })

        it('should return false if status not equal 200', (done) => {
            let sandbox = chai.spy.sandbox()
            sandbox.on(authService.authApi, 'token', () => Promise.resolve({
                status: 401,
            }))
            sandbox.on(userService, 'sync')

            store.commit('auth/client', {client: 'test-client'})

            authService.login({'username': 'test'})

            setTimeout(() => {
                expect(authService.authApi.token).to.have.been.called.once
                    .with({'username': 'test', client: 'test-client'})
                expect(userService.sync).to.have.not.been.called

                let token = store.getters['auth/token'],
                    authenticated = store.getters['auth/authenticated']

                expect(token).to.be.false
                expect(authenticated).to.be.false

                store.commit('auth/token', false)
                store.commit('auth/authenticated', false)
                store.commit('auth/client', false)

                sandbox.restore()
                done()
            }, 0)
        })
    })

    describe('#signup', () => {
        it('should signup', (done) => {
            let sandbox = chai.spy.sandbox()
            sandbox.on(authService.actionApi, 'signup', () => Promise.resolve(true))

            authService.signup({'username': 'test'}).then((result) => {
                expect(result).to.be.true
                expect(authService.actionApi.signup).to.have.been.called.once
                    .with({'username': 'test'})

                sandbox.restore()
                done()
            })
        })
    })

    describe('#logout', () => {
        it('should signup', () => {
            let sandbox = chai.spy.sandbox()
            sandbox.on(authService.authApi, 'revoke', () => Promise.resolve(true))
            sandbox.on(router, 'push', () => Promise.resolve(true))

            store.commit('auth/token', {access_token: 'test-token'})

            authService.logout({'username': 'test'})

            store.commit('auth/token', false)
            sandbox.restore()
        })
    })
})
