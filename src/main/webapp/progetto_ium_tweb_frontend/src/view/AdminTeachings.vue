<template>
  <CustomToast />

  <AddTeachingModal v-bind:modalId="'addModal'" />
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item active" aria-current="page">Gestione insegnamenti</li>
    </ol>
  </nav>
  <p @new-teaching="updateTeaching" data-bs-toggle="modal" v-bind:data-bs-target="'#addModal'">
    Aggiungi insegnamento</p>
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
      teachings: null
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
          setTimeout(() => {
            self.teachings = data
          }, 2000)
        }
      })
    },
    removeTeaching(teaching) {
      for (var i = 0; i < this.teachings.length; i++)
        if (this.teachings[i] == teaching)
          this.teachings.splice(i, 1)
    },
    updateTeaching(teaching) {
      this.teaching.add(teaching);
    }
  },
  mounted() {
    this.getTeachings()
  }
}
</script>