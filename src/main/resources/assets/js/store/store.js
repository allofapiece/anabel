import Vue from 'vue'
import Vuex from 'vuex'
import profile from './modules/profile'
import auth from './modules/auth'
import social from './modules/social'
import service from './modules/service'

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        'profile': profile,
        'auth': auth,
        'social': social,
        'service': service,
    },
})
