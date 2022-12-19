const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: process.env.NODE_ENV === 'production'
    ? '/' + "progetto_ium_tweb_2022_2023_war_exploded" + '/'
    : '/'
})
