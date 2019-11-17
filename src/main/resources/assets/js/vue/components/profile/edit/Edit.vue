<template>
    <v-container>
        <v-row>
            <v-col class="col-12 col-md-3">
                <v-card class="mb-3">
                    <v-navigation-drawer permanent style="width:100%" floating>
                        <v-list-item link :to="'/' + this.$store.state.profile.profile.slug">
                            <v-list-item-icon>
                                <v-icon>arrow_back</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>Back to Profile</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                    </v-navigation-drawer>
                </v-card>
                <v-card>
                    <v-navigation-drawer permanent style="width:100%" floating>
                        <v-list dense nav>
                            <v-list-item link to="/user/edit/general">
                                <v-list-item-icon>
                                    <v-icon>track_changes</v-icon>
                                </v-list-item-icon>
                                <v-list-item-content>
                                    <v-list-item-title>General</v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list>
                    </v-navigation-drawer>
                </v-card>
            </v-col>
            <v-col class="col-12 col-md-9">
                <router-view></router-view>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        computed: {
            ...mapState('profile', ['profile']),
        },
    }
</script>
