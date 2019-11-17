import empty from 'is-empty'

export default {
    fullName(profile) {
        let firstName, lastName;

        if (typeof profile === 'object') {
            ({firstName, lastName} = profile)
        } else {
            [firstName, lastName] = arguments
        }

        if (empty(firstName) || empty(lastName)) {
            throw new Error('First and last names cannot be empty')
        }

        return firstName + ' ' + lastName
    }
}
