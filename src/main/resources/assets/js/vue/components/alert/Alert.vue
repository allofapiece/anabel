<template>
    <div v-show="this.alert">
        <component v-if="alert.component" :is="alert.component" v-bind="alert.params"></component>
        <v-alert v-else class="mt-2 position-fixed"
                 transition="fade-transition"
                 :type="this.alert.type"
                 text
                 border="right"
                 prominent>
            <v-row align="center">
                <v-col class="grow">{{this.alert.message}}</v-col>
                <v-col class="shrink">
                    <router-link v-for="(action, index) in this.alert.actions" :to="action.link" :key="index">
                        <v-btn :class="action.type ? action.type : 'primary'">
                            {{ action.text }}
                        </v-btn>
                    </router-link>
                </v-col>
            </v-row>
        </v-alert>
    </div>
</template>

<script>
    import alertService from 'alert/alert-service'
    import ReverifyEmail from './alerts/ReverifyEmail.vue'

    export default {
        components: {
            ReverifyEmail
        },
        computed: {
            alert() {
                return alertService.state.alert
            }
        },
    }
</script>
