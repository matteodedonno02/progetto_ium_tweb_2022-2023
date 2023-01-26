<template>

  <ProfessorModal @delete-professor="this.$emit('delete-professor', this.professor)"
    v-bind:modalId="'modalDelete' + professor.serialNumber" title="Desideri cancellare il seguente insegnante ?"
    v-bind:professor="professor" />

  <div v-on:contextmenu="openMenu" class="w-100 card-row d-flex align-items-center mb-3">
    <div class="card my-repetition-card professor-item me-5">
      <div class="card-body">
        <div class="row">
          <div class="col-1 col-img" v-bind:id="professor.serialNumber"></div>

          <div class="col-10 text-start">
            {{ professor.serialNumber }} {{ professor.surname }} {{ professor.name }}
          </div>
          <div class="col-1 text-end">
            <TrashCanOutline class="pt-1 pr-1" data-bs-toggle="modal"
              v-bind:data-bs-target="'#modalDelete' + professor.serialNumber" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
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
  mounted() {
    $("#" + this.professor.serialNumber).css("background-image", "url(" + this.professor.imageUrl + ")")
  }
}
</script>