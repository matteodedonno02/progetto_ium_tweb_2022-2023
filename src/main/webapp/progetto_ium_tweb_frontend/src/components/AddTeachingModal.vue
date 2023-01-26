<template>
  <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Aggiungi un nuovo insegnante</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" v-on:click="clearInput"
            aria-label="Close"></button>
        </div>
        <div class="modal-body">




        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" v-on:click="clearInput"
            data-bs-dismiss="modal">Annulla</button>
          <button v-on:click="executeOperation" type="button" :disabled="imageUrl.length <= 0" class="btn btn-primary"
            data-bs-dismiss="modal">Aggiungi</button>
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
  name: "AddTeachingModal",
  props: ['modalId'],
  emits: ["new-teaching"],
  data() {
    return {
      serialNumber: '',
      imageUrl: '',
      name: '',
      surname: ''
    };
  },
  methods: {
    changeToastMessage,

    openToast(toastMessage) {
      const toastLiveExample = $("#liveToast")
      const toast = new Toast(toastLiveExample)
      this.changeToastMessage(toastMessage);
      toast.show()
    },
    clearInput() {
      this.serialNumber = ''
      this.idCourse = ''

    },
    executeOperation() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet", {
        method: "POST",
        data: {
          operation: "add",
          serialNumber: self.serialNumber,
          idCourse: self.idCourse
        },
        xhrFields: {
          withCredentials: true
        },
        crossDomain: true,
        success(data) {
          if (data.error !== undefined) {
            self.openToast("Professore già presente")
          }
          else {
            self.$emit("new-professore", data)
            self.openToast("Professore inserito con successo")
          }
        }
      })

      self.clearInput();
    }

  }
}
</script>