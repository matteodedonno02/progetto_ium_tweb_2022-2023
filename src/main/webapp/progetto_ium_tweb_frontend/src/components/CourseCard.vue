<template>
  <div class="card">
    <div class="row">
      <img v-bind:src="iconUrl" class="card-img-top" alt="Course Card">
    </div>
    <div class="card-body">
      <h5 class="card-title">{{ title }}</h5>
    </div>
  </div>
</template>

<script>

export default {
  name: "CourseCard",
  props: ["loggedUser", "iconUrl", "title"],
}
</script>