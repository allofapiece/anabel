<template>
    <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
        <template v-slot:activator="{ on }">
            <v-btn  @click="book" color="green" text dark v-on="on">Book Time</v-btn>
        </template>
        <v-card>
            <v-toolbar dark color="primary">
                <v-btn icon dark @click="dialog = false">
                    <v-icon>mdi-close</v-icon>
                </v-btn>
                <v-toolbar-title>Time Booking</v-toolbar-title>
            </v-toolbar>
            <ds-calendar ref="app" :calendar="calendar">
                <template slot="title">
                    DaySpan
                </template>

                <template slot="menuRight">
                    <v-btn icon large href="https://github.com/ClickerMonkey/dayspan-vuetify" target="_blank">
                        <v-avatar size="32px" tile>
                            <img src="https://simpleicons.org/icons/github.svg" alt="Github">
                        </v-avatar>
                    </v-btn>
                </template>

                <template slot="eventPopover" slot-scope="slotData">
                    <ds-calendar-event-popover
                            v-bind="slotData"
                            :read-only="readOnly"
                            @finish="saveState"
                    ></ds-calendar-event-popover>
                </template>

                <template slot="eventCreatePopover" slot-scope="{placeholder, calendar, close}">
                    <ds-calendar-event-create-popover
                            :calendar-event="placeholder"
                            :calendar="calendar"
                            :close="$refs.app.$refs.calendar.clearPlaceholder"
                            @create-edit="$refs.app.editPlaceholder"
                            @create-popover-closed="saveState"
                    ></ds-calendar-event-create-popover>
                </template>

                <template slot="eventTimeTitle" slot-scope="{calendarEvent, details}">
                    <div>
                        <v-icon class="ds-ev-icon"
                                v-if="details.icon"
                                size="14"
                                :style="{color: details.forecolor}">
                            {{ details.icon }}
                        </v-icon>
                        <strong class="ds-ev-title">{{ details.title }}</strong>
                    </div>
                    <div class="ds-ev-description">{{ getCalendarTime( calendarEvent ) }}</div>
                </template>

                <template slot="drawerBottom">
                    <div class="pa-3">
                        <v-checkbox
                                label="Read Only?"
                                v-model="readOnly"
                        ></v-checkbox>
                    </div>
                </template>

            </ds-calendar>
        </v-card>
    </v-dialog>
</template>

<script>
    import catalogService from 'service/CatalogService'
    import alertService from 'alert/alert-service'
    import { Calendar, Weekday, Month } from 'dayspan';
    import clientService from "../../../service/ClientService";
    import alert from "../../../alert/alert-service";

    export default {
        props: {
            product: {
                type: Object
            }
        },
        components: {
        },
        data: () => ({
            dialog: false,
            storeKey: 'dayspanState',
            calendar: Calendar.months(),
            readOnly: false,
            defaultEvents: [
                {
                    data: {
                        title: 'Weekly Meeting',
                        color: '#3F51B5'
                    },
                    schedule: {
                        dayOfWeek: [Weekday.MONDAY],
                        times: [9],
                        duration: 30,
                        durationUnit: 'minutes'
                    }
                },
                {
                    data: {
                        title: 'First Weekend',
                        color: '#4CAF50'
                    },
                    schedule: {
                        weekspanOfMonth: [0],
                        dayOfWeek: [Weekday.FRIDAY],
                        duration: 3,
                        durationUnit: 'days'
                    }
                },
                {
                    data: {
                        title: 'End of Month',
                        color: '#000000'
                    },
                    schedule: {
                        lastDayOfMonth: [1],
                        duration: 1,
                        durationUnit: 'hours'
                    }
                },
                {
                    data: {
                        title: 'Mother\'s Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.MAY],
                        dayOfWeek: [Weekday.SUNDAY],
                        weekspanOfMonth: [1]
                    }
                },
                {
                    data: {
                        title: 'New Year\'s Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.JANUARY],
                        dayOfMonth: [1]
                    }
                },
                {
                    data: {
                        title: 'Inauguration Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.JANUARY],
                        dayOfMonth: [20]
                    }
                },
                {
                    data: {
                        title: 'Martin Luther King, Jr. Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.JANUARY],
                        dayOfWeek: [Weekday.MONDAY],
                        weekspanOfMonth: [2]
                    }
                },
                {
                    data: {
                        title: 'George Washington\'s Birthday',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.FEBRUARY],
                        dayOfWeek: [Weekday.MONDAY],
                        weekspanOfMonth: [2]
                    }
                },
                {
                    data: {
                        title: 'Memorial Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.MAY],
                        dayOfWeek: [Weekday.MONDAY],
                        lastWeekspanOfMonth: [0]
                    }
                },
                {
                    data: {
                        title: 'Independence Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.JULY],
                        dayOfMonth: [4]
                    }
                },
                {
                    data: {
                        title: 'Labor Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.SEPTEMBER],
                        dayOfWeek: [Weekday.MONDAY],
                        lastWeekspanOfMonth: [0]
                    }
                },
                {
                    data: {
                        title: 'Columbus Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.OCTOBER],
                        dayOfWeek: [Weekday.MONDAY],
                        weekspanOfMonth: [1]
                    }
                },
                {
                    data: {
                        title: 'Veterans Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.NOVEMBER],
                        dayOfMonth: [11]
                    }
                },
                {
                    data: {
                        title: 'Thanksgiving Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.NOVEMBER],
                        dayOfWeek: [Weekday.THURSDAY],
                        weekspanOfMonth: [3]
                    }
                },
                {
                    data: {
                        title: 'Christmas Day',
                        color: '#2196F3',
                        calendar: 'US Holidays'
                    },
                    schedule: {
                        month: [Month.DECEMBER],
                        dayOfMonth: [25]
                    }
                }
            ]
        }),
        methods: {

            book() {
                clientService.add({
                    product: `/${this.product.id}`
                }).then(() => {
                    alert.push('Book request has been sent. You can see progress in `bookings`.')
                })
            }
        },
        computed: {
        },
        mounted() {
        }
    }
</script>
