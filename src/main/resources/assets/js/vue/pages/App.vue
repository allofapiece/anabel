<template>
    <v-app>
        <Nav></Nav>
        <v-content>
            <router-view></router-view>
        </v-content>
        <Alert></Alert>
    </v-app>
</template>

<script>
    import Nav from "../components/Nav.vue"
    import Alert from 'vue/components/alert/Alert.vue'


    export default {
        components: {Nav, Alert},
        mounted() {
        }
    }
</script>
