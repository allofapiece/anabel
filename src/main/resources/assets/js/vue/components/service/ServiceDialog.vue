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
                Add Service
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
                New Service
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text>
                        <ValidationProvider
                                name="service"
                                ref="service"
                                rules="required"
                        >
                            <v-text-field
                                    v-model="service.name"
                                    label="Service Name"
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
                    Add Service
                </v-btn>
            </v-card-actions>
                </v-form>

            </ValidationObserver>
        </v-card>
    </v-dialog>
</template>

<script>
    import serviceService from 'service/ServiceService'
    import alertService from 'alert/alert-service'

    export default {
        props: {
            service: {
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
                serviceService.add({
                    name: this.service.name,
                }).then((result) => {
                    this.dialog = false
                    alertService.push('Service was added.')
                })
            }
        },
        computed: {
        },
        mounted() {
        }
    }
</script>
