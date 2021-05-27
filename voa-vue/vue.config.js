module.exports = {
    // publicPath: './',
    configureWebpack: {
        resolve: {
            alias: {
                'assets': '@/assets',
                'common': '@/common',
                'components': '@/components',
                'network': '@/network',
                'views': '@/views'
            }
        }
    },
    devServer: {
        proxy: {
            '/agent': {//代理api
                target: 'https://voa.yinjiahui.cn',// 代理接口(注意只要域名就够了)
                // ws: true, // proxy websockets
                changeOrigin: true,//是否跨域
                pathRewrite: {//重写路径
                    "^/agent": ''//代理路径
                }
            }
        }
    }
}
