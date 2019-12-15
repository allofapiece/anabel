<template>
    <v-dialog
            v-model="dialog"
            width="500"
    >
        <template v-slot:activator="{ on }">
            <v-btn
                    color="green" text large style="height: 100%;"
                    v-on="on"
            >
                Add Catalog
            </v-btn>
        </template>
        <v-card>
            <ValidationObserver v-slot="{ invalid }">
                <v-form
                        id="catalog-form"
                        ref="product-form"
                        v-model="invalid"
                        @submit.prevent="add"
                >
            <v-card-title
                    class="headline grey lighten-2"
                    primary-title
            >
                New Catalog
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
                        <ValidationProvider
                                name="catalog"
                                ref="catalog"
                                rules="required"
                        >
                            <v-text-field
                                    v-model="catalog.name"
                                    label="Catalog Name"
                                    slot-scope="{ errors }"
                                    :error-messages="errors"
                                    type="text"
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
                    Add Catalog
                </v-btn>
            </v-card-actions>
                </v-form>

            </ValidationObserver>
        </v-card>
    </v-dialog>
</template>

<script>
    import catalogService from 'service/CatalogService'
    import alertService from 'alert/alert-service'

    export default {
        props: {
            catalog: {
                type: Object,
                default: {}
            }
        },
        components: {
        },
        data() {
            return {
                invalid: true,
                dialog: false
            }
        },
        methods: {
            add() {
                catalogService.add({
                    name: this.catalog.name,
                    service: `/${this.catalog.service.id}`
                }).then((result) => {
                    this.dialog = false
                    alertService.push('Catalog was added.')
                })
            }
        },
        computed: {
        },
        mounted() {
        }
    }
</script>
