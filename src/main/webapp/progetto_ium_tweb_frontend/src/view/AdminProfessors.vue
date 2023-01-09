<template>

  <CustomToast />
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione professori</li>
    </ol>
  </nav>

  <div v-if="professors === null">
    <LoadingRow />
    <LoadingRow />
    <LoadingRow />

  </div>
  <div v-else>
    <div v-if="professors == ''">
      Nessun professore presente
    </div>
    <div v-else>
      <ul class="list-group">
        <div class="course-card" v-for="professor in professors" v-bind:key="professor.serialNumber">
          <ProfessorItem v-bind:professor="professor" @delete-professor="removeProfessor" />
        </div>
      </ul>
    </div>
  </div>
</template>

<script>

import $ from 'jquery'
import LoadingRow from '../components/LoadingRow.vue'
import ProfessorItem from '../components/ProfessorItem.vue'
import CustomToast from '@/components/CustomToast.vue'

export default {
  name: "AdminProfessors",
  data() {
    return {
      professors: null
    }
  },
  components: {
    LoadingRow,
    ProfessorItem,
    CustomToast
  },
  methods: {
    getProfessors() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "ProfessorServlet?operation=select", {
        method: "GET",
        success: (data) => {
          setTimeout(() => {
            self.professors = data
          }, 2000)
        }
      })
    },
    removeProfessor(professor) {
      for (var i = 0; i < this.professors.length; i++)
        if (this.professors[i] == professor)
          this.professors.splice(i, 1)
    },
  },
  mounted() {
    this.getProfessors()
  }
}
</script>