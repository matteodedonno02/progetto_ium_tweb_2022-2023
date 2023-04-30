<template>
    <CustomToast ref="customToast" />

    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item active" aria-current="page">Le mie ripetizioni</li>
        </ol>
    </nav>

    <!-- TODO: Da sostituire con uno switch -->
    <button v-on:click="changeViewType">switcha</button>

    <div v-if="viewType === 'list'">


        <h4 class="pt-5">Ripetizioni da effettuare</h4>
        <div v-if="pendingRepetitions === null">
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
        </div>
        <div v-else>
            <div v-if="pendingRepetitions.length === 0">
                Non hai ripetizioni da effettuare
            </div>
            <div v-else>
                <div v-for="repetition in pendingRepetitions" v-bind:key="repetition.idRepetition">
                    <div v-if="repetition.state === 0">
                        <MyRepetitionCard v-bind:repetition="repetition"
                            @confirm-repetition="moveRepetitionToConfirmedRepetitions"
                            @delete-repetition="moveRepetitionToDeletedRepetitions" />
                    </div>
                </div>
            </div>
        </div>

        <h4 class="pt-5">Ripetizioni confermate</h4>
        <div v-if="confirmedRepetitions === null">
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
        </div>
        <div v-else>
            <div v-if="confirmedRepetitions.length === 0">
                Non hai ripetizioni confermate
            </div>
            <div v-else>
                <div v-for="repetition in confirmedRepetitions" v-bind:key="repetition.idRepetition">
                    <div v-if="repetition.state === 1">
                        <MyRepetitionCard v-bind:repetition="repetition" />
                    </div>
                </div>
            </div>
        </div>

        <h4 class="pt-5">Ripetizioni cancellate</h4>
        <div v-if="deletedRepetitions === null">
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
            <MyRepetitionCardLoading />
        </div>
        <div v-else>
            <div v-if="deletedRepetitions.length === 0">
                Non hai ripetizioni cancellate
            </div>
            <div v-else>
                <div v-for="repetition in deletedRepetitions" v-bind:key="repetition.idRepetition">
                    <div v-if="repetition.state === 2">
                        <MyRepetitionCard v-bind:repetition="repetition" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-else>
        <vue-cal :events="events" :timeCellHeight="100" locale="it" active-view="day" :time-from="15 * 60"
            :time-to="19 * 60" hide-weekends :disable-views="['years', 'year', 'month', 'week']" />
    </div>
</template>

<script>
import $ from 'jquery'
import MyRepetitionCard from '@/components/MyRepetitionCard.vue'
import MyRepetitionCardLoading from '@/components/MyRepetitionCardLoading.vue'
import CustomToast from '@/components/CustomToast.vue'
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import { formatDate, fromDateToString, formatTime, sumHours } from '../util/DateFormatter'


export default {
    name: "MyRepetition",
    props: ["loggedUserEmail"],
    data() {
        return {
            loggedUserRepetitions: null,
            pendingRepetitions: null,
            confirmedRepetitions: null,
            deletedRepetitions: null,
            todayRepetitions: null,
            viewType: "list",
            events: []
        }
    },
    components: {
        MyRepetitionCard,
        MyRepetitionCardLoading,
        CustomToast,
        VueCal
    },
    methods: {
        formatDate,
        fromDateToString,
        sumHours,
        getUserRepetitions(email) {
            let self = this
            $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
                method: "GET",
                data: {
                    operation: "selectByEmail",
                    email: email,
                },
                success: (data) => {
                    self.loggedUserRepetitions = data
                    self.loadRepetitionsByState(self.loggedUserRepetitions)

                    //TODO: Differenziare per stato di prenotazione
                    data.forEach((repetition) => {
                        self.events.push({
                            start: repetition.date + " " + formatTime(repetition.time),
                            end: repetition.date + " " + sumHours(formatTime(repetition.time)),
                            title: repetition.teaching.course.title,
                            content: repetition.teaching.professor.name + " " + repetition.teaching.professor.surname,
                            class: "materia",
                            course: repetition.teaching.course,
                            professor: repetition.teaching.professor
                        })
                    })
                }
            })
        },
        loadRepetitionsByState(repetitions) {
            this.pendingRepetitions = []
            this.confirmedRepetitions = []
            this.deletedRepetitions = []
            repetitions.forEach((repetition) => {
                switch (repetition.state) {
                    case 0:
                        this.pendingRepetitions.push(repetition)
                        break;
                    case 1:
                        this.confirmedRepetitions.push(repetition)
                        break;
                    case 2:
                        this.deletedRepetitions.push(repetition)
                        break;
                }
            })
        },
        moveRepetitionToDeletedRepetitions(repetition) {
            for (var i = 0; i < this.pendingRepetitions.length; i++)
                if (this.pendingRepetitions[i] == repetition)
                    this.pendingRepetitions.splice(i, 1)

            repetition.state = 2
            this.deletedRepetitions.push(repetition)
        },
        moveRepetitionToConfirmedRepetitions(repetition) {
            for (var i = 0; i < this.pendingRepetitions.length; i++)
                if (this.pendingRepetitions[i] == repetition)
                    this.pendingRepetitions.splice(i, 1)

            repetition.state = 1
            this.confirmedRepetitions.push(repetition)
        },
        changeViewType() {
            if (this.viewType === "list") {
                this.viewType = "calendar"
                $(".vuecal__arrow--prev").addClass("vuecal__arrow--prev-visible")
            } else {
                this.viewType = "list"
            }
        }
    },
    mounted() {
        this.getUserRepetitions(this.loggedUserEmail)
    }
}
</script>