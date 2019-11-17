import helper from 'validate/helper'

describe('Validate Helper', () => {
    describe('#isValidationError', () => {
        it('should detect that response is validation error', () => {
            expect(helper.isValidationError({
                status: 422
            })).to.be.true
            expect(helper.isValidationError({
                status: 200
            })).to.be.false
        })
    })

    describe('#isFieldError', () => {
        it('should detect that error is field error', () => {
            expect(helper.isFieldError({
                type: 'FIELD'
            })).to.be.true
            expect(helper.isFieldError({
                type: 'TYPE'
            })).to.be.false
        })
    })

    describe('#isTypeError', () => {
        it('should detect that error is field error', () => {
            expect(helper.isTypeError({
                type: 'TYPE'
            })).to.be.true
            expect(helper.isTypeError({
                type: 'FIELD'
            })).to.be.false
        })
    })

    describe('#mapErrors', () => {
        it('should map field errors', () => {
            let errors = [
                {
                    type: 'FIELD',
                    property: 'name',
                    message: 'Length'
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
                },
                {
                    type: 'TYPE',
                    message: 'Length'
                }
            ]

            let mappedErrors = helper.mapFieldErrors(errors)

            expect(mappedErrors).to.be.an('object')
            expect(mappedErrors).to.have.property('name')
            expect(mappedErrors).to.have.property('surname')
            expect(mappedErrors.name).to.lengthOf(2)
            expect(mappedErrors.name).to.include.members(['Length', 'Case'])
            expect(mappedErrors.surname).to.lengthOf(1)
            expect(mappedErrors.name).to.include.members(['Length'])
        })

        it('should map type errors', () => {
            let errors = [
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
                },
                {
                    type: 'TYPE',
                    message: 'Length'
                }
            ]

            let mappedErrors = helper.mapTypeErrors(errors)

            expect(mappedErrors).to.be.an('array')
            expect(mappedErrors).to.lengthOf(2)
            expect(mappedErrors[0]).to.equal('Case')
            expect(mappedErrors[1]).to.equal('Length')
        })
    })
})
