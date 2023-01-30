<template>
    <CustomToast ref="customToast" />

    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item active" aria-current="page">Tutte le ripetizioni</li>
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
            Non ci sono ripetizioni da effettuare
        </div>
        <div v-else>
            <div v-for="repetition in pendingRepetitions" v-bind:key="repetition.idRepetition">
                <div v-if="repetition.state === 0">
                    <RepetitionCard v-bind:repetition="repetition" />
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
            Non ci sono ripetizioni confermate
        </div>
        <div v-else>
            <div v-for="repetition in confirmedRepetitions" v-bind:key="repetition.idRepetition">
                <div v-if="repetition.state === 1">
                    <RepetitionCard v-bind:repetition="repetition" />
                </div>
            </div>
        </div>
    </div>

    <h4 class="pt-5">Ripetizioni cancellate</h4>
    <div v-if="cancelledRepetitions === null">
        <MyRepetitionCardLoading />
        <MyRepetitionCardLoading />
        <MyRepetitionCardLoading />
    </div>
    <div v-else>
        <div v-if="cancelledRepetitions.length === 0">
            Non ci sono ripetizioni cancellate
        </div>
        <div v-else>
            <div v-for="repetition in cancelledRepetitions" v-bind:key="repetition.idRepetition">
                <div v-if="repetition.state === 2">
                    <RepetitionCard v-bind:repetition="repetition" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import RepetitionCard from '@/components/RepetitionCard.vue'
import MyRepetitionCardLoading from '@/components/MyRepetitionCardLoading.vue'
import CustomToast from '@/components/CustomToast.vue'

export default {
    name: "AdminRepetitions",
    data() {
        return {
            repetitions: null,
            pendingRepetitions: null,
            confirmedRepetitions: null,
            cancelledRepetitions: null
        }
    },
    components: {
        RepetitionCard,
        MyRepetitionCardLoading,
        CustomToast
    },
    methods: {
        getUserRepetitions() {
            let self = this
            $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
                method: "GET",
                data: {
                    operation: "select",
                },
                success: (data) => {
                    self.repetitions = data
                    self.loadRepetitionsByState(data)
                }
            })
        },
        loadRepetitionsByState(repetitions) {
            this.pendingRepetitions = []
            this.confirmedRepetitions = []
            this.cancelledRepetitions = []
            repetitions.forEach((repetition) => {
                switch (repetition.state) {
                    case 0:
                        this.pendingRepetitions.push(repetition)
                        break
                    case 1:
                        this.confirmedRepetitions.push(repetition)
                        break
                    case 2:
                        this.cancelledRepetitions.push(repetition)
                        break
                }
            })
        }
    },
    mounted() {
        this.getUserRepetitions()
    }
}
</script>