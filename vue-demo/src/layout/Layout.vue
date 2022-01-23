<template>
  <div>
    <!--    头部-->
    <Header :nick-name="loginData.nickName"/>
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
import Header from "../components/Header";
import AsideBar from "../components/AsideBar";
import {mapState} from "vuex";

export default {
  name: "Layout",
  components: {
    Header,
    AsideBar
  },
  computed: mapState(["loginData"]),
  data() {
    return {}
  },
  created() {
    console.log(this.loginData)
  }
}
</script>

<style scoped>

</style>