<template>
    <div class="d-flex justify-content-between mb-3">
        <div class="d-flex align-center">
            <v-btn class="mx-2" dark small fab>
                <v-icon medium dark>{{ this.social.socialNetwork.icon }}</v-icon>
            </v-btn>
            <span class="text--primary headline">{{link}}</span>
        </div>
        <div class="ml-auto">
            <v-btn @click="$root.$emit('social.edit', social.id)" class="mx-2" dark small fab>
                <v-icon medium dark>edit</v-icon>
            </v-btn>
            <v-btn @click="$root.$emit('social.remove', social.id)" class="mx-2" dark small fab>
                <v-icon medium dark>delete</v-icon>
            </v-btn>
        </div>

    </div>
</template>

<script>
    export default {
        props: {
            social: Object
        },
        computed: {
            link() {
                return `${this.social.socialNetwork.domain}/${this.social.link}`
            },
        },
    }
</script>
