<template>
    <div>
        <CatalogItem v-for="catalog in this.catalogs" :key="catalog.id" :catalog="catalog"></CatalogItem>
    </div>
</template>

<script>
    import serviceService from 'service/ServiceService'
    import CatalogItem from "./CatalogItem.vue";

    export default {
        components: {
            CatalogItem
        },
        props: {
            catalogs: Array
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
