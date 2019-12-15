<template>
    <div>
        <div class="d-flex">
            <h3 class="display-1">Services</h3>
            <v-spacer></v-spacer>
            <ServiceDialog></ServiceDialog>
        </div>
        <hr class="mb-2"/>
        <v-carousel
                cycle
                height="400"
                hide-delimiter-background
                show-arrows-on-hover
        >
            <v-carousel-item
                    v-for="(service, i) in services"
                    :key="service.id"
            >
                <v-sheet
                        height="100%"
                >
                    <router-link :to="`/services/${service.id}`">
                        <v-row
                                class="fill-height"
                                align="center"
                                justify="center"
                        >
                            <div class="display-3">{{ service.name }}</div>
                        </v-row>
                    </router-link>
                </v-sheet>
            </v-carousel-item>
        </v-carousel>
    </div>
</template>

<script>
    import profileUtil from 'util/profile'
    import userService from 'service/UserService'
    import SocialList from 'vue/components/social/SocialList.vue'
    import empty from 'is-empty'
    import {mapState} from 'vuex'
    import ServiceDialog from "./ServiceDialog.vue";

    export default {
        components: {
            ServiceDialog
        },
        data() {
            return {
            }
        },
        methods: {

        },
        computed: {
            ...mapState('service', ['services'])
        },
        beforeMount() {

        }
    }
</script>
