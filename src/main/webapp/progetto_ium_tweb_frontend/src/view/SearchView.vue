<template>
  <CustomToast />
  <NewRepetitionModal modalId="newRepetition" :title="titleForModal" :event="eventForModal" :loggedUser="loggedUser"
    @change-page="this.$emit('change-page', 'my-repetitions')" />

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

  <vue-cal :selected-date="minDate" v-on:view-change="dateChanged" :events="events" :on-event-click="onEventClick"
    :timeCellHeight="100" locale="it" active-view="day" :time-from="15 * 60" :time-to="19 * 60" hide-weekends
    :disable-views="['years', 'year', 'month', 'week']" />
</template>

<script>
import $ from 'jquery'
import { formatDate, fromDateToString, formatTime, sumHours } from '../util/DateFormatter'
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
      eventForModal: {
        professor: {
          name: "default",
          surname: "default",
        },
        course: {
          title: "default",
        },
        start: new Date()
      },
      titleForModal: "Vuoi prenotare la seguente ripetizione?"
    }
  },
  props: ["loggedUser"],
  components: {
    CustomToast,
    VueCal,
    NewRepetitionModal,
  },
  emits: ["change-page"],
  methods: {
    formatDate,
    fromDateToString,
    sumHours,
    repetitionExists(list, time, serialNumber, idCourse) {
      return list.some((repetition) => {
        return formatTime(repetition.time) === time + ":00"
          && repetition.teaching.professor.serialNumber === serialNumber
          && repetition.teaching.course.idCourse === idCourse
      })
    },
    pushRepetition(repetition) {
      this.events.push({
        start: this.date + " " + formatTime(repetition.time),
        end: this.date + " " + sumHours(formatTime(repetition.time)),
        title: repetition.teaching.course.title,
        content: repetition.teaching.professor.name + " " + repetition.teaching.professor.surname,
        class: "materia",
        course: repetition.teaching.course,
        professor: repetition.teaching.professor
      })
    },
    generateNewRepetitions() {
      this.events = []
      let self = this
      $.ajax("RepetitionServlet", {
        method: "POST",
        data: {
          operation: "newRepetitions",
          idCourse: self.selectedCourse === "default" ? "-1" : self.selectedCourse,
          serialNumber: self.selectedProfessor === "default" ? "" : self.selectedProfessor,
          date: self.date
        },
        success(data) {
          let currentDate = new Date()
          let currentHour = currentDate.getHours()
          currentDate.setHours(0, 0, 0, 0)

          data.forEach((repetition) => {
            let repetitionHour = formatTime(repetition.time).split(":")[0]
            let repetitionDate = new Date(repetition.date)
            repetitionDate.setHours(0, 0, 0, 0)

            if (repetitionHour > currentHour && currentDate.getTime() === repetitionDate.getTime()) {
              self.pushRepetition(repetition)
            } else if (currentDate.getTime() !== repetitionDate.getTime()) {
              self.pushRepetition(repetition)
            }
          })

          self.getUserRepetitions()
        }
      })
    },
    getUserRepetitions() {
      let self = this
      $.ajax("RepetitionServlet", {
        method: "GET",
        data: {
          operation: "selectByEmailAndDate",
          email: self.loggedUser.email,
          date: self.date
        },
        success(data) {
          self.events.forEach((event) => {
            if (data.find((r) => formatTime(r.time) === event.start.split(" ")[1]) !== undefined) {
              event.class = "materia occupied"
            }
          })
        }
      })
    },
    getProfessors() {
      let self = this
      $.ajax("ProfessorServlet", {
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
      $.ajax("CourseServlet", {
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
        self.generateNewRepetitions()
        return;
      }

      $.ajax("TeachingServlet", {
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

          self.generateNewRepetitions()
        }
      })
    },
    professorChange(e) {
      let self = this
      const serialNumber = e.target.value
      if (serialNumber === "default") {
        self.getCourses()
        self.generateNewRepetitions()
        return;
      }

      $.ajax("TeachingServlet", {
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

          self.generateNewRepetitions()
        }
      })
    },
    dateChanged({ startDate }) {
      this.date = fromDateToString(startDate)

      const tempDate = new Date(startDate.setHours(0, 0, 0, 0))
      const tempDate2 = new Date(this.minDate.setHours(0, 0, 0, 0))

      tempDate.getTime() !== tempDate2.getTime() ?
        $(".vuecal__arrow--prev").addClass("vuecal__arrow--prev-visible") :
        $(".vuecal__arrow--prev").removeClass("vuecal__arrow--prev-visible")

      this.generateNewRepetitions()
    },
    onEventClick(event, e) {
      if (event.class === "materia occupied") {
        return;
      }

      this.eventForModal = event

      const modal = new Modal($("#newRepetition"))
      modal.show()
      e.stopPropagation()
    },
    getCourseByIdCourse(idCourse) {
      return this.courses.find((course) => course.idCourse === idCourse)
    },
    getProfessorBySerialNumber(serialNumber) {
      return this.professors.find((professor) => professor.serialNumber === serialNumber)
    }
  },
  computed: {
    minDate() {
      const currentDate = new Date()

      if (currentDate.toLocaleDateString("it-IT", { weekday: 'long' }) === "sabato") {
        return currentDate.addDays(2)
      } else if (currentDate.toLocaleDateString("it-IT", { weekday: 'long' }) === "domenica") {
        return currentDate.addDays(3)
      }

      return currentDate
    }
  },
  mounted() {
    this.getCourses()
    this.getProfessors()
    this.date = fromDateToString(this.minDate)
  }
}
</script>