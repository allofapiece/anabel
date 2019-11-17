import Vue from 'vue'
import {extend, setInteractionMode, ValidationObserver, ValidationProvider} from 'vee-validate/dist/vee-validate.full'
import * as rules from './rules'

setInteractionMode('eager')

for (let rule in rules) {
    extend(rule, rules[rule]);
}

Vue.component('ValidationObserver', ValidationObserver)
Vue.component('ValidationProvider', ValidationProvider)
