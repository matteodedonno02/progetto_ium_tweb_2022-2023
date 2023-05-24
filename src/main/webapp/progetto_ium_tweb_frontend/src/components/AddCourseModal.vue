<template>
  <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Aggiungi un nuovo corso</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" v-on:click="clearInput"
            aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Titolo</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" v-model="titleCourse" required="true" />
            </div>
          </div>
          <div class="mb-3 row">
            <label class="col-sm-2 col-form-label">Icona</label>
            <div class="col-sm-10">
              <input class="form-control" @change="handleFile" type="file" required="true" />
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button v-on:click="clearInput" data-bs-dismiss="modal" class="modal-button btn btn-primary rounded-pill mb-3">
            Annulla
          </button>
          <button v-on:click="executeOperation" :disabled="titleCourse.length <= 0 || file === null" type="button"
            class="modal-button btn btn-primary rounded-pill mb-3" data-bs-dismiss="modal">Aggiungi
          </button>
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
  name: "AddCourseModal",
  props: ['modalId'],
  emits: ["new-course"],
  data() {
    return {
      titleCourse: '',
      iconUrl: '',
      file: null
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
      this.changeToastMessage(toastMessage);
      toast.show()
    },
    clearInput() {
      this.titleCourse = ''
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
      $.ajax(process.env.VUE_APP_BASE_URL + "CourseServlet", {
        method: "POST",
        data: {
          operation: "add",
          title: self.titleCourse,
          file: self.file
        },
        success(data) {
          if (data.error !== undefined) {
            self.openToast("Corso già presente")
          }
          else {
            self.$emit("new-course", data)
            self.openToast("Corso aggiunto con successo")
          }

          self.clearInput();
        }
      })
    }
  }
}
</script>