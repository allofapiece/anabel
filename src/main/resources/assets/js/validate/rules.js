import axios from 'axios'
import store from 'store/store'

export const lower = {
    validate: (value, {amount}) => (value.match(/[a-z]/g) || []).length >= amount,
    message: '{amount} lower characters required.',
    params: ['amount']
}

export const upper = {
    validate: (value, {amount}) => (value.match(/[A-Z]/g) || []).length >= amount,
    message: '{amount} upper characters required.',
    params: ['amount']
}

export const number = {
    validate: (value, {amount}) => (value.match(/[0-9]/g) || []).length >= amount,
    message: '{amount} numbers required.',
    params: ['amount']
}

export const remote = {
    lazy: true,
    validate: (value, {url, authentication, method, }) => {
        authentication = authentication || true

        let headers = {}

        if (authentication) {
            const token = store.getters['auth/token']

            if (!token) {
                return Promise.reject()
            }

            headers.Authorization = `Bearer ${token.access_token}`
        }

        return axios.request({
            method: method || 'post',
            url: url,
            data: {
                value: value
            },
            headers: headers
        }).then((response) => {
            return response.status === 200 && response.data === true
        }).catch(() => false)
    },
    message: 'This {_field_} already taken.',
    params: ['url', 'authentication', 'method']
}
