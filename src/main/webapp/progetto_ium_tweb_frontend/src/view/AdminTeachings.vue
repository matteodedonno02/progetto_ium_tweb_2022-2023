<template>
  <CustomToast />

  <AddTeachingModal v-bind:modalId="'addModal'" @new-teaching="updateTeaching" />
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione insegnamenti</li>
    </ol>
  </nav>

  <div class="row pb-5">
    <div class="col-5">
      <input v-model="searchField" type="text" class="form-control p-2" placeholder="Cerca insegnamento">
    </div>
  </div>

  <p data-bs-toggle="modal" v-bind:data-bs-target="'#addModal'">
    Aggiungi insegnamento</p>
  <div v-if="teachings === null">
    <LoadingRow />
    <LoadingRow />
    <LoadingRow />
  </div>
  <div v-else>
    <div v-if="teachings.length === 0">
      Nessun insegnamento presente
    </div>
    <div v-else>

      <ul class="list-group">
        <div class="course-card" v-for="teaching in teachings" v-bind:key="teaching.idTeaching">
          <TeachingItem v-bind:teaching="teaching" @delete-teaching="removeTeaching" />
        </div>
      </ul>
    </div>
  </div>


</template>

<script>

import $ from 'jquery'
import LoadingRow from '../components/LoadingRow.vue'
import TeachingItem from '../components/TeachingItem.vue'
import CustomToast from '@/components/CustomToast.vue'
import AddTeachingModal from '@/components/AddTeachingModal.vue'

export default {
  name: "AdminTeachings",
  data() {
    return {
      teachings: null,
      searchField: ""
    }
  },
  components: {
    LoadingRow,
    TeachingItem,
    CustomToast,
    AddTeachingModal
  },

  methods: {
    getTeachings() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet?operation=select", {
        method: "GET",
        success: (data) => {
          self.teachings = data
        }
      })
    },
    removeTeaching(teaching) {
      for (var i = 0; i < this.teachings.length; i++)
        if (this.teachings[i] == teaching)
          this.teachings.splice(i, 1)
    },
    updateTeaching(teaching) {
      this.teachings.push(teaching);
    }
  },
  watch: {
    searchField: function (newSearchField) {
      if (newSearchField === "") {
        this.getTeachings()
        return;
      }

      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "TeachingServlet", {
        method: "GET",
        data: {
          operation: "search",
          searchField: newSearchField
        }, success(data) {
          self.teachings = data
        }
      })
    },
  },
  mounted() {
    this.getTeachings()
  }
}
</script>