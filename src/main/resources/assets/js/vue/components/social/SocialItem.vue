<template>
    <div>
        <v-btn :href="this.href" class="mx-2  text-lowercase" dark small fab>
            <v-icon medium dark>{{ this.social.socialNetwork.icon }}</v-icon>
        </v-btn>
    </div>
</template>

<script>

    export default {
        props: {
            social: Object,
            amount: Number
        },
        computed: {
            href() {
                return `${this.social.socialNetwork.protocol}://${this.social.socialNetwork.domain}/${this.social.link}`
            },
        },
    }
</script>
