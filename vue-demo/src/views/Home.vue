<template>
  <div>
    Home
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "Home",
  computed: mapState(["loginData"]),
  created() {
      console.log(this.loginData);
  }
}
</script>

<style scoped>

</style>