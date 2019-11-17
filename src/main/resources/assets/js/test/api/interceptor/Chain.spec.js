import Interceptor from "api/interceptor/interceptors/Interceptor"
import Chain from "api/interceptor/chain"

class InterceptorA extends Interceptor {
    onFulfilled(config, isRequest) {
        if (isRequest) {
            config.request = 'a'
            config.requestA = 'a'
        } else {
            config.response = 'a'
            config.responseA = 'a'
        }

        return this.nextFulfilled(config, isRequest)
    }

    onRejected(error, isRequest) {
        if (isRequest) {
            error.request = 'a'
            error.requestA = 'a'
        } else {
            error.response = 'a'
            error.responseA = 'a'
        }

        return this.nextReject(error, isRequest)
    }
}

class InterceptorB extends Interceptor {
    onFulfilled(config, isRequest) {
        if (isRequest) {
            config.request = 'b'
            config.requestB = 'b'
        } else {
            config.response = 'b'
            config.responseB = 'b'
        }

        return this.nextFulfilled(config, isRequest)
    }

    onRejected(error, isRequest) {
        if (isRequest) {
            error.request = 'b'
            error.requestB = 'b'
        } else {
            error.response = 'b'
            error.responseB = 'b'
        }

        return this.nextReject(error, isRequest)
    }
}

describe('Chain', () => {
    it('should import correctly', () => {
        let chain = new Chain()

        expect(chain).to.be.an('object').that.to.instanceOf(Chain)
        expect(chain).to.have.property('handleFulfilled').that.to.be.a('function')
        expect(chain).to.have.property('handleRejected').that.to.be.a('function')
        expect(chain).to.have.property('interceptors').that.to.be.an('array').and.to.be.empty
    })

    describe('#addInterceptor', () => {
        it('should add interceptor', () => {
            let chain = new Chain()

            chain.addInterceptor(new InterceptorA())

            expect(chain.interceptors).to.be.lengthOf(1)
            expect(chain.interceptors[0]).to.be.instanceOf(InterceptorA)
        })
    })

    describe('#addInterceptors', () => {
        it('should add interceptors', () => {
            let chain = new Chain(),
                interceptorA = new InterceptorA(),
                interceptorB = new InterceptorB()

            chain.addInterceptors([interceptorA, interceptorB])

            expect(chain.interceptors).to.be.lengthOf(2)
            expect(chain.interceptors[0]).to.be.instanceOf(InterceptorA)
            expect(chain.interceptors[1]).to.be.instanceOf(InterceptorB)

            expect(chain.interceptors[0].next).to.be.null
            expect(chain.interceptors[1].next).to.be.instanceOf(InterceptorA)
                .and.to.be.equal(interceptorA)
        })
    })

    describe('#handleFulfilled', () => {
        it('should handle fulfilled for request. request variable should be overrode', () => {
            let chain = new Chain(),
                interceptorA = new InterceptorA(),
                interceptorB = new InterceptorB()

            chain.addInterceptors([interceptorA, interceptorB])

            let result = chain.handleFulfilled({}, true)

            expect(result).to.have.property('request').that.equal('a')
            expect(result).to.have.property('requestA').that.equal('a')
            expect(result).to.have.property('requestB').that.equal('b')
        })

        it('should handle fulfilled for response. response variable should be overrode', () => {
            let chain = new Chain(),
                interceptorA = new InterceptorA(),
                interceptorB = new InterceptorB()

            chain.addInterceptors([interceptorA, interceptorB])

            let result = chain.handleFulfilled({}, false)

            expect(result).to.have.property('response').that.equal('a')
            expect(result).to.have.property('responseA').that.equal('a')
            expect(result).to.have.property('responseB').that.equal('b')
        })
    })

    describe('#handleRejected', () => {
        it('should handle error for request. request variable should be overrode', () => {
            let chain = new Chain(),
                interceptorA = new InterceptorA(),
                interceptorB = new InterceptorB()

            chain.addInterceptors([interceptorA, interceptorB])

            let result = chain.handleRejected({}, true)

            expect(result).to.have.property('request').that.equal('a')
            expect(result).to.have.property('requestA').that.equal('a')
            expect(result).to.have.property('requestB').that.equal('b')
        })

        it('should handle error for response. response variable should be overrode', () => {
            let chain = new Chain(),
                interceptorA = new InterceptorA(),
                interceptorB = new InterceptorB()

            chain.addInterceptors([interceptorA, interceptorB])

            let result = chain.handleRejected({}, false)

            expect(result).to.have.property('response').that.equal('a')
            expect(result).to.have.property('responseA').that.equal('a')
            expect(result).to.have.property('responseB').that.equal('b')
        })
    })
})
