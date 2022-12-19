<template>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Home</li>
    </ol>
  </nav>


  <div v-for="course in mostRequestedCourse" :key="course.idCourse">
    {{ course }}
  </div>
</template>

<script>
import $ from 'jquery'

export default {
  name: "HomeView",
  data() {
    return {
      mostRequestedCourse: []
    }
  },
  mounted() {
    let self = this
    $.ajax(process.env.VUE_APP_BASE_URL + "/rungo?operation=mostRequested", {
      method: "GET",
      success: (data) => {
        self.mostRequestedCourse = data
      }
    })
  }
}
</script>