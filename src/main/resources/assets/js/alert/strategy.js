const map = {
    'alert.signup.expired': 'ReverifyEmail'
}

const resolve = (strategy) => strategy in map ? map[strategy] : false

export default resolve
