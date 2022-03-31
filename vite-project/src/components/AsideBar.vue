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
      <el-sub-menu index="2">
        <template #title>用户管理</template>
        <el-sub-menu index="2-1">
          <template #title>学生部分</template>
          <el-menu-item index="user">学生管理</el-menu-item>
          <el-menu-item index="course">课程管理</el-menu-item>
          <el-menu-item index="score">成绩管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu v-if="$store.getters.adminSelection === 2" index="2-2">
          <template #title>教师部分</template>
          <el-menu-item index="teacher">教师账号管理</el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
      <el-sub-menu index="3">
        <template #title>题库管理</template>
        <el-menu-item index="exercises">题目</el-menu-item>
      </el-sub-menu>
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