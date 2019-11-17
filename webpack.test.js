const nodeExternals = require('webpack-node-externals')
const merge = require('webpack-merge')
const common = require('./webpack.config')
const path = require('path')

module.exports = merge(common, {
    mode: 'development',
    externals: [nodeExternals()],
    output: {
        devtoolModuleFilenameTemplate: '[absolute-resource-path]',
        devtoolFallbackModuleFilenameTemplate: '[absolute-resource-path]?[hash]'
    },
    devtool: "inline-cheap-module-source-map",
    module: {
        rules: [
            {
                test: /\.s(c|a)ss$/,
                loader: 'null-loader'
            },
            {
                test: /\.css$/,
                loader: 'null-loader'
            },
        ]
    },

    resolve: {
        alias: {
            '$vue': 'vue/dist/vue.esm.js'
        },
    }
})
