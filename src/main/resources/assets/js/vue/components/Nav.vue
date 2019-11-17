<template>
    <div>
        <v-toolbar>
            <v-container>
                <v-row align="center">
                    <v-toolbar-title>Anabel</v-toolbar-title>
                    <div class="flex-grow-1"></div>

                    <template v-if="this.profile">
                        <v-btn :to="'/' + this.profile.slug" icon class="ml-2">
                            <v-icon>mdi-account-circle</v-icon>
                        </v-btn>
                        <v-btn text v-if="this.profile" @click="logout" class="ml-2">
                            Logout
                        </v-btn>
                    </template>
                    <template v-else>
                        <router-link to="/signin">
                            <v-btn outlined v-if="!this.profile" class="ml-2 text-capitalize">
                                Sign In
                            </v-btn>
                        </router-link>
                        <router-link to="/signup">
                            <v-btn v-if="!this.profile" class="ml-2 primary text-capitalize">
                                Sign Up
                            </v-btn>
                        </router-link>
                    </template>
                </v-row>
            </v-container>
        </v-toolbar>
    </div>
</template>

<script>
    import authService from 'service/AuthService'
    import {mapState} from 'vuex'

    export default {
        computed: {
            ...mapState('profile', ['profile']),
        },
        methods: {
            logout() {
                authService.logout()
            }
        }
    }
</script>
