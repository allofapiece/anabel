<template>
    <div>
        <SocialItem v-for="social in this.socials" v-bind:social="social" :amount="socials.length" :key="social.id"></SocialItem>
    </div>
</template>

<script>
    import SocialItem from './SocialItem.vue'

    export default {
        components: {
            SocialItem
        },
        props: {
            socials: Array
        },
    }
</script>
