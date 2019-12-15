<template>
    <v-card  elevation="4" class="d-flex flex-column flex-wrap justify-center" style="height: 200px; cursor: pointer;">
        <v-card-title class="justify-center">New Product</v-card-title>
    </v-card>
</template>

<script>
    import serviceService from 'service/ServiceService'
    import CatalogList from 'vue/components/service/CatalogList.vue'

    export default {
        props: {
        },
        components: {
        },
        data() {
            return {
            }
        },
        methods: {

        },
        computed: {
        },
        beforeMount() {
        }
    }
</script>
