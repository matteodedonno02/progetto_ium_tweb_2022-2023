<template>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Home</li>
    </ol>
  </nav>


  <h2>Corsi più richiesti!</h2>

  <div v-if="mostRequestedCourse.length === 0" class="cards">
    <LoadingCard class="course-card" />
    <LoadingCard class="course-card" />
    <LoadingCard class="course-card" />
    <LoadingCard class="course-card" />
  </div>
  <div class="cards" v-else>
    <div class="course-card" v-for="course in mostRequestedCourse" :key="course.idCourse">
      <CourseCard v-bind:loggedUser="loggedUser" v-bind:iconUrl="course.iconUrl" v-bind:title="course.title" />
    </div>
  </div>

  <h2 class="pt-5">Docenti più richiesti!</h2>

  <div v-if="mostRequestedCourse.length === 0" class="cards">
    <LoadingCard class="course-card" />
    <LoadingCard class="course-card" />
    <LoadingCard class="course-card" />
    <LoadingCard class="course-card" />
  </div>
  <div class="cards">
    <div class="professor-card" v-for="professor in mostRequestedProfessor" :key="professor.serialNumber">
      <ProfessorCard v-bind:serialNumber="professor.serialNumber" v-bind:imageUrl="professor.imageUrl"
        v-bind:title="professor.name + ' ' + professor.surname" />
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import CourseCard from '../components/CourseCard.vue'
import LoadingCard from '../components/LoadingCard.vue'
import ProfessorCard from '../components/ProfessorCard.vue'

export default {
  name: "HomeView",
  data() {
    return {
      mostRequestedCourse: [],
      mostRequestedProfessor: []
    }
  },
  props: ["loggedUser"],
  components: {
    CourseCard,
    LoadingCard,
    ProfessorCard
  },
  methods: {
    getMostRequestedCourse() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "CourseServlet?operation=mostRequested", {
        method: "GET",
        success: (data) => {
          setTimeout(() => {
            self.mostRequestedCourse = data
          }, 2000)
        }
      })
    },
    getMostRequestedProfessor() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "ProfessorServlet?operation=mostRequested", {
        method: "GET",
        success: (data) => {
          setTimeout(() => {
            self.mostRequestedProfessor = data
          }, 2000)
        }
      })
    }
  },
  mounted() {
    // this.getMostRequestedCourse()
    // this.getMostRequestedProfessor()
  }
}
</script>