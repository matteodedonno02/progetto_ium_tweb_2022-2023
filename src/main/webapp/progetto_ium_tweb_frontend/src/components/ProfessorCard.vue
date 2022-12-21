<template>
  <div class="card" style="width: 18rem;">
    <div class="card-body">
      <div class="row">
        <div v-bind:id="serialNumber" class="col-5 col-img">
        </div>
        <div class="col-7 d-flex flex-column justify-content-center">
          <h5 class="card-title">{{ title.split(" ")[0] }} <br> {{ title.split(" ")[1] }}</h5>
        </div>
      </div>
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