<template>

  <ProfessorModal @delete-professor="this.$emit('delete-professor', this.professor)"
    v-bind:modalId="'modalDelete' + professor.serialNumber" title="Desideri cancellare il seguente insegnante ?"
    v-bind:professor="professor" />

  <div v-on:contextmenu="openMenu" class="w-100 card-row d-flex align-items-center mb-3">
    <div class="card my-repetition-card me-5">
      <div class="card-body">
        <div class="row">
          <img class="card-img-top w-10" alt="Immagine del professore" v-bind:src="professor.imageUrl">

          <div class="col text-start">
            {{ professor.serialNumber }} {{ professor.surname }} {{ professor.name }}
          </div>
          <div class="col text-end">

            <TrashCanOutline data-bs-toggle="modal" v-bind:data-bs-target="'#modalDelete' + professor.serialNumber" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import TrashCanOutline from 'vue-material-design-icons/TrashCanOutline.vue'
import ProfessorModal from './ProfessorModal.vue';

export default {
  name: "ProfessorItem",
  props: ["professor"],
  components: {
    TrashCanOutline,
    ProfessorModal
  },
  emits: ["delete-professor"],
  methods: {
    removeProfessor() {
      this.$emit("delete-professor", this.professor)
    }
  }
}
</script>