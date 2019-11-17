<template>
    <v-container>
        <v-row>
            <v-col class="col-12 col-md-3">
                <v-navigation-drawer permanent class="elevation-4" style="width:100%" floating>
                    <template v-if="this.isCurrent">
                        <v-list dense nav>
                            <v-list-item link to="/user/settings/general">
                                <v-list-item-icon>
                                    <v-icon>settings_input_component</v-icon>
                                </v-list-item-icon>
                                <v-list-item-content>
                                    <v-list-item-title>Settings</v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                            <v-list-item link to="/user/edit/general">
                                <v-list-item-icon>
                                    <v-icon>create</v-icon>
                                </v-list-item-icon>
                                <v-list-item-content>
                                    <v-list-item-title>Edit profile</v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list>
                    </template>
                </v-navigation-drawer>
            </v-col>
            <v-col class="col-12 col-md-9">
                <div class="spot">
                    <div class="spot-content">
                        <h3>{{ this.fullName }}</h3>
                        <blockquote v-if="this.profile.about">{{ this.profile.about }}</blockquote>
                        <hr/>
                    </div>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import profileUtil from 'util/profile'
    import util from 'util/util'
    import userService from 'service/UserService'
    import {mapState} from 'vuex'
    import empty from 'is-empty'

    export default {
        data() {
            return {
                profile: {},
            }
        },
        methods: {
            getCurrentSlug() {
                return this.$route.params.slug
            },
        },
        computed: {
            fullName() {
               return !empty(this.profile) ? profileUtil.fullName(this.profile) : 'User Name'
            },
            isCurrent() {
                return this.profile.id === this.$store.state.profile.profile.id
            }
        },
        beforeMount() {
            const current = this.$store.getters['profile/profile'],
                slug = this.getCurrentSlug()

            if (current && slug === current.slug) {
                this.profile = current
            } else {
                let that = this

                userService.getBySlug(slug).then((response) => {
                    that.profile = response.data
                })
            }
        }
    }
</script>
