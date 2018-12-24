const { VueLoaderPlugin } = require('vue-loader');

module.exports = {
    plugins: [ new VueLoaderPlugin() ],
    entry: {
        index: './src/main/js/index.ts',
        tasks: './src/main/js/tasks.ts'
    },
    output: {
        filename: '[name].js',
        path: __dirname + '/src/main/resources/static/js'
    },
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.ts$/,
                exclude: /node_modules|vue\/src/,
                loader: 'ts-loader',
                options: {
                    appendTsSuffixTo: [/\.vue$/]
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.js$/,
                use: 'babel-loader',
                exclude: /node_modules/
            }
        ]
    },
    resolve: {
        extensions: ['.ts'],
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        }
    }
};