import profileUtil from 'util/profile'

describe('Profile Util', () => {
    describe('#fullName', () => {
        it('should display full name with given profile', () => {
            let profile = {
                firstName: 'Mike',
                lastName: 'Green'
            },
                fullName = profileUtil.fullName(profile)

            expect(fullName).to.be.an('string').that.be.equal('Mike Green')
        })

        it('should display full name with given first and last names', () => {
            let fullName = profileUtil.fullName('Mike', 'Green')

            expect(fullName).to.be.an('string').that.be.equal('Mike Green')
        })

        it('should throw error if first or last name are not presented', () => {
            expect(() => profileUtil.fullName({})).to.throw(Error, 'cannot be empty')
            expect(() => profileUtil.fullName('Mike')).to.throw(Error, 'cannot be empty')
            expect(() => profileUtil.fullName({firstName: 'Mike'})).to.throw(Error, 'cannot be empty')
        })
    })
})
