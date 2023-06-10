<template>
    <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">{{ title }}</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    Ripetizione di {{ repetition.teaching.course.title }} con {{ repetition.teaching.professor.name }}
                    {{ repetition.teaching.professor.surname }} del giorno {{ formatDate(repetition.date) }} alle
                    {{ formatTime(repetition.time) }}
                </div>
                <div class="modal-footer">
                    <button data-bs-dismiss="modal" class="modal-button btn btn-primary rounded-pill mb-3">
                        Chiudi
                    </button>

                    <button v-on:click="executeOperation" class="modal-button btn btn-primary rounded-pill mb-3"
                        data-bs-dismiss="modal">Ok</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import { Toast } from "bootstrap"
import { formatDate, formatTime } from '../util/DateFormatter'
import { changeToastMessage } from '../util/ChangeToastMessage'

export default {
    name: "MyRepetitionModal",
    props: ["title", "repetition", "modalId", "newState"],
    methods: {
        formatDate,
        formatTime,
        changeToastMessage,
        openToast() {
            const toastLiveExample = $("#liveToast")
            const toast = new Toast(toastLiveExample)
            toast.show()
        },
        executeOperation() {
            let self = this
            $.ajax("RepetitionServlet", {
                method: "POST",
                data: {
                    operation: "edit",
                    idRepetition: self.repetition.idRepetition,
                    newState: self.newState
                },
                success() {
                    if (self.newState === "1") {
                        changeToastMessage("Prenotazione confermata con successo!")
                        self.$emit("confirm-repetition")
                    }

                    else if (self.newState === "2") {
                        changeToastMessage("Prenotazione eliminata con successo!")
                        self.$emit("delete-repetition")
                    }

                    self.openToast()
                }
            })
        }
    }
}
</script>