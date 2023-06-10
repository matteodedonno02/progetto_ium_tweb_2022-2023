<template>
    <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">{{ title }}</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="d-flex justify-content-center pb-3">
                        <img :src="event.professor.imageUrl" class="modal-image" alt="Professor image">
                    </div>

                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Prof.</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control"
                                v-bind:value="event.professor.name + ' ' + event.professor.surname" disabled>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Corso</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" v-bind:value="event.course.title" disabled>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Data</label>
                        <div class="col-sm-10">
                            <input type="date" class="form-control" v-bind:value="fromDateToString(event.start)" disabled>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Ora</label>
                        <div class="col-sm-10">
                            <input type="time" class="form-control" v-bind:value="event.start.getHours() + ':00'" disabled>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button data-bs-dismiss="modal" class="modal-button btn btn-primary rounded-pill mb-3">
                        Chiudi
                    </button>
                    <button v-on:click="getIdTeaching" data-bs-dismiss="modal"
                        class="modal-button btn btn-primary rounded-pill mb-3">
                        Conferma
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import { formatTime, fromDateToString } from '@/util/DateFormatter';
import { Toast } from 'bootstrap'
import { changeToastMessage } from '@/util/ChangeToastMessage';

export default {
    name: "NewRepetitionModal",
    props: ["modalId", "title", "event", "loggedUser"],
    methods: {
        formatTime,
        fromDateToString,
        changeToastMessage,
        openToast() {
            const toastLiveExample = $("#liveToast")
            const toast = new Toast(toastLiveExample)
            toast.show()
        },
        getIdTeaching() {
            let self = this
            $.ajax("TeachingServlet", {
                method: "GET",
                data: {
                    operation: "getIdTeaching",
                    serialNumber: self.event.professor.serialNumber,
                    idCourse: self.event.course.idCourse
                },
                success(data) {
                    self.executeOperation(data.idTeaching)
                }
            })
        },
        executeOperation(idTeaching) {
            let self = this
            $.ajax("RepetitionServlet", {
                method: "POST",
                data: {
                    operation: "add",
                    email: self.loggedUser.email,
                    idTeaching: idTeaching,
                    date: fromDateToString(self.event.start),
                    time: self.event.start.getHours() + ':00:00'
                },
                success: () => {
                    self.$emit("change-page")
                }
            })
        }
    }
}
</script>