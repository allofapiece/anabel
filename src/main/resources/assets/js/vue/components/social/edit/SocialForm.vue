<template>
    <div>
        <ValidationObserver v-slot="{ invalid }">
            <v-form
                    id="social-form"
                    ref="form"
                    v-model="invalid"
                    @submit.prevent="submit"
            >
                <v-row>
                    <v-col cols="12" md="2" class="text-center">
                        <v-btn fab dark medium>
                            <v-icon medium dark>{{selected.icon}}</v-icon>
                        </v-btn>
                    </v-col>
                    <v-col cols="12" md="3">
                        <ValidationProvider
                                name="network"
                                ref="network"
                                rules="required"
                        >
                            <v-select
                                    v-model="network"
                                    :items="socialNames"
                                    label="Social Network"
                                    required
                                    slot-scope="{ errors }"
                                    :error-messages="errors"
                            >
                            </v-select>
                        </ValidationProvider>
                    </v-col>
                    <v-col cols="12" md="5">
                        <ValidationProvider
                                name="link"
                                ref="link"
                                rules="required|max:50"
                        >
                            <v-text-field
                                    v-model="link"
                                    slot-scope="{ errors }"
                                    :error-messages="errors"
                                    label="Link"
                                    required
                                    type="text"
                            ></v-text-field>
                        </ValidationProvider>
                    </v-col>
                    <v-col cols="12" md="2">
                        <v-btn
                                block
                                :disabled="invalid"
                                color="primary"
                                class="mt-3"
                                type="submit"
                        >
                            Add Link
                        </v-btn>
                    </v-col>
                </v-row>
                <input type="hidden" name="id" v-model="editId"/>
            </v-form>
        </ValidationObserver>
        <h3 class="headline text--primary">Your Social Networks</h3>
        <v-divider class="mb-3"></v-divider>
        <SocialList :socials="existentSocials"></SocialList>
    </div>
</template>

<script>
    import {mapState, mapGetters} from 'vuex'
    import SocialList from 'vue/components/social/edit/SocialList.vue'
    import store from 'store/store'
    import userService from 'service/UserService'
    import userSocials from 'service/UserSocialService'

    export default {
        components: {
            SocialList
        },
        props: {
            existentSocials: Array
        },
        data() {
            return {
                network: 'vk',
                link: '',
                invalid: true,
                editId: 0
            }
        },
        computed: {
            ...mapState('social', ['socials']),
            ...mapGetters('social', ['byName']),
            selected() {
                return this.byName(this.network)
            },
            socialNames() {
                return this.socials.length ? this.socials.map(social => social.name) : []
            }
        },
        methods: {
            submit() {
                userSocials.add(this.link, this.selected).then(() => userService.syncSocials())
            },
            editSocial(id) {
                console.log(id)
            },
            removeSocial(id) {
                userService.deleteSocial(id)
            },
            addEventListener: function () {
                this.$root.$on('social.edit', (id) => this.editSocial(id))
                this.$root.$on('social.remove', (id) => this.removeSocial(id))
            },
            removeEventListener: function () {
                this.$root.$off('social.edit')
                this.$root.$off('social.remove')
            }
        },
        created: function () {
            this.addEventListener()
        },
        beforeDestroy: function () {
            this.removeEventListener()
        },
    }
</script>
