<template>
  <div>
    <router-view/>
  </div>
</template>

<style>

</style>
<script>

export default {
  name: "App",
  components: {
  },
  created() {
    if (!this.$store.getters.hasLogin) {
      // 不在登录状态，则路由到登陆界面
      this.$router.push({path: "/login"});
    } else {
      // 在登陆状态
      this.$router.push({path: "/"});
    }
  },
  methods: {}
}
</script>