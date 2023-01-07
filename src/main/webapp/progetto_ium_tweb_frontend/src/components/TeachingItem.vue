<template>
  <div class="card-body">

    <div class="col">
      <div class="row">
        <div class="col text-start">
          <p class="card-text">
            {{ teaching.professor.surname }} {{ teaching.professor.name }} con matricola {{
              teaching.professor.serialNumber
            }} effettua ripetizioni di {{ teaching.course.title }}
            <TrashCanOutline />
          </p>
        </div>

      </div>
    </div>
  </div>

</template>

<script>


import TrashCanOutline from 'vue-material-design-icons/TrashCanOutline.vue'

export default {
  name: "TeachingItem",
  props: ["teaching"],
  components: {
    TrashCanOutline
  }
}
</script>