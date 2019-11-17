import OAuth2Interceptor from 'api/interceptor/interceptors/oauth2/OAuth2Interceptor'
import store from 'store/store'
import axios from 'axios'
import chai from 'chai'
import InterceptorError from "api/interceptor/InterceptorError"

let oauth2 = new OAuth2Interceptor(store)

describe('OAuth2 interceptor', () => {
    it('should import correctly', () => {
        expect(oauth2).to.be.an('object')

        expect(oauth2).to.have.property('onFulfilled').that.to.be.a('function')
        expect(oauth2).to.have.property('onRejected').that.to.be.a('function')
    })

    describe('#onFulfilled', () => {
        it('should do onFulfilled method correctly', () => {
            store.commit('auth/token', {access_token: 'test-token'})

            let config = {headers:{}}
            config = oauth2.onFulfilled(config, true)

            expect(config).to.have.nested.property('headers.Authorization')
                .that.to.equal('Bearer test-token')

            store.commit('auth/token', false)
        })
    })

    describe('#onReject', () => {

        it('should do nothing if request', () => {
            expect(oauth2.onRejected({}, true)).to.be.empty
        })

        it('should do onRejected method correctly', (done) => {
            chai.spy.on(oauth2, 'updateToken', () => Promise.resolve({data: {access_token: 'test-token'}}))
            chai.spy.on(axios, 'request', () => 'success')
            store.commit('auth/token', true)

            let error = {
                config: {
                    headers: {},
                    baseURL: '/base',
                    url: '/base/url'
                },
                response: {
                    status: 401
                }
            }

            oauth2.onRejected(error, false).then(result => {
                expect(result).to.equal('success')
                expect(axios.request).to.have.been.called.once
                expect(axios.request).to.have.been.called.with({
                    headers: {
                        Authorization: 'Bearer test-token'
                    },
                    baseURL: '/base',
                    url: 'url'
                })

                expect(store.getters['auth/token']).to.have.property('access_token')
                    .that.to.equal('test-token')

                chai.spy.restore(axios)
                chai.spy.restore(oauth2)
                done()
            }).catch(errro => {
                console.log(errro)
            })
        })
    })

    describe('#updateToken', () => {
        it('should throw exception if token or token.refresh_token undefined', () => {
            expect(oauth2.updateToken().catch((exception) => {
                expect(exception).to.be.instanceOf(InterceptorError)
            }))

            store.commit('auth/token', {refresh_token: null})

            expect(oauth2.updateToken().catch((exception) => {
                expect(exception).to.be.instanceOf(InterceptorError)
            }))

            store.commit('auth/token', false)
        })

        it('should do onRejected method correctly', (done) => {
            chai.spy.on(axios, 'post', () => Promise.resolve('success'))

            store.commit('auth/token', {
                refresh_token: 'test-refresh-token'
            })

            oauth2.updateToken().then(result => {
                expect(result).to.equal('success')
                expect(axios.post).to.have.been.called.once.with('/oauth/token', {
                    grant_type: 'refresh_token',
                    refresh_token: 'test-refresh-token'
                })

                chai.spy.restore(axios)
                done()
            }).catch(() => {
                chai.spy.restore(axios)
                done()
            })
        })
    })
})
