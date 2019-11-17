export default {
    isValidationError(response) {
        return response.status === 422
    },
    mapTypeErrors(errors) {
        return errors.filter(this.isTypeError).map((error) => error.message)
    },
    mapFieldErrors(errors) {
        let fieldsErrors = {}

        errors.filter(this.isFieldError).forEach((error) => {
            let field = error.property,
                message = error.message

            if (!(field in fieldsErrors)) {
                fieldsErrors[field] = []
            }

            fieldsErrors[field].push(message)
        })

        return fieldsErrors
    },
    isFieldError: (error) => error.type === 'FIELD',
    isTypeError: (error) => error.type === 'TYPE'
}
