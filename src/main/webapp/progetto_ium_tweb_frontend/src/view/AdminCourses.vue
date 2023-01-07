<template>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione corsi</li>
    </ol>
  </nav>

  <div v-if="courses === null">
    <LoadingRow />
    <LoadingRow />
    <LoadingRow />
  </div>
  <div v-else>
    <div v-if="courses == ''">
      Nessun corso presente
    </div>
    <div v-else>
      <ul class="list-group">
        <div class="course-card" v-for="course in courses" v-bind:key="course.idCourse">
          <CourseItem v-bind:course="course" />
        </div>
      </ul>
    </div>
  </div>
</template>

<script>

import $ from 'jquery'
import LoadingRow from '../components/LoadingRow.vue'
import CourseItem from '../components/CourseItem.vue'

export default {
  name: "AdminCourse",
  data() {
    return {
      courses: null
    }
  },
  components: {
    LoadingRow,
    CourseItem
  },

  methods: {
    getCourses() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "CourseServlet?operation=select", {
        method: "GET",
        success: (data) => {
          setTimeout(() => {
            self.courses = data
          }, 2000)
        }
      })
    }
  },
  mounted() {
    this.getCourses()
  }
}
</script>