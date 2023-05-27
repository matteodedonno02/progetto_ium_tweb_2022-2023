<template>
  <div v-bind:id="modalId" class="modal fade" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Aggiungi un nuovo insegnamento</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" v-on:click="clearInput"
            aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="row pb-5">

            <div class="col">
              <select v-model="selectedProfessor" class="form-select" aria-label="Default select example">
                <option value="default">Seleziona professore</option>
                <option v-for="professor in professors" :value="professor.serialNumber" :key="professor.serialNumber">{{
                  professor.name
                }} {{ professor.surname }}</option>
              </select>
            </div>
            <div class="col">
              <select v-model="selectedCourse" :disabled="selectedProfessor === 'default'" class="form-select"
                aria-label="Default select example">
                <option value="default">Seleziona materia</option>
                <option v-for="course in courses" :value="course.idCourse" :key="course.idCourse">{{ course.title }}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="modal-button btn btn-primary rounded-pill mb-3" v-on:click="clearInput"
            data-bs-dismiss="modal">Annulla</button>
          <button v-on:click="executeOperation" :disabled="selectedCourse === 'default'" type="button"
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
  name: "AddTeachingModal",
  props: ['modalId'],
  emits: ["new-teaching"],
  data() {
    return {
      professors: [],
      courses: [],
      selectedCourse: 'default',
      selectedProfessor: 'default'
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
      if (this.selectedProfessor !== 'default') {
        this.selectedProfessor = 'default'
        this.getProfessors()

        if (this.selectedCourse !== 'default') {
          this.selectedCourse = 'default'
          this.getCourses()
        }
      }
    },
    executeOperation() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet", {
        method: "POST",
        data: {
          operation: "add",
          serialNumber: self.selectedProfessor,
          idCourse: self.selectedCourse
        },
        success(data) {
          console.log(data)
          self.$emit("new-teaching", data)
          self.openToast("Insegnamento inserito con successo")
        }
      })

      self.clearInput();
    },
    getProfessors() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "ProfessorServlet", {
        method: "GET",
        data: {
          operation: "select"
        },
        success: (data) => {
          self.professors = data
          self.getCourses()
        }
      })
    },
    getCourses() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "CourseServlet", {
        method: "GET",
        data: {
          operation: "select"
        },
        success: (data) => {
          self.courses = data
          console.log(self.courses)
        }
      })
    },
    professorChange() {
      let self = this

      if (this.selectedProfessor === "default") {
        self.getCourses()
        return;
      }

      $.ajax(process.env.VUE_APP_BASE_URL + "CourseServlet", {
        method: "GET",
        data: {
          operation: "newTeaching",
          serialNumber: self.selectedProfessor
        },
        success: (data) => {
          self.courses = data
        }
      })
    }
  },
  mounted() {
    this.getProfessors()

  }
}
</script>