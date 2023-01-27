<template>
  <CustomToast />

  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Cerca</li>
    </ol>
  </nav>

  <div class="row pb-5">
    <div class="col">
      <select v-on:change="coursesChanged" class="form-select" aria-label="Select della materia">
        <option selected value="default">Seleziona materia</option>
        <option v-for="course in courses" :value="course.idCourse" :key="course.idCourse">{{ course.title }}</option>
      </select>
    </div>
    <div class="col">
      <select v-on:change="professorChange" class="form-select" aria-label="Select del professore">
        <option selected value="default">Seleziona professore</option>
        <option v-for="professor in professors" :value="professor.serialNumber" :key="professor.serialNumber">{{
          professor.name
        }} {{ professor.surname }}</option>
      </select>
    </div>
  </div>

  <vue-cal v-on:view-change="dateChanged" :events="events" locale="it" active-view="day" :time-from="14 * 60"
    :time-to="19 * 60" hide-weekends :disable-views="['years', 'year', 'month', 'week']" />
</template>

<script>
import $ from 'jquery'
import { formatDate, fromDateToString } from '../util/DateFormatter'
import CustomToast from '../components/CustomToast.vue'
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'


export default {
  name: "SearchView",
  data() {
    return {
      userRepetitions: null,
      professors: [],
      courses: [],

      events: [
        {
          start: '2023-01-27 16:00',
          end: '2023-01-27 17:00',
          // You can also define event dates with Javascript Date objects:
          // start: new Date(2018, 11 - 1, 16, 10, 30),
          // end: new Date(2018, 11 - 1, 16, 11, 30),
          title: 'Doctor appointment',
          content: 'Me gusta',
          class: 'materia'
        }
      ]
    }
  },
  props: ["loggedUser"],
  components: {
    CustomToast,
    VueCal
  },
  methods: {
    formatDate,
    fromDateToString,
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
            this.professors.push(teaching.professor)
          })
        }
      })
    },
    professorChange(e) {
      let self = this
      const serialNumber = e.target.value
      if (serialNumber === "default") {
        self.getCourses()
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
            this.courses.push(teaching.course)
          })
        }
      })
    },
    dateChanged({ startDate }) {
      console.log(fromDateToString(startDate))
    },
    getUserRepetition() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
        method: "GET",
        data: {
          operation: "selectByEmail",
          email: self.loggedUser.email,
        },
        success: (data) => {
          self.userRepetitions = data
        }
      })
    }
  },
  mounted() {
    this.getProfessors()
    this.getCourses()
    this.getUserRepetition()
  }
}
</script>