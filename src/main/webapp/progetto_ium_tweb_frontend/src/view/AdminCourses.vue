<template>
  <CustomToast />
  <AddCourseModal v-bind:modalId="'addModal'" />
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione corsi</li>
    </ol>
  </nav>
  <div class="row">
    <p @new-course="updateCourses" data-bs-toggle="modal" v-bind:data-bs-target="'#addModal'">
      Aggiungi corso</p>
  </div>
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
          <CourseItem v-bind:course="course" @delete-course="removeCourse" />

        </div>
      </ul>
    </div>
  </div>
</template>

<script>

import $ from 'jquery'
import LoadingRow from '../components/LoadingRow.vue'
import CourseItem from '../components/CourseItem.vue'
import CustomToast from '@/components/CustomToast.vue'
import AddCourseModal from '@/components/AddCourseModal.vue'

export default {
  name: "AdminCourse",
  data() {
    return {
      courses: null
    }
  },
  components: {
    LoadingRow,
    CourseItem,
    CustomToast,
    AddCourseModal
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
    },
    removeCourse(course) {
      for (var i = 0; i < this.courses.length; i++)
        if (this.courses[i] == course)
          this.courses.splice(i, 1)
    },
    updateCourses(course) {
      this.courses.add(course);
    }
  },
  mounted() {
    this.getCourses()
  }
}
</script>