<template>
  <CustomToast />

  <AddProfessorModal v-bind:modalId="'addModal'" @new-professor="updateProfessors" />
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione professori</li>
    </ol>
  </nav>

  <div class="row pb-5">
    <div class="col-5">
      <input v-model="searchField" v-on:change="textFieldChange" type="text" class="form-control p-2"
        placeholder="Cerca professore">
    </div>
  </div>

  <button data-bs-toggle="modal" v-bind:data-bs-target="'#addModal'"
    class="admin-button custom-button btn btn-primary rounded-pill mb-3">
    Aggiungi insegnante
  </button>

  <div v-if="professors === null">
    <LoadingRow />
    <LoadingRow />
    <LoadingRow />

  </div>
  <div v-else>
    <div v-if="professors.length === 0">
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
import AddProfessorModal from '../components/AddProfessorModal.vue'

export default {
  name: "AdminProfessors",
  data() {
    return {
      professors: null,
      searchField: ""
    }
  },
  components: {
    LoadingRow,
    ProfessorItem,
    CustomToast,
    AddProfessorModal
  },
  methods: {
    getProfessors() {
      let self = this
      $.ajax("ProfessorServlet?operation=select", {
        method: "GET",
        success: (data) => {
          self.professors = data
        }
      })
    },
    removeProfessor(professor) {
      for (var i = 0; i < this.professors.length; i++)
        if (this.professors[i] == professor)
          this.professors.splice(i, 1)
    },
    updateProfessors(professor) {
      this.professors.push(professor)
      this.professors.sort((a, b) => {
        return a.serialNumber.localeCompare(b.serialNumber)
      })
    },
  },
  watch: {
    searchField: function (newSearchField) {
      if (newSearchField === "") {
        this.getProfessors()
        return;
      }

      let self = this
      $.ajax("ProfessorServlet", {
        method: "GET",
        data: {
          operation: "search",
          searchField: newSearchField
        }, success(data) {
          self.professors = data
        }
      })
    }
  },
  mounted() {
    this.getProfessors()
  }
}
</script>