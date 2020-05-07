const path = require("path")

const CaseSensitivePathsPlugin = require("case-sensitive-paths-webpack-plugin")
const HtmlWebpackPlugin = require("html-webpack-plugin")
module.exports = {
    entry: "./src/index.tsx",
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: 'my-first-webpack.bundle.js'
    },
    resolve: {
        alias: {
            "@src": path.resolve("./src"),
            "@components": path.resolve("./src/components"),
            "@utils": path.resolve("./src/utils")
        },
        extensions: [".js", ".jsx", ".ts", ".tsx"]
    },

    module: {
        rules: [
            {
                test: /\.(jsx?|tsx?)$/,
                loader: "babel-loader",
                exclude: { test: /node_modules/ }
            },
            {
                test: /\.(scss|css)$/,
                use: ["style-loader", "css-loader", "sass-loader"]
            },
            {
                test: /\.(bmp|gif|jpe?g|png)$/,
                loader: "file-loader",
                options: { name: "images/[name].[hash:6].[ext]" }
            },
            {
                test: /\.(eot|ttf|otf|woff2?)$/,
                loader: "file-loader",
                options: { name: "fonts/font.[hash:6].[ext]" }
            }
        ]
    },
    devServer: {
        host: "0.0.0.0",
        port: 443,
        disableHostCheck: true,
        historyApiFallback: true,
        https: true,
    },
    plugins: [
        new CaseSensitivePathsPlugin(),
        new HtmlWebpackPlugin({
            template: "./src/index.html",
        })
    ]
};