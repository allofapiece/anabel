import * as rules from 'validate/rules'
import axios from 'axios'
import chai from 'chai'

describe('Validation Rules', () => {
    describe('#lower', () => {
        it('should be validated', () => {
            expect(rules.lower.validate('lower', {amount: 5})).to.be.true
            expect(rules.lower.validate('LOWeR', {amount: 1})).to.be.true
            expect(rules.lower.validate('LOWER', {amount: 0})).to.be.true
        })

        it('should reject', () => {
            expect(rules.lower.validate('lower', {amount: 6})).to.be.false
            expect(rules.lower.validate('LOWER', {amount: 1})).to.be.false
        })
    })

    describe('#upper', () => {
        it('should be validated', () => {
            expect(rules.upper.validate('UPPER', {amount: 5})).to.be.true
            expect(rules.upper.validate('uppEr', {amount: 1})).to.be.true
            expect(rules.upper.validate('upper', {amount: 0})).to.be.true
        })

        it('should reject', () => {
            expect(rules.upper.validate('UPPER', {amount: 6})).to.be.false
            expect(rules.upper.validate('upper', {amount: 1})).to.be.false
        })
    })

    describe('#number', () => {
        it('should be validated', () => {
            expect(rules.number.validate('ab1c2d3ef6', {amount: 3})).to.be.true
            expect(rules.number.validate('ab1cd', {amount: 1})).to.be.true
            expect(rules.number.validate('upper', {amount: 0})).to.be.true
        })

        it('should reject', () => {
            expect(rules.number.validate('abcde12345', {amount: 6})).to.be.false
            expect(rules.number.validate('abcd', {amount: 1})).to.be.false
        })
    })

    describe('#remote', () => {
        beforeEach(() => {
        })

        afterEach(() => {
            chai.spy.restore(axios)
        })

        it('should be validated', (done) => {
            chai.spy.on(axios, 'request', () => Promise.resolve({status: 200, data: true}))
//
            rules.remote.validate('some', {url: 'email', authentication: false}).then((result) => {
                expect(result).to.be.true
                expect(axios.request).to.have.been.called.once
                    .with({url: 'email', method: 'post', data: {value: 'some'}})

                done()
            })
        })

        it('should reject if server response 200', (done) => {
            chai.spy.on(axios, 'request', () => Promise.resolve({status: 200, data: false}))

            rules.remote.validate('some', {url: 'email', authentication: false}).then((result) => {
                expect(result).to.be.false
                expect(axios.request).to.have.been.called.once
                    .with({url: 'email', method: 'post', data: {value: 'some'}})

                done()
            })
        })

        it('should reject if server response not 2xx', (done) => {
            chai.spy.on(axios, 'request', () => Promise.reject())

            rules.remote.validate('some', {url: 'email', authentication: false}).then((result) => {
                expect(result).to.be.false
                expect(axios.request).to.have.been.called.once
                    .with({url: 'email', method: 'post', data: {value: 'some'}})

                done()
            })
        })
    })
})
