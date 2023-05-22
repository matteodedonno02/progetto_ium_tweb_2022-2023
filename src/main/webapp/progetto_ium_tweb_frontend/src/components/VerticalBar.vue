<template>
  <nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
      <a class="navbar-brand">
        <BookOpenPageVariantOutlineIcon :size="38" />
      </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-between" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item ">

            <a class="nav-link"
              :class="page === 'home' ? 'active d-flex flex-column align-items-center' : 'd-flex flex-column align-items-center'"
              v-on:click="this.$emit('change-page', 'home')">
              <HomeIcon />
              Home
            </a>
          </li>
          <li v-if="loggedUser !== null" class="nav-item">
            <a class="nav-link"
              :class="page === 'search' ? 'active d-flex flex-column align-items-center' : 'd-flex flex-column align-items-center'"
              v-on:click="this.$emit('change-page', 'search')">
              <SearchIcon />
              Cerca
            </a>
          </li>
          <li v-if="loggedUser !== null" class="nav-item">
            <a class="nav-link"
              :class="page === 'my-repetitions' ? 'active d-flex flex-column align-items-center' : 'd-flex flex-column align-items-center'"
              v-on:click="this.$emit('change-page', 'my-repetitions')">
              <BookAlertIcon />
              Le mie ripetizioni
            </a>
          </li>
          <li v-if="loggedUser !== null && loggedUser.role" class="nav-item">
            <a class="nav-link"
              :class="page === 'admin-courses' ? 'active d-flex flex-column align-items-center' : 'd-flex flex-column align-items-center'"
              v-on:click="this.$emit('change-page', 'admin-courses')">
              <BookOpenVariant />
              Gestione corsi
            </a>
          </li>
          <li v-if="loggedUser !== null && loggedUser.role" class="nav-item">
            <a class="nav-link"
              :class="page === 'admin-professors' ? 'active d-flex flex-column align-items-center' : 'd-flex flex-column align-items-center'"
              v-on:click="this.$emit('change-page', 'admin-professors')">
              <AccountTie />
              Gestione professori
            </a>
          </li>
          <li v-if="loggedUser !== null && loggedUser.role" class="nav-item">
            <a class="nav-link"
              :class="page === 'admin-teachings' ? 'active d-flex flex-column align-items-center' : 'd-flex flex-column align-items-center'"
              v-on:click="this.$emit('change-page', 'admin-teachings')">
              <AccountDetails />
              Gestione insegnamenti
            </a>
          </li>
          <li v-if="loggedUser !== null && loggedUser.role" class="nav-item">
            <a class="nav-link"
              :class="page === 'admin-repetitions' ? 'active d-flex flex-column align-items-center' : 'd-flex flex-column align-items-center'"
              v-on:click="this.$emit('change-page', 'admin-repetitions')">
              <AccountDetails />
              Tutte le ripetizioni
            </a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li>
            <a v-on:click="this.$emit('log-out')" class="nav-link d-flex flex-column align-items-center">
              <MdiLogoutIcon />
              Log Out
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import HomeIcon from 'vue-material-design-icons/Home.vue'
import SearchIcon from 'vue-material-design-icons/Magnify.vue'
import BookOpenPageVariantOutlineIcon from 'vue-material-design-icons/BookOpenPageVariantOutline.vue'
import BookAlertIcon from 'vue-material-design-icons/BookAlert.vue'
import BookOpenVariant from 'vue-material-design-icons/BookOpenVariant.vue'
import AccountDetails from 'vue-material-design-icons/AccountDetails.vue'
import AccountTie from 'vue-material-design-icons/AccountTie.vue'
import MdiLogoutIcon from 'vue-material-design-icons/Logout.vue'

export default {
  name: "VerticalBar",
  components: {
    HomeIcon,
    SearchIcon,
    BookOpenPageVariantOutlineIcon,
    BookAlertIcon,
    BookOpenVariant,
    AccountDetails,
    AccountTie,
    MdiLogoutIcon
  },
  props: ["page", "loggedUser"]
}
</script>