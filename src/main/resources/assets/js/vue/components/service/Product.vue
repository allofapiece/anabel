<template>
    <v-card elevation="4" class="d-flex flex-column flex-wrap justify-center" style="height: 200px;">
        <v-card-title class="justify-center">{{product.name}}</v-card-title>
        <v-card-text class="justify-center">
            <h1 class="display-3 text-center">{{product.price}}$</h1>
        </v-card-text>
        <v-card-actions>
            <BookTime :product="product"></BookTime>
        </v-card-actions>
    </v-card>
</template>

<script>
    import clientService from 'service/ClientService'
    import alert from 'alert/alert-service'
    import CatalogList from 'vue/components/service/CatalogList.vue'
    import BookTime from 'vue/components/service/BookTime.vue'

    export default {
        props: {
            catalogs: Array,
            product: Object
        },
        components: {
            BookTime
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
