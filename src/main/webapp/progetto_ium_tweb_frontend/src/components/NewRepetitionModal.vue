<template>
    <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">{{ title }}</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Prof.</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" v-bind:value="professorName" disabled>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Corso</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" v-bind:value="courseTitle" disabled>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Data</label>
                        <div class="col-sm-10">
                            <input type="date" class="form-control" v-bind:value="date" disabled>
                        </div>
                    </div>
                    <div class="mb-3 row">
                        <label class="col-sm-2 col-form-label">Ora</label>
                        <div class="col-sm-10">
                            <input type="time" class="form-control" v-bind:value="time" disabled>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    <button v-on:click="getIdTeaching" type="button" class="btn btn-primary"
                        data-bs-dismiss="modal">Ok</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import $ from 'jquery'
import { formatTime } from '@/util/DateFormatter';
import { Toast } from 'bootstrap'
import { changeToastMessage } from '@/util/ChangeToastMessage';

export default {
    name: "NewRepetitionModal",
    props: ["modalId", "title", "professorName", "courseTitle", "date", "time", "idCourse", "serialNumber", "loggedUser"],
    methods: {
        formatTime,
        changeToastMessage,
        openToast() {
            const toastLiveExample = $("#liveToast")
            const toast = new Toast(toastLiveExample)
            toast.show()
        },
        getIdTeaching() {
            let self = this
            $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet", {
                method: "GET",
                data: {
                    operation: "getIdTeaching",
                    serialNumber: self.serialNumber,
                    idCourse: self.idCourse
                },
                success(data) {
                    self.executeOperation(data.idTeaching)
                }
            })
        },
        executeOperation(idTeaching) {
            let self = this
            $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
                method: "POST",
                data: {
                    operation: "add",
                    email: self.loggedUser.email,
                    idTeaching: idTeaching,
                    date: self.date,
                    time: formatTime(self.time) + ":00"
                },
                success: () => {
                    self.changeToastMessage("Ripetizione prenotata con successo!")
                    self.openToast()
                }
            })
        }
    }
}
</script>