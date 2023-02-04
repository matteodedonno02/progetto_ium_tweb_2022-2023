<template>
  <LoginPage v-if="loggedUser === null" @set-logged-user="setLoggedUser" />
  <div v-else>
    <VerticalBar v-bind:loggedUser="loggedUser" v-bind:page="page" @change-page="changePage" />
    <div class="container">
      <div class="container-content pt-5">
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
        <div v-else-if="page === 'admin-repetitions'">
          <AdminRepetitions />
        </div>
      </div>
    </div>
  </div>

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
import AdminRepetitions from './view/AdminRepetitions.vue'
import LoginPage from './view/LoginPage.vue'

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
    AdminTeachings,
    AdminRepetitions,
    LoginPage
  },
  methods: {
    changePage(page) {
      this.page = page
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
    },
    setLoggedUser(loggedUser) {
      this.loggedUser = loggedUser
    }
  },
  mounted() {
    this.getUserFromSession()
  }
}
</script>
