<template>
  <div class="header-bg">
    <div style="width: 200px; padding-left: 1em; font-weight: bold; color: white;">后台管理
    </div>
    <div style="flex: 1;">
    </div>
    <el-dropdown style="width: 100px; line-height: 50px; text-align: center;">
        <span class="el-dropdown-link">
          {{ nickName }}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="$router.push('/info')">个人信息</el-dropdown-item>
          <el-dropdown-item @click="logout">退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script>
import {ArrowDown} from '@element-plus/icons-vue'
import {ElDropdown, ElDropdownMenu, ElDropdownItem, ElIcon} from 'element-plus'

export default {
  name: "Header",
  props: {
    nickName: String
  },
  components: {
    ArrowDown,ElDropdown, ElDropdownMenu, ElDropdownItem, ElIcon
  },
  methods: {
    logout() {
      this.$store.dispatch("Logout");
      this.$router.push("/login");
    }
  }
}
</script>

<style scoped>
.header-bg {
  height: 50px;
  line-height: 50px;
  border-bottom: 1px solid #aaa;
  display: flex;
  background-color: dodgerblue;
}

.el-dropdown-link {
  color: white;
}
</style>