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
import DaySpan from 'dayspan-vuetify'

import 'dayspan-vuetify/dist/lib/dayspan-vuetify.min.css'

import userService from './service/UserService'
import serviceService from './service/ServiceService'
import socialNetworkService from './service/SocialNetworkService'

Vue.config.productionTip = false

Vue.use(DaySpan, {
    methods: {
        getDefaultEventColor: () => '#1976d2'
    }
});

new Vue({
    router,
    store,
    vuetify,
    render: a => a(App),
    beforeMount() {
        userService.sync()
        socialNetworkService.sync()
        serviceService.sync()
    }
}).$mount('#app');
