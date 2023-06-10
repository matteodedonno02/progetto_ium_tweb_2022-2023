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
          <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Matricola</label>
            <div class="col-sm-10">
              <input class="form-control" v-model="serialNumber" type="text" minlength="6" maxlength="6"
                required="true" />
            </div>
          </div>
          <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Nome</label>
            <div class="col-sm-10">
              <input class="form-control" v-model="name" type="text" required="true" />
            </div>
          </div>
          <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Cognome</label>
            <div class="col-sm-10">
              <input class="form-control" v-model="surname" type="text" required="true" />
            </div>
          </div>
          <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Immagine</label>
            <div class="col-sm-10">
              <input class="form-control" @change="handleFile" type="file" required="true" accept="image/*" />
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="modal-button btn btn-primary rounded-pill mb-3" v-on:click="clearInput"
            data-bs-dismiss="modal">Annulla</button>
          <button v-on:click="executeOperation" type="button"
            :disabled="serialNumber.length != 6 || name.length <= 0 || surname.length <= 0 || file === null"
            class="modal-button btn btn-primary rounded-pill mb-3" data-bs-dismiss="modal">Aggiungi</button>
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
      imageUrl: 'uygfuwhsduibfoisdhfbsdnflnk',
      name: '',
      surname: '',
      file: null,
    };
  },
  methods: {
    changeToastMessage,
    handleFile(e) {
      this.file = e.target.files[0]
    },
    openToast(toastMessage) {
      const toastLiveExample = $("#liveToast")
      const toast = new Toast(toastLiveExample)
      this.changeToastMessage(toastMessage)
      toast.show()
    },
    clearInput() {
      this.serialNumber = ''
      this.name = ''
      this.surname = ''
      this.file = null
    },
    executeOperation() {
      let self = this
      let reader = new FileReader()
      reader.readAsDataURL(this.file)
      reader.onload = () => {
        self.file = reader.result
        self.executeCall()
      }
    },
    executeCall() {
      let self = this
      $.ajax("ProfessorServlet", {
        method: "POST",
        data: {
          operation: "add",
          serialNumber: self.serialNumber,
          name: self.name,
          surname: self.surname,
          file: self.file
        },
        success(data) {
          if (data.error !== undefined) {
            self.openToast("Professore già presente")
          }
          else {
            self.$emit("new-professor", data)
            self.openToast("Professore inserito con successo")
          }
          self.clearInput()
        }
      })
    }
  }
}
</script>