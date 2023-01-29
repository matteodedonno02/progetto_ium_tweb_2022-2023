<template>
  <VerticalBar v-bind:loggedUser="loggedUser" v-bind:page="page" @change-page="changePage" />
  <div class="container">
    <div class="container-content">
      <div v-if="page === 'home'">
        <HomeView v-bind:loggedUser="loggedUser" />
      </div>
      <div v-else-if="page === 'search'">
        <SearchView v-bind:loggedUser="loggedUser" @change-page="changePage" />
      </div>
      <div v-else-if="page === 'my-repetitions'">
        <MyRepetitions v-bind:loggedUserEmail="loggedUser.email" />
      </div>
      <div v-else-if="page === 'admin-courses'">
        <AdminCourses />
      </div>
      <div v-else-if="page === 'admin-professors'">
        <AdminProfessors />
      </div>
      <div v-else-if="page === 'admin-teachings'">
        <AdminTeachings />
      </div>
    </div>
  </div>


  <button v-on:click="login">LOGIN</button>

</template>

<script>
import $ from 'jquery'
import VerticalBar from './components/VerticalBar.vue'
import HomeView from './view/HomeView.vue'
import SearchView from './view/SearchView.vue'
import MyRepetitions from './view/MyRepetitions.vue'
import AdminCourses from './view/AdminCourses.vue'
import AdminProfessors from './view/AdminProfessors.vue'
import AdminTeachings from './view/AdminTeachings.vue'

export default {
  name: 'App',
  data() {
    return {
      page: "home",
      loggedUser: null,
      userRepetitions: [],
    }
  },
  components: {
    VerticalBar,
    HomeView,
    SearchView,
    MyRepetitions,
    AdminCourses,
    AdminProfessors,
    AdminTeachings
  },
  methods: {
    changePage(page) {
      this.page = page
    },
    login() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "UserServlet", {
        method: "POST",
        data: {
          operation: "login",
          email: "matteodedonno02@gmail.com",
          password: "matteo"
        },
        xhrFields: {
          withCredentials: true
        },
        crossDomain: true,
        success: (data) => {
          console.log(data)
          self.loggedUser = data
        }
      })
    },
    getUserFromSession() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "UserServlet", {
        method: "POST",
        data: {
          operation: "getFromSession"
        },
        success: (data) => {
          if (data !== null) {
            self.loggedUser = data
          }
        }
      })
    }
  },
  mounted() {
    this.getUserFromSession()
  }
}
</script>
