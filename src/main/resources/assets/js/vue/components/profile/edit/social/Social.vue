<template>
    <v-card elevation="4" class="xs12">
        <v-card-title>Sharing</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
            <SocialForm :existent-socials="this.profile.socials"></SocialForm>
        </v-card-text>
    </v-card>
</template>

<script>
    import SocialForm from 'vue/components/social/edit/SocialForm.vue'
    import {mapState} from 'vuex'

    export default {
        components: {
            SocialForm
        },
        computed: {
            ...mapState('profile', ['profile'])
        }

    }
</script>
