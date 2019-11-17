<template>
    <v-card elevation="4" class="xs12">
        <v-card-title>Security</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
            <Slug></Slug>
        </v-card-text>
    </v-card>
</template>

<script>
    import Slug from './Slug.vue'

    export default {
        components: {
            Slug
        }
    }
</script>
