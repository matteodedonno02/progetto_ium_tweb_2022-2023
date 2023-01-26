<template>

  <TeachingModal @delete-teaching="this.$emit('delete-teaching', this.teaching)"
    v-bind:modalId="'modalDelete' + teaching.idTeaching" title="Desideri cancellare il seguente insegnamento ?"
    v-bind:teaching="teaching" />

  <div v-on:contextmenu="openMenu" class="w-100 card-row d-flex align-items-center mb-3">
    <div class="card my-repetition-card me-5">
      <div class="card-body">
        <div class="row">
          <div class="col text-start">
            {{ teaching.professor.surname }}
            {{ teaching.professor.name }}
            {{ teaching.professor.serialNumber }}
          </div>
          <div class="col text-start">
            {{ teaching.course.title }}
          </div>
          <div class="col text-end">

            <TrashCanOutline data-bs-toggle="modal" v-bind:data-bs-target="'#modalDelete' + teaching.idTeaching" />
          </div>
        </div>
      </div>
    </div>
  </div>



</template>

<script>


import TrashCanOutline from 'vue-material-design-icons/TrashCanOutline.vue'
import TeachingModal from './TeachingModal.vue';

export default {
  name: "TeachingItem",
  props: ["teaching"],
  components: {
    TrashCanOutline,
    TeachingModal
  },
  emits: ["delete-teaching"],

}
</script>