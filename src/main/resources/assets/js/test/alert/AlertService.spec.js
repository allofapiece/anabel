import alertService from 'alert/alert-service'
import Alert from 'alert/alert'
import chai from 'chai'

const sandbox = chai.spy.sandbox()

let resetService = () => {
    alertService.queue = []
    alertService.started = false
    alertService.current = false
}

describe('AlertService', () => {
    it('should import correctly', () => {
        expect(alertService).to.have.property('queue').that.to.be.an('array')
        expect(alertService).to.have.property('state').that.to.be.an('object')
        expect(alertService).to.have.property('current').that.to.be.false
        expect(alertService).to.have.property('started').that.to.be.false
    })

    afterEach(() => {
        resetService()
    })

    describe('#push', () => {
        beforeEach(() => {
            sandbox.on(alertService, 'next')
        })

        afterEach(() => {
            sandbox.restore()
        })

        it('should push alert object', () => {
            alertService.push(new Alert({time: 1000}))

            expect(alertService.next).to.have.been.called.once
            expect(alertService.queue).to.be.lengthOf(1)
            expect(alertService.queue[0]).to.be.an('object')
                .that.to.have.property('time')
                .that.to.equal(1000)
        })

        it('should push simple object', () => {
            alertService.push({time: 1000})

            expect(alertService.next).to.have.been.called.once
            expect(alertService.queue).to.be.lengthOf(1)
            expect(alertService.queue[0]).to.be.an('object')
                .that.to.have.property('time')
                .that.to.equal(1000)
        })

        it('should push simple object when service already started', () => {
            alertService.started = true
            alertService.push({time: 1000})

            expect(alertService.next).to.not.have.been.called.once
        })
    })

    describe('#take', () => {
        it('should take alert from queue', () => {
            let alert = new Alert({time: 1000})
            alertService.queue.push(alert)

            let takenAlert = alertService.take()

            expect(alert).to.equal(takenAlert)
            expect(alertService.queue.length).to.equal(0)
        })
    })

    describe('#next', () => {
        it('should push alert object', (done) => {
            sandbox.on(alertService, 'next')

            alertService.period = 0
            let alert = new Alert({time: 0})

            alertService.queue.push(alert)
            alertService.next()

            setTimeout(() => {
                expect(alertService.current).to.equal(alert)
                expect(alertService.started).to.be.true
                expect(alertService.next).to.be.called.once

                sandbox.restore()

                done()
            }, 0)
        })
    })

    describe('#resetTick', () => {
        it('should reset tick and timeout function', () => {
            alertService.tick = setTimeout(() => true, 100000)
            alertService.resetTick()

            expect(alertService.tick).to.false
        })
    })
})
