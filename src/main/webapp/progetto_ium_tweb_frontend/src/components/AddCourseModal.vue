<template>
  <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Crea un nuovo corso</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" v-on:click="clearInput"
            aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="row">
            Titolo del corso:
            <input type="text" class="form-control" v-model="titleCourse" required="true" />
          </div>
          <div class="row">
            Immagine del corso: <input class="form-control" v-model="iconUrl" type="text" required="true" />
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" v-on:click="clearInput"
            data-bs-dismiss="modal">Annulla</button>
          <button v-on:click="executeOperation" :disabled="titleCourse.length <= 0 || iconUrl.length <= 0" type="button"
            class="btn btn-primary" data-bs-dismiss="modal">Aggiungi
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
      this.titleCourse = ''
      this.iconUrl = ''
    },
    executeOperation() {
      let self = this
      self.clearInput();
      $.ajax(process.env.VUE_APP_BASE_URL + "CourseServlet", {
        method: "POST",
        data: {
          operation: "add",
          title: self.titleCourse,
          iconUrl: self.iconUrl
        },
        xhrFields: {
          withCredentials: true
        },
        crossDomain: true,
        success(data) {
          if (data.error !== undefined) {
            self.openToast("Corso già presente")
          }
          else {
            self.$emit("new-course", data)
            self.openToast("Corso aggiunto con successo")
          }
        }
      })
    }
  }
}
</script>