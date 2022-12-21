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
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item d-flex flex-column align-items-center">
            <HomeIcon />
            <a class="nav-link" :class="page === 'home' ? 'active' : ''"
              v-on:click="this.$emit('change-page', 'home')">Home</a>
          </li>
          <li class="nav-item d-flex flex-column align-items-center">
            <SearchIcon />
            <a class="nav-link" :class="page === 'search' ? 'active' : ''"
              v-on:click="this.$emit('change-page', 'search')">Search</a>
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

export default {
  name: "VerticalBar",
  components: {
    HomeIcon,
    SearchIcon,
    BookOpenPageVariantOutlineIcon
  },
  props: ["page"]
}
</script>