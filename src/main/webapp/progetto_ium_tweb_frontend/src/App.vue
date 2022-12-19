<template>
  <div class="row">
    <div class="col-1">
      <VerticalBar @change-page="changePage" />
    </div>
    <div class="col-11">
      <div v-if="page === 'home'">
        <HomeView />
      </div>
      <div v-else-if="page === 'search'">
        <SearchView />
      </div>
    </div>
  </div>

</template>

<script>
import VerticalBar from './components/VerticalBar.vue'
import HomeView from './view/HomeView.vue'
import SearchView from './view/SearchView.vue'

export default {
  name: 'App',
  data() {
    return {
      page: "home"
    }
  },
  components: {
    VerticalBar,
    HomeView,
    SearchView
  },
  methods: {
    changePage(page) {
      this.page = page
    }
  },
  mounted() {
  }
}
</script>
