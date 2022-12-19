<template>
  <nav class="navbar">
    <ul class="nav navbar-nav">
      <li class="nav-item">
        <a class="nav-link" v-on:click="this.$emit('change-page', 'home')">
          <div class="icon">
            <HomeIcon :size="38" />
          </div>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" v-on:click="this.$emit('change-page', 'search')">
          <div class="icon">
            <SearchIcon :size="38" />
          </div>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
import HomeIcon from 'vue-material-design-icons/Home.vue'
import SearchIcon from 'vue-material-design-icons/Magnify.vue'

export default {
  name: "VerticalBar",
  components: {
    HomeIcon,
    SearchIcon
  }
}
</script>