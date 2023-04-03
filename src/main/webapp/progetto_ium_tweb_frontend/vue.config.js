const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: process.env.NODE_ENV === 'production'
    ? '/' + "progetto_ium_tweb_2022_2023_war_exploded" + '/'
    : '/',
  devServer: {
    proxy: {
      '/': {
        target: 'http://134.209.18.52:8080/progetto_ium_tweb_2022_2023_war_exploded',
        pathRewrite: { '^/': '' },
        ws: false
      }
    }
  }
})
