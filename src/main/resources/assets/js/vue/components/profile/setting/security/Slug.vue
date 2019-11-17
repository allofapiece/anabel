<template>
    <div>
        <div class="subtitle-1">Account Link</div>
        <ValidationObserver v-slot="{ invalid, changed }">
            <v-form
                    id="slug-form"
                    ref="slug-form"
                    v-model="invalid"
                    @submit.prevent="submit"
            >
                <!--                TODO validate only when field is changed -->
                <ValidationProvider
                        name="slug"
                        ref="slug"
                        :rules="changed ? 'required|remote:/api/action/verify/slug' : ''"
                >
                    <v-text-field
                            v-model="slug"
                            slot-scope="{ errors }"
                            :error-messages="errors"
                            required
                            prefix="anabel.com/"
                            type="text"
                    ></v-text-field>
                </ValidationProvider>
                <v-btn
                        :disabled="invalid || !changed"
                        color="primary"
                        class="mr-4"
                        type="submit"
                >
                    Take Address
                </v-btn>
            </v-form>
        </ValidationObserver>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import userService from 'service/UserService'
    import alertService from 'alert/alert-service'

    export default {
        data() {
            return {
                invalid: true,
                changed: false,
                slug: this.profile ? this.profile.slug : 'user'
            }
        },
        computed: {
            ...mapState('profile', ['profile']),
        },
        methods: {
            submit() {
                userService.patch(this.profile.id, {slug: this.slug}).then(response => {
                    if (response.status === 200) {
                        this.$store.commit('profile/slug', this.slug)
                        alertService.push('Address has been taken.')
                    } else {
                        alertService.push({
                            message: 'Something wrong',
                            type: 'error'
                        })
                    }
                })
            }
        },
        mounted() {
            if (this.profile) {
                this.slug = this.profile.slug
            }

            this.$store.subscribe((mutation) => {
                if (mutation.type === 'profile/profile') {
                    this.slug = mutation.payload.slug
                }
            })
        }
    }
</script>
