import AlertInterceptor from 'api/interceptor/interceptors/AlertInterceptor'
import chai from 'chai'
import alertService from 'alert/alert-service'
import Alert from 'alert/alert'

let alertInterceptor = new AlertInterceptor()

const sandbox = chai.spy.sandbox()

describe('AlertInterceptor', () => {
    beforeEach(() => {
        alertService.queue = []
        alertService.started = false
        alertService.current = false
    })

    it('should import correctly', () => {
        expect(alertInterceptor).to.be.an('object')
        expect(alertInterceptor).to.be.instanceOf(AlertInterceptor)

        expect(alertInterceptor).to.have.property('onFulfilled').that.to.be.a('function')
        expect(alertInterceptor).to.have.property('onRejected').that.to.be.a('function')
    })

    describe('#onFulfilled', () => {
        it('should do nothing for request', () => {
            let config = {foo: 'bar'}
            config = alertInterceptor.onFulfilled(config, true)

            expect(config).to.equal(config)
        })

        it('should do push alerts if response', () => {
            sandbox.on(alertService, 'next')

            let config = {
                response: {
                    data: {
                        alerts: [
                            {
                                type: 'SUCCESS',
                                message: 'Test message'
                            }
                        ]
                    }
                }
            }

            alertInterceptor.onFulfilled(config, false)

            expect(alertService.next).to.have.been.called.once
            expect(alertService.queue).to.be.lengthOf(1)
            expect(alertService.queue[0]).to.be.instanceOf(Alert)
            expect(alertService.queue[0]).to.have.property('message')
                .that.to.equal('Test message')
            expect(alertService.queue[0]).to.have.property('type')
                .that.to.equal('SUCCESS')

            sandbox.restore()
        })
    })

    describe('#onReject', () => {
        it('should display general validation errors', () => {
            sandbox.on(alertService, 'next')

            let config = {
                response: {
                    status: 422,
                    data: [
                        {
                            type: 'TYPE',
                            message: 'Case'
                        },
                        {
                            type: 'FIELD',
                            property: 'surname',
                            message: 'Length'
                        },
                        {
                            type: 'FIELD',
                            property: 'name',
                            message: 'Case'
                        }
                    ]
                }
            }
            let resultConfig = alertInterceptor.onRejected(config, false)

            expect(alertService.queue).to.lengthOf(1)
            expect(alertService.queue[0]).to.instanceOf(Alert)
            expect(alertService.queue[0]).to.have.property('message')
                .that.equal('Case')
            expect(alertService.queue[0]).to.have.property('type')
                .that.equal('error')
            expect(alertService.next).to.have.been.called.once
            expect(resultConfig).to.equal(config)

            sandbox.restore()
        })

        it('should skip if request', () => {
            sandbox.on(alertService, 'push')

            let config = {
                foo: 'bar'
            }

            const resultConfig = alertInterceptor.onFulfilled(config, true)

            expect(alertService.push).to.not.have.been.called
            expect(resultConfig).to.equal(config)

            sandbox.restore()
        })
    })
})
