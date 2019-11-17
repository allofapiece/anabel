import mapper from 'api/interceptor/interceptors/oauth2/mapper'

describe('OAuth2 mapper', () => {
    describe('#map', () => {
        it('should map error to alert', () => {
            const error = {
                error: 'invalid_grant',
                error_description: 'User is disabled'
            }

            expect(mapper.map(error)).to.include({
                type: 'error',
                message: 'You need verify your email.'
            })
        })
    })

    describe('#interpolate', () => {
        it('should interpolate error message', () => {
            expect(mapper.interpolate('User is disabled')).to.be.equal('You need verify your email.')
        })
    })
})
