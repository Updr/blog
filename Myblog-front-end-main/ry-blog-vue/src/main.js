// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/style.less'
import store from './store'
import MavonEditor from 'mavon-editor'
import 'aos/dist/aos.css'
import AOS from 'aos/dist/aos.js'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(MavonEditor)
Vue.use(AOS)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
