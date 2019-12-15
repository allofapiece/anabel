<template>
    <div style="width: 100%">

        <h3 class="display-2">Clients</h3>
        <v-data-table
                style="width:100%"
                :headers="headers"
                :items="clients"
                sort-by="calories"
                class="elevation-1"
        >
            <template v-slot:item.action="{ item }">
                <v-btn @click="approve(item.id)" icon v-on="on">
                    <v-icon>done</v-icon>
                </v-btn>
                <v-btn icon v-on="on">
                    <v-icon>mail</v-icon>
                </v-btn>
            </template>
        </v-data-table>
    </div>

</template>

<script>
    import clientService from 'service/ClientService'
    import alert from 'alert/alert-service'

    export default {
        props: {
            service: {
                type: Object
            }
        },
        components: {},
        data: () => ({
            clients: [],
            headers: [
                {text: 'Name', value: 'user.fullName'},
                {text: 'Status', value: 'status'},
                {text: 'Date', value: 'createdAt'},
                {text: 'Actions', value: 'action', sortable: false},
            ],
        }),
        computed: {},
        watch: {
            service(value) {
                if (value) {
                    this.loadClients()
                }
            }
        },
        methods: {
            approve(id) {
                clientService.approve(id).then((result) => {
                    alert.push('Client was approved.')
                    this.loadClients()
                })
            },
            loadClients() {
                clientService.getByServiceId(this.service.id).then((result) => {
                    this.clients = result.data._embedded.clients
                })
            }
        },
        created() {

        }
    }
</script>
