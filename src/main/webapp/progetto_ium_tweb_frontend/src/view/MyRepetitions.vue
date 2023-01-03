<template>
    <CustomToast />

    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item active" aria-current="page">Le mie ripetizioni</li>
        </ol>
    </nav>

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
</template>

<script>
import $ from 'jquery'
import MyRepetitionCard from '@/components/MyRepetitionCard.vue'
import MyRepetitionCardLoading from '@/components/MyRepetitionCardLoading.vue'
import CustomToast from '@/components/CustomToast.vue'

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
        }
    },
    components: {
        MyRepetitionCard,
        MyRepetitionCardLoading,
        CustomToast
    },
    computed: {
        deletedRepetitionsComputed() {
            return this.dele
        }
    },
    methods: {
        getUserRepetitions(email) {
            let self = this
            $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
                method: "GET",
                data: {
                    operation: "selectByEmail",
                    email: email,
                },
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    setTimeout(() => {
                        self.loggedUserRepetitions = data
                        self.loadRepetitionsByState(data)
                    }, 2000)
                }
            })
        },
        loadRepetitionsByState(repetitions) {
            this.pendingRepetitions = []
            repetitions.forEach((repetition) => {
                if (repetition.state === 0) {
                    this.pendingRepetitions.push(repetition)
                }
            })
            this.confirmedRepetitions = []
            repetitions.forEach((repetition) => {
                if (repetition.state === 1) {
                    this.confirmedRepetitions.push(repetition)
                }
            })
            this.deletedRepetitions = []
            repetitions.forEach((repetition) => {
                if (repetition.state === 2) {
                    this.deletedRepetitions.push(repetition)
                }
            })
        },
        moveRepetitionToDeletedRepetitions(repetition) {
            this.pendingRepetitions.pop(repetition)
            repetition.state = 2
            this.deletedRepetitions.push(repetition)
        },
        moveRepetitionToConfirmedRepetitions(repetition) {
            this.pendingRepetitions.pop(repetition)
            repetition.state = 1
            this.confirmedRepetitions.push(repetition)
        }
    },
    mounted() {
        this.getUserRepetitions(this.loggedUserEmail)
    }
}
</script>