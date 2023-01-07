<template>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione insegnamenti</li>
    </ol>
  </nav>

  <div v-if="teachings === null">
    <LoadingRow />
    <LoadingRow />
    <LoadingRow />
  </div>
  <div v-else>
    <div v-if="teachings == ''">
      Nessun insegnamento presente
    </div>
    <div v-else>
      <h2>FA SCHIFO POI LO SISTEMO</h2>
      <ul class="list-group">
        <div class="course-card" v-for="teaching in teachings" v-bind:key="teaching.idTeaching">
          <TeachingItem v-bind:teaching="teaching" />
        </div>
      </ul>
    </div>
  </div>


</template>

<script>

import $ from 'jquery'
import LoadingRow from '../components/LoadingRow.vue'
import TeachingItem from '../components/TeachingItem.vue'

export default {
  name: "AdminTeachings",
  data() {
    return {
      teachings: null
    }
  },
  components: {
    LoadingRow,
    TeachingItem
  },

  methods: {
    getTeachings() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet?operation=select", {
        method: "GET",
        success: (data) => {
          setTimeout(() => {
            self.teachings = data
          }, 2000)
        }
      })
    }
  },
  mounted() {
    this.getTeachings()
  }
}
</script>