import Builder from 'api/builder'

let testInterceptor = {
    onFulfilled: (config) => config,
    onRejected: (error) => error
}

describe('Builder', () => {
    it('should import correctly', () => {
        expect(Builder).to.not.equal(undefined)
        expect(Builder.new).to.be.a('function')
    })

    it('should create instance correctly', () => {
        let builder = new Builder()

        expect(builder).to.be.a('object')
    })

    describe('#withConfig', () => {
        it('should add config', () => {
            let testConfig = {prop: 'prop'},
                builder = new Builder()

            builder.withConfig(testConfig)

            expect(builder.config).to.be.a('object')
            expect(builder.config).to.deep.equal(testConfig)
        })
    })

    describe('#withInterceptor', () => {
        it('should add interceptor', () => {
            let builder = new Builder({useDefaultInterceptor: false})

            builder.withInterceptor(testInterceptor)

            expect(builder.interceptors.length).to.equal(1)

            expect(builder.interceptors[0]).to.be.a('object')
            expect(builder.interceptors[0].onRejected).to.be.a('function')
            expect(builder.interceptors[0].onFulfilled).to.be.a('function')
        })
    })

    describe('#withInterceptors', () => {
        it('should add interceptors', () => {
            let builder = new Builder({useDefaultInterceptor: false})

            builder.withInterceptors([testInterceptor, testInterceptor])

            expect(builder.interceptors.length).to.equal(2)

            expect(builder.interceptors[0]).to.be.a('object')
            expect(builder.interceptors[1]).to.be.a('object')
            expect(builder.interceptors[0].onRejected).to.be.a('function')
            expect(builder.interceptors[0].onFulfilled).to.be.a('function')
        })
    })

    describe('#new', () => {
        it('should create and return new instance of builder', () => {
            let builder = Builder.new()

            expect(builder).to.be.a('object')
        })

        it('should create instance without default interceptors', () => {
            let builder = Builder.new({useDefaultInterceptor: false})

            expect(builder.interceptors).to.be.a('array')
            expect(builder.interceptors.length).to.equal(0)
        })
    })

    describe('#build', () => {
        it('should build ready state axios instance', () => {
            let instance = Builder.new().withConfig({
                baseURL: '/test',
            }).build()

            expect(instance).to.be.a('function')
            expect(instance.get).to.be.a('function')
            expect(instance.post).to.be.a('function')
            expect(instance.defaults.baseURL).to.equal('/test')
        })
    })
})
