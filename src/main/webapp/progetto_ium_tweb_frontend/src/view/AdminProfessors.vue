<template>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione professori</li>
    </ol>
  </nav>

  <div v-if="professors.length === 0" class="cards">
    <LoadingRow />
    <LoadingRow />
    <LoadingRow />

  </div>
  <div v-else>

    <ul class="list-group">
      <div class="course-card" v-for="professor in professors" v-bind:key="professor.serialNumber">
        <ProfessorItem v-bind:professor="professor" />
      </div>
    </ul>
  </div>

</template>

<script>

import $ from 'jquery'
import LoadingRow from '../components/LoadingRow.vue'
import ProfessorItem from '../components/ProfessorItem.vue'

export default {
  name: "AdminProfessors",
  data() {
    return {
      professors: []
    }
  },
  components: {
    LoadingRow,
    ProfessorItem
  },
  methods: {
    getProfessors() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "ProfessorServlet?operation=select", {
        method: "GET",
        success: (data) => {
          setTimeout(() => {
            self.courses = data
          }, 2000)
        }
      })
    }
  },
  mounted() {
    this.getProfessors()
  }
}
</script>