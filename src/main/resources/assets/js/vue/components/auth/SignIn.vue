<template>
    <Auth>
        signin
        <ValidationObserver v-slot="{ invalid }">
            <v-form
                    id="signin-form"
                    ref="form"
                    v-model="invalid"
                    @submit.prevent="submit"
            >
                <ValidationProvider
                        name="email"
                        ref="email"
                        rules="required|email|max:320"
                >
                    <v-text-field
                            v-model="email"
                            prepend-icon="email"
                            slot-scope="{ errors }"
                            :error-messages="errors"
                            label="Email"
                            required
                            type="email"
                    ></v-text-field>
                </ValidationProvider>
                <ValidationProvider
                        mode="aggressive"
                        name="password"
                        ref="password"
                        rules="required"
                >
                    <v-text-field
                            v-model="password"
                            label="Password"
                            required
                            type="password"
                            prepend-icon="lock"
                            slot-scope="{ errors }"
                            :error-messages="errors"
                    ></v-text-field>
                </ValidationProvider>
                <v-btn
                        :disabled="invalid"
                        color="primary"
                        class="mr-4"
                        type="submit"
                >
                    Log In
                </v-btn>
            </v-form>
        </ValidationObserver>
    </Auth>
</template>

<script>
    import Auth from './Auth.vue'
    import authService from 'service/AuthService'

    export default {
        components: {Auth},
        data() {
            return {
                invalid: true,
                email: '',
                password: ''
            }
        },
        methods: {
            submit() {
                let router = this.$router

                authService.login({
                    'username': this.email,
                    'password': this.password
                }).then((result) => {
                    if (result) {
                        router.push('/feed')
                    }
                })
            }
        }
    }
</script>
