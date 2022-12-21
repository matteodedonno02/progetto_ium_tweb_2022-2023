<template>
  <div class="card is-loading" style="width: 18rem;">
    <div class="row">
      <div class="image"></div>
    </div>
    <div class="card-body">
      <h5 class="card-title"></h5>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoadingCard",
  props: ["iconUrl", "title"],
}
</script>