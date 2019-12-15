<template>
    <v-dialog
            v-model="dialog"
            width="500"
    >
        <v-card>
            <ValidationObserver v-slot="{ invalid }">
                <v-form
                        id="product-form"
                        ref="product-form"
                        v-model="invalid"
                        @submit.prevent="add"
                >
            <v-card-title
                    class="headline grey lighten-2"
                    primary-title
            >
                New Product
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
                        <ValidationProvider
                                name="name"
                                ref="name"
                                rules="required"
                        >
                            <v-text-field
                                    v-model="product.name"
                                    label="Product Name"
                                    slot-scope="{ errors }"
                                    :error-messages="errors"
                                    type="text"
                            ></v-text-field>
                        </ValidationProvider>
                        <ValidationProvider
                                name="price"
                                ref="price"
                                rules="required"
                        >
                            <v-text-field
                                    v-model="product.price"
                                    label="Price"
                                    slot-scope="{ errors }"
                                    :error-messages="errors"
                                    type="number"
                            ></v-text-field>
                        </ValidationProvider>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                        :disabled="invalid"
                        color="success"
                        class="mr-4"
                        type="submit"
                >
                    Add Product
                </v-btn>
            </v-card-actions>
                </v-form>

            </ValidationObserver>
        </v-card>
    </v-dialog>
</template>

<script>
    import productService from 'service/ProductService'
    import alertService from 'alert/alert-service'

    export default {
        props: {
            dialog: Boolean,
            product: {
                type: Object,
                default: {}
            }
        },
        components: {
        },
        data() {
            return {
                invalid: true,
            }
        },
        methods: {
            add() {
                productService.add({
                    name: this.product.name,
                    price:this. product.price,
                    catalog: `/${this.product.catalog.id}`
                }).then((result) => {
                    alertService.push('Product was added.')
                    this.$emit('update:dialog', false)
                })

            }
        },
        computed: {
        },
        mounted() {
        }
    }
</script>
