<template>
    <div>
        <h3 class="display-1">{{catalog.name}}</h3>
        <v-layout wrap align-center>
            <v-row>
                <v-col v-for="product in this.catalog.products" :key="product.id" cols="4">
                    <Product :product="product"></Product>
                </v-col>
                <v-col cols="4">
                    <NewProduct @click.native="$root.$emit('product.add', catalog.id)"></NewProduct>
                </v-col>
            </v-row>
        </v-layout>
    </div>
</template>

<script>
    import serviceService from 'service/ServiceService'
    import Product from 'vue/components/service/Product.vue'
    import NewProduct from "./NewProduct.vue";

    export default {
        components: {
            NewProduct,
            Product
        },
        props: {
            catalog: Object,
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
