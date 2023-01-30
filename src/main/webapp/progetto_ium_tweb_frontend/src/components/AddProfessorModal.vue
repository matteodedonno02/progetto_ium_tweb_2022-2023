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
          <div class="row">
            Matricola: <input class="form-control" v-model="serialNumber" type="text" minlength="6" maxlength="6"
              required="true" />
          </div>
          <div class="row">
            Nome: <input class="form-control" v-model="name" type="text" required="true" />
          </div>
          <div class="row">
            Cognome: <input class="form-control" v-model="surname" type="text" required="true" />
          </div>
          <div class="row">
            Immagine del professore: <input class="form-control" v-model="imageUrl" type="text" required="true" />
          </div>


        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" v-on:click="clearInput"
            data-bs-dismiss="modal">Annulla</button>
          <button v-on:click="executeOperation" type="button"
            :disabled="serialNumber.length != 6 || name.length <= 0 || surname.length <= 0 || imageUrl.length <= 0"
            class="btn btn-primary" data-bs-dismiss="modal">Aggiungi</button>
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
  name: "AddProfessorModal",
  props: ['modalId'],
  emits: ["new-professor"],
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
      this.imageUrl = ''
      this.name = ''
      this.surname = ''
    },
    executeOperation() {

      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "ProfessorServlet", {
        method: "POST",
        data: {
          operation: "add",
          serialNumber: self.serialNumber,
          imageUrl: self.imageUrl,
          name: self.name,
          surname: self.surname
        },
        success(data) {
          if (data.error !== undefined) {
            self.openToast("Professore già presente")
          }
          else {
            self.$emit("new-professor", data)
            self.openToast("Professore inserito con successo")
          }
        }
      })

      self.clearInput();


    }

  }
}
</script>