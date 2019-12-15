<template>
    <v-container>
        <v-row>
            <h3 class="display-3">{{service.name}}</h3>
        </v-row>
        <v-divider></v-divider>
        <v-row>
            <Catalog :catalogs="catalogs" :service="service"></Catalog>
        </v-row>

        <v-row class="mb-3">
            <Clients :service="service"></Clients>
        </v-row>
        <v-row style="height: 300px;"></v-row>
    </v-container>
</template>

<script>
    import serviceService from 'service/ServiceService'
    import Catalog from "./Catalog.vue";
    import empty from 'is-empty'
    import Clients from "./Clients.vue";

    export default {
        components: {
            Clients,
            Catalog
        },
        data() {
            return {
                service: {}
            }
        },
        methods: {

        },
        computed: {
            catalogs() {
                return empty(this.service.catalogs) ? [] : this.service.catalogs
            }
        },
        beforeMount() {
            serviceService.get(this.$route.params.id).then((result) => {
                this.service = result.data
            })
        }
    }
</script>
