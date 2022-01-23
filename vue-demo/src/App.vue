<template>
  <div>
    <router-view/>
  </div>
</template>

<style>

</style>
<script>

import {mapState} from "vuex";

export default {
  name: "App",
  components: {},
  computed: mapState(["hasLogin"]), // 很迷，getters 里啥都没有
  created() {
    if (!this.hasLogin) {
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