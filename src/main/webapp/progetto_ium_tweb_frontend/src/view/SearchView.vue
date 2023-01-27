<template>
  <CustomToast />
  <NewRepetitionModal modalId="newRepetition" title="Vuoi prenotare la seguente ripetizione?"
    :professorName="professorNameForModal" :courseTitle="courseTitleForModal" :date="dateForModal"
    :time="timeForModal" />

  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Cerca</li>
    </ol>
  </nav>

  <div class="row pb-5">
    <div class="col">
      <select id="selectCourses" v-on:change="coursesChanged" v-model="selectedCourse" class="form-select"
        aria-label="Select della materia">
        <option selected value="default">Seleziona materia</option>
        <option v-for="course in courses" :value="course.idCourse" :key="course.idCourse">{{ course.title }}</option>
      </select>
    </div>
    <div class="col">
      <select id="selectProfessors" v-on:change="professorChange" v-model="selectedProfessor" class="form-select"
        aria-label="Select del professore">
        <option selected value="default">Seleziona professore</option>
        <option v-for="professor in professors" :value="professor.serialNumber" :key="professor.serialNumber">{{
          professor.name
        }} {{ professor.surname }}</option>
      </select>
    </div>
  </div>

  <vue-cal v-on:view-change="dateChanged" :events="events" :on-event-click="onEventClick" :timeCellHeight="100"
    locale="it" active-view="day" :time-from="15 * 60" :time-to="19 * 60" hide-weekends
    :disable-views="['years', 'year', 'month', 'week']" />
</template>

<script>
import $ from 'jquery'
import { formatDate, fromDateToString, formatTime } from '../util/DateFormatter'
import CustomToast from '../components/CustomToast.vue'
import NewRepetitionModal from '../components/NewRepetitionModal.vue'
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import { Modal } from 'bootstrap'


export default {
  name: "SearchView",
  data() {
    return {
      userRepetitions: null,
      professors: [],
      courses: [],
      selectedCourse: "default",
      selectedProfessor: "default",
      date: null,
      events: [],
      professorNameForModal: "",
      courseTitleForModal: "",
      dateForModal: "",
      timeForModal: ""
    }
  },
  props: ["loggedUser"],
  components: {
    CustomToast,
    VueCal,
    NewRepetitionModal,
  },
  methods: {
    formatDate,
    fromDateToString,
    repetitionExists(list, time, serialNumber, idCourse) {
      return list.some((repetition) => {
        return formatTime(repetition.time) === time + ":00"
          && repetition.teaching.professor.serialNumber === serialNumber
          && repetition.teaching.course.idCourse === idCourse
      })
    },
    generateNewRepetitions(existingRepetition) {
      this.events = []
      if (this.selectedCourse !== "default" && this.selectedProfessor === "default") {
        for (let i = 15; i <= 18; i++) {
          this.professors.forEach((professor) => {
            if (!this.repetitionExists(existingRepetition, i, professor.serialNumber, this.selectedCourse)) {
              this.events.push({
                start: this.date + " " + i + ":00",
                end: this.date + " " + parseInt((i + 1)) + ":00",
                title: this.getSelectedCourseText(),
                content: professor.name + " " + professor.surname,
                class: "materia"
              })
            }
          })
        }
      } else if (this.selectedCourse === "default" && this.selectedProfessor !== "default") {
        for (let i = 15; i <= 18; i++) {
          this.courses.forEach((course) => {
            if (!this.repetitionExists(existingRepetition, i, this.selectedProfessor, course.idCourse)) {
              this.events.push({
                start: this.date + " " + i + ":00",
                end: this.date + " " + parseInt((i + 1)) + ":00",
                title: course.title,
                content: this.getSelectedProfessorText(),
                class: "materia"
              })
            }
          })
        }
      } else if (this.selectedCourse !== "default" && this.selectedProfessor !== "default") {
        for (let i = 15; i <= 18; i++) {
          if (!this.repetitionExists(existingRepetition, i, this.selectedProfessor, this.selectedCourse)) {
            this.events.push({
              start: this.date + " " + i + ":00",
              end: this.date + " " + parseInt((i + 1)) + ":00",
              title: this.getSelectedCourseText(),
              content: this.getSelectedProfessorText(),
              class: "materia"
            })
          }
        }
      }
    },
    getExistingRepetition() {
      if (this.date === null) {
        this.date = fromDateToString(new Date())
      }

      let operation = ""
      if (this.selectedProfessor === "default" && this.selectedCourse !== "default") {
        operation = "selectByCourseAndDate"
      } else if (this.selectedProfessor !== "default" && this.selectedCourse === "default") {
        operation = "selectByProfessorAndDate"
      } else if (this.selectedProfessor !== "default" && this.selectedCourse !== "default") {
        operation = "selectByProfessorCourseAndDate"
      } else {
        this.events = []
        return;
      }

      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
        method: "GET",
        data: {
          operation: operation,
          idCourse: self.selectedCourse,
          serialNumber: self.selectedProfessor,
          date: self.date
        },
        success(data) {
          self.generateNewRepetitions(data)
        }
      })
    },
    getUserRepetitions() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
        method: "GET",
        data: {
          operation: "selectByEmail",
          email: self.loggedUser.email
        },
      })
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
        }
      })
    },
    coursesChanged(e) {
      let self = this
      const idCourse = e.target.value
      if (idCourse === "default") {
        self.getProfessors()
        self.getExistingRepetition()
        return;
      }

      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet", {
        method: "GET",
        data: {
          operation: "selectByCourse",
          idCourse: idCourse
        },
        success: (data) => {
          self.professors = []
          data.forEach((teaching) => {
            self.professors.push(teaching.professor)
          })

          self.getExistingRepetition()
        }
      })
    },
    professorChange(e) {
      let self = this
      const serialNumber = e.target.value
      if (serialNumber === "default") {
        self.getCourses()
        self.getExistingRepetition()
        return;
      }

      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet", {
        method: "GET",
        data: {
          operation: "selectByProfessor",
          serialNumber: serialNumber
        },
        success: (data) => {
          self.courses = []
          data.forEach((teaching) => {
            self.courses.push(teaching.course)
          })

          self.getExistingRepetition()
        }
      })
    },
    dateChanged({ startDate }) {
      this.date = fromDateToString(startDate)
      if (this.selectedCourse !== "default") {
        this.getExistingRepetition()
      }
    },
    getSelectedCourseText() {
      const select = document.getElementById("selectCourses")
      return select.options[select.selectedIndex].text
    },
    getSelectedProfessorText() {
      const select = document.getElementById("selectProfessors")
      return select.options[select.selectedIndex].text
    },
    onEventClick(event, e) {
      this.professorNameForModal = event.content
      this.courseTitleForModal = event.title
      this.dateForModal = fromDateToString(event.start)
      this.timeForModal = event.start.getHours() + ":00"
      console.log(this.timeForModal)
      const modal = new Modal($("#newRepetition"))
      modal.show()
      // console.log(event)

      // Prevent navigating to narrower view (default vue-cal behavior).
      e.stopPropagation()
    }
  },
  mounted() {
    this.getProfessors()
    this.getCourses()
  }
}
</script>