<template>
  <div v-on:contextmenu="openMenu" class="w-100 card-row d-flex align-items-center mb-3">
    <div class="card my-repetition-card me-5">
      <div class="card-body">
        <div class="row">
          <img class="card-img-top w-10" alt="Immagine del corso" v-bind:src="course.iconUrl">

          <div class="col text-start">
            {{ course.title }}
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
  name: "CourseItem",
  props: ["course"],
  components: {
    TrashCanOutline
  }
}
</script>