<template>
  <div>
    <!--    头部-->
    <Header :nick-name="user.nickName"/>
    <!--    主体-->
    <div style="display: flex;">
      <!--      侧边栏-->
      <AsideBar/>
      <!--      内容-->
      <router-view style="flex: 1;"/>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import AsideBar from "@/components/AsideBar.vue";

export default {
  name: "Layout",
  components: {
    Header,
    AsideBar
  },
  data() {
    return {
      user: {}
    }
  },
  created() {
    this.user = this.$store.getters.loginData
  }
}
</script>

<style scoped>

</style>