<template>
  <div v-on:contextmenu="openMenu" class="w-100 card-row d-flex align-items-center mb-3">
    <div class="card my-repetition-card me-5">
      <div class="card-body">
        <div class="row">
          <img class="card-img-top w-10" alt="Immagine del professore" v-bind:src="professor.imageUrl">

          <div class="col text-start">
            {{ professor.serialNumber }} {{ professor.surname }} {{ professor.name }}
          </div>
          <div class="col text-end">
            <TrashCanOutline />
          </div>
        </div>
      </div>
    </div>
  </div>


</template>

<script>

import TrashCanOutline from 'vue-material-design-icons/TrashCanOutline.vue'

export default {
  name: "ProfessorItem",
  props: ["professor"],
  components: {
    TrashCanOutline
  }
}
</script>