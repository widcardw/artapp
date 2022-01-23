<template>
  <div>

    <el-menu
        default-active="home"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        router
    >
      <!--      index 即为要跳转的路由-->
      <el-menu-item index="home" :route="{path: '/'}">
        <template #title>首页</template>
      </el-menu-item>
      <el-sub-menu>
        <template #title>用户管理</template>
        <el-sub-menu>
          <template #title>学生部分</template>
          <el-menu-item index="user">学生管理</el-menu-item>
          <el-menu-item index="course">课程管理</el-menu-item>
          <el-menu-item index="score">成绩管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu v-if="$store.getters.adminSelection === 2">
          <template #title>教师部分</template>
          <el-menu-item index="teacher">教师账号管理</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
      <el-menu-item>
        <template #title>题库管理</template>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "AsideBar",
  methods: {
    handleOpen() {

    },
    handleClose() {
    }
  }
}
</script>

<style scoped>
.el-menu-vertical-demo {
  width: 200px;
  min-height: calc(100vh - 50px);
}
</style>