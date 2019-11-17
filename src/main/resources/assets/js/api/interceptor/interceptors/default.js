export default {
    onFulfilled: (config) => config,
    onRejected: (error) => Promise.reject(error)
}
