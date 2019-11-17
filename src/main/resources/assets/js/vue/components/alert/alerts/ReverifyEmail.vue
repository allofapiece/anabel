<template>
    <v-alert v-else class="mt-2 position-fixed"
             transition="fade-transition"
             type="warning"
             text
             outlined
             prominent
             border="left">
        <v-row align="center">
            <v-col class="grow">Your mail has been expired. We can send verifying email again.</v-col>
            <v-col class="shrink">
                <v-btn class="warning" @click="resend">Send</v-btn>
            </v-col>
        </v-row>
    </v-alert>
</template>

<script>
    import alertService from 'alert/alert-service'
    import authService from 'service/AuthService'

    export default {
        methods: {
            resend() {
                authService.resendVerification(this.$route.query.token).then(() => {
                    alertService.setCurrent(false)
                    alertService.next()
                })
            }
        }
    }
</script>
