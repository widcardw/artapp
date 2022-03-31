<template>
  <div>
    Home
  </div>
</template>

<script>

export default {
  name: "Home",
  created() {
      console.log(this.$store.getters.loginData);
  }
}
</script>

<style scoped>

</style>