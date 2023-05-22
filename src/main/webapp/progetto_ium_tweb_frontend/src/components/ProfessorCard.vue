<template>
  <div class="d-flex flex-column align-items-center" style="width: 18rem;">
    <div v-bind:id="serialNumber" class="col-5 col-img-2">
    </div>
    <div class="row justify-content-center pt-3">
      <h5 class="card-title">{{ title.split(" ")[0] }} {{ title.split(" ")[1] }}</h5>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'

export default {
  name: "ProfessorCard",
  props: ["serialNumber", "imageUrl", "title"],
  mounted() {
    $("#" + this.serialNumber).css("background-image", "url(" + this.imageUrl + ")")
  }
}
</script>