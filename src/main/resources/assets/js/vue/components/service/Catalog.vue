<template>
    <div class="d-flex flex-column flex-wrap mt-2" style="width: 100%;">
        <div class="d-flex">
            <h3 class="display-2">Product Catalogs</h3>
            <v-spacer></v-spacer>
            <CatalogDialog :catalog="catalog"></CatalogDialog>
        </div>
        <CatalogList :catalogs="catalogs"></CatalogList>
        <ProductDialog :dialog.sync="dialog" :product="product"></ProductDialog>
    </div>
</template>

<script>
    import serviceService from 'service/ServiceService'
    import CatalogList from 'vue/components/service/CatalogList.vue'
    import ProductDialog from "./ProductDialog.vue";
    import CatalogDialog from "./CatalogDialog.vue";

    export default {
        props: {
            catalogs: Array,
            service: Object,
        },
        components: {
            CatalogList,
            ProductDialog,
            CatalogDialog
        },
        data() {
            return {
                dialog: false,
                product: {},
            }
        },
        methods: {
            addEventListener: function () {
                console.log('addEventListener')
                this.$root.$on('product.add', (id) => this.addProduct(id))
            },
            removeEventListener: function () {
                console.log('removeEventListener')
                this.$root.$off('product.add')
            },
            addProduct(id) {
                console.log('product.add')
                this.dialog = true
                this.product = {
                    catalog: {
                        id: id
                    }
                }
            }
        },
        computed: {
            catalog() {
                return {
                    service: this.service
                }
            }
        },
        beforeMount() {
        },
        created: function () {
            this.addEventListener()
        },
        beforeDestroy: function () {
            this.removeEventListener()
        },
    }
</script>
