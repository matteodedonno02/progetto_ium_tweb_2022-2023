<template>
    <MyRepetitionModal @confirm-repetition="this.$emit('confirm-repetition', this.repetition)"
        v-bind:modalId="'modalConfirm' + repetition.idRepetition" newState="1" operation="confirmRepetition"
        title="Desideri segnare come effettuata la seguente ripetizione ?" v-bind:repetition="repetition" />

    <MyRepetitionModal @delete-repetition="this.$emit('delete-repetition', this.repetition)"
        v-bind:modalId="'modalDelete' + repetition.idRepetition" newState="2" operation="deleteRepetition"
        title="Desideri segnare come cancellata la seguente ripetizione ?" v-bind:repetition="repetition" />

    <div class="w-100 d-flex align-items-center">
        <div class="trash-icon mb-3" v-if="repetition.state === 0">
            <DeleteIcon data-bs-toggle="modal" v-bind:data-bs-target="'#modalDelete' + repetition.idRepetition" />
        </div>

        <div class="w-100 card-row d-flex align-items-center mb-3">
            <div class="card my-repetition-card me-5">
                <div class="card-body">
                    <h5 class="card-title">{{ repetition.teaching.course.title }}</h5>
                    <div class="col">
                        <div class="row">
                            <div class="col text-start">
                                <p class="card-text">
                                    Ripetizione con {{ repetition.teaching.professor.name }}
                                    {{ repetition.teaching.professor.surname }}
                                    il giorno {{ formatDate(repetition.date) }}
                                    alle {{ formatTime(repetition.time) }}
                                </p>
                            </div>
                            <div class="col text-end">
                                <div v-if="repetition.state === 0">
                                    <span class="fw-bold">Prenotata</span>
                                </div>
                                <div v-else-if="repetition.state === 1">
                                    <span class="fw-bold">Effettuata</span>
                                </div>
                                <div v-else-if="repetition.state === 2">
                                    <span class="fw-bold">Cancellata</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="mb-3" v-if="repetition.state === 0" data-bs-toggle="modal"
            v-bind:data-bs-target="'#modalConfirm' + repetition.idRepetition">
            <CheckIcon />
        </div>
    </div>



</template>

<script>
// import $ from 'jquery'
import { formatDate, formatTime } from '../util/DateFormatter'
import CheckIcon from 'vue-material-design-icons/Check.vue'
import DeleteIcon from 'vue-material-design-icons/Delete.vue'
import MyRepetitionModal from './MyRepetitionModal.vue'

export default {
    name: "MyRepetitionCard",
    props: ["repetition"],
    components: { MyRepetitionModal, CheckIcon, DeleteIcon },
    emits: ["delete-repetition", "confirm-repetition"],
    methods: {
        formatDate,
        formatTime,
        moveRepetitionToDeletedRepetitions() {
            this.$emit("delete-repetition", this.repetition)
        }
    }
}
</script>