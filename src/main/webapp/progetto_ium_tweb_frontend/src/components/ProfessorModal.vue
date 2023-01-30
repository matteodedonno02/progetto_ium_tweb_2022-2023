<template>
  <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">{{ title }}</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          {{ professor.surname }}
          {{ professor.name }}
          {{ professor.serialNumber }}
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
          <button v-on:click="executeOperation" type="button" class="btn btn-primary"
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
  name: "ProfessorModal",
  props: ["professor", "title", "modalId"],
  methods: {
    changeToastMessage,
    openToast() {
      const toastLiveExample = $("#liveToast")
      const toast = new Toast(toastLiveExample)
      this.changeToastMessage("Professore cancellato con successo")
      toast.show()
    },
    executeOperation() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "ProfessorServlet", {
        method: "POST",
        data: {
          operation: "delete",
          serialNumber: self.professor.serialNumber
        },
        success() {
          self.$emit("delete-professor")

          self.openToast()
        }
      })
    }
  }
}
</script>