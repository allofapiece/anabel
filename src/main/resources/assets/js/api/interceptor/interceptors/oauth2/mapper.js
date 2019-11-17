export default {
    map(error) {
        if (error.error && error.error_description) {
            return {
                type: 'error',
                message: this.interpolate(error.error_description),
                time: 10000
            }
        }
    },
    interpolate(message) {
        if (message === 'User is disabled') {
            return 'You need verify your email.'
        }

        return message
    }
}
