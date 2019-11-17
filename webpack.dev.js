const merge = require('webpack-merge')
const common = require('./webpack.config')

module.exports = merge(common, {
    mode: 'development',
    devtool: 'source-map',
    devServer: {
        contentBase: './dist',
        compress: true,
        port: 8000,
        allowedHosts: [
            'localhost:8070',
        ],
        watchOptions: {
            poll: true,
        }
    },
})
