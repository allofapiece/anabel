import userService, {UserService} from 'service/UserService'
import ProfileApi from "api/profile"
import chai from 'chai'
import store from 'store/store'

describe('UserService', () => {
    it('should import correctly', () => {
        expect(userService).to.be.instanceOf(UserService)
        expect(userService.api).to.be.instanceOf(ProfileApi)
    })

    describe('#sync', () => {
        it('should commit profile in store', (done) => {
            chai.spy.on(userService.api, 'current', () => Promise.resolve({
                status: 200,
                data: 'data'
            }))

            let result = userService.sync()

            expect(result).to.be.true
            setTimeout(() => {
                expect(store.getters['profile/profile']).to.equal('data')
                done()
            }, 0)

            chai.spy.restore(userService.api)
        })

        it('should commit false in store for profile if api returns 4xx', (done) => {
            chai.spy.on(userService.api, 'current', () => Promise.resolve({
                status: 403
            }))

            let result = userService.sync()

            expect(result).to.be.true

            setTimeout(() => {
                expect(store.getters['profile/profile']).to.false
                done()
            }, 0)

            chai.spy.restore(userService.api)
        })
    })
})
