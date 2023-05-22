<template>
  <CustomToast />
  <AddCourseModal v-bind:modalId="'addModal'" @new-course="updateCourses" />
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione corsi</li>
    </ol>
  </nav>

  <div class="row pb-5">
    <div class="col-5">
      <input v-model="searchField" type="text" class="form-control p-2" placeholder="Cerca corso">
    </div>
  </div>



  <button data-bs-toggle="modal" v-bind:data-bs-target="'#addModal'"
    class="admin-button custom-button btn btn-primary rounded-pill mb-3">
    Aggiungi corso
  </button>

  <div v-if="courses === null">
    <LoadingRow />
    <LoadingRow />
    <LoadingRow />
  </div>
  <div v-else>

    <div v-if="courses.length === 0">
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
      courses: null,
      searchField: ""
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
          self.courses = data
        }
      })
    },
    removeCourse(course) {
      for (var i = 0; i < this.courses.length; i++)
        if (this.courses[i] == course)
          this.courses.splice(i, 1)
    },
    updateCourses(course) {
      this.courses.push(course);
    }
  },
  watch: {
    searchField: function (newSearchField) {
      if (newSearchField === "") {
        this.getCourses()
        return;
      }

      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "CourseServlet", {
        method: "GET",
        data: {
          operation: "search",
          searchField: newSearchField
        }, success(data) {
          self.courses = data
        }
      })
    },
  },
  mounted() {
    this.getCourses()
  }
}
</script>