const Dotenv = require('dotenv-webpack')
const path = require('path')
const VueLoaderPlugin = require('vue-loader/lib/plugin')

module.exports = {
    entry: [path.join(__dirname, 'src', 'main', 'resources', 'assets', 'js', 'main.js')],
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env'],
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                ]
            },
            {
                test: /\.s(c|a)ss$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    {
                        loader: 'sass-loader',
                        options: {
                            implementation: require('sass'),
                            sassOptions: {
                                fiber: require('fibers'),
                                indentedSyntax: true,
                                outputStyle: 'compressed',
                            },
                            prependData: `@import "${path.join(__dirname, 'src', 'main', 'resources', 'assets', 'scss', '_variables.sass')}"`,
                        }
                    }
                ]
            },
            {
                test: /.(png|jpe?g|gif|svg|woff|woff2|otf|ttf|eot|ico)$/,
                loader: 'url-loader',
                options: {
                    name: '[name]-[hash:8].[ext]',
                    publicPath: '../',
                }

            }
        ]
    },
    plugins: [
        new VueLoaderPlugin(),
        new Dotenv({
            safe: true
        }),
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'assets', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}
