import 'font-awesome/css/font-awesome.min.css'
import '@mdi/font/css/materialdesignicons.css'
import Vue from 'vue'
import vuetify from 'vuetify/vuetify'
import '../scss/main.sass'
import '@babel/polyfill'
import router from 'router/router'
import App from './vue/pages/App.vue'
import store from 'store/store'
import 'validate/vee-validate'

import userService from './service/UserService'
import socialNetworkService from './service/SocialNetworkService'

new Vue({
    router,
    store,
    vuetify,
    render: a => a(App),
    beforeMount() {
        userService.sync()
        socialNetworkService.sync()
    }
}).$mount('#app');
