<template>
  <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">{{ title }}</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          {{ teaching.professor.surname }}
          {{ teaching.professor.name }} con matricola
          {{ teaching.professor.serialNumber }} insegna
          {{ teaching.course.title }}
        </div>
        <div class="modal-footer">
          <button type="button" class="modal-button btn btn-primary rounded-pill mb-3"
            data-bs-dismiss="modal">Chiudi</button>
          <button v-on:click="executeOperation" type="button" class="modal-button btn btn-primary rounded-pill mb-3"
            data-bs-dismiss="modal">Ok</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import { Toast } from "bootstrap"
import { changeToastMessage } from '@/util/ChangeToastMessage'
export default {
  name: "TeachingModal",
  props: ["teaching", "title", "modalId"],
  methods: {
    changeToastMessage,
    openToast() {
      const toastLiveExample = $("#liveToast")
      const toast = new Toast(toastLiveExample)
      this.changeToastMessage("Insegnamento cancellato con successo")
      toast.show()
    },
    executeOperation() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet", {
        method: "POST",
        data: {
          operation: "delete",
          idTeaching: self.teaching.idTeaching
        },
        success() {
          self.$emit("delete-teaching")

          self.openToast()
        }
      })
    }
  }
}
</script>