<template>
  <div class="card d-flex justify-content-center align-items-center">
    <img v-bind:src="iconUrl" class="card-img-top pt-3" alt="Course Card">
    <h5 class="card-title pt-3 pb-1">{{ title }}</h5>
  </div>
</template>

<script>
export default {
  name: "CourseCard",
  props: ["iconUrl", "title"],
}
</script>