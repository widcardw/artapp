<template>
  <div>
    <div style="padding: 20px;">个人信息修改</div>
<!--    当前角色为教师-->
    <div v-if="adminSelection === 1">
      <el-form :model="form1" label-width="120px" style="width: 500px;" :rules="rules">
        <el-form-item label="教师用户名" prop="teacherName">
          <el-input v-model="form1.teacherName" maxlength="18"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form1.nickName" maxlength="18"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form1.password" type="password" show-password maxlength="31"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitTeacherData">确认修改</el-button>
        </el-form-item>
      </el-form>
    </div>
<!--    当前角色为管理员-->
    <div v-else-if="adminSelection === 2">
      <el-form :model="form2" label-width="120px" style="width: 500px;" :rules="rules">
        <el-form-item label="管理员" prop="adminName">
          <el-input v-model="form2.adminName" maxlength="18"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form2.nickName" maxlength="18"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form2.password" type="password" show-password maxlength="31"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAdminData">确认修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import {mapState} from "vuex";
import md5 from "crypto-js/md5";

export default {
  name: "Information",
  data() {
    return {
      // 考虑将这两个 form 合并
      form1: {},
      form2: {},
      rules: {
        teacherName: [{
          required: true, message: "请输入教师名", trigger: "blur"
        }],
        adminName: [{
          required: true, message: "请输入管理员名", trigger: "blur"
        }]
      }
    }
  },
  created() {
    this.getInfo();
    // console.log(this.$store.getters.loginData)
  },
  computed: mapState(["adminSelection", "loginData"]),
  methods: {
    // 管理员信息修改提交
    submitAdminData() {
      // 如果密码长度不足 32 位，说明是新密码，需要加密
      if (this.form2.password && this.form2.password.length !== 32) {
        this.encodePassword(this.form2);
      }
      // 发起请求
      request.put("/admin", this.form2).then(res=>{
        console.log(res);
        if (res.code === "0") {
          this.$message({type:"success", message: "修改成功"});
        } else {
          this.$message({type:"error", message: res.msg});
        }
      }).catch(err=>{
        console.log(err);
        this.$message({type:"warning", message: "请求失败"});
      })
    },
    // 教师修改信息
    submitTeacherData() {
      if (this.form1.password && this.form1.password.length !== 32) {
        this.encodePassword(this.form1);
      }
      request.put("/teacher", this.form1).then(res=>{
        console.log(res);
        if (res.code === "0") {
          this.$message({type:"success", message: "修改成功"});
        } else {
          this.$message({type:"error", message: res.msg});
        }
      }).catch(err=>{
        console.log(err);
        this.$message({type:"warning", message: "请求失败"});
      })
    },
    // 获取角色信息
    getInfo() {
      if (this.adminSelection === 1) {
        // 是教师
        this.getTeacherInfo();
      } else if (this.adminSelection === 2) {
        // 是管理员
        this.getAdminInfo();
      }
    },
    getTeacherInfo() {
      request.get("/teacher/info", {
        params: {
          teacherName: this.loginData.teacherName
        }
      }).then(res => {
        console.log(res);
        if (res.code === "0") {
          this.form1 = res.data;
        } else {
          this.$message({type: "error", message: res.msg})
        }
      });
    },
    getAdminInfo() {
      request.get("/admin/info", {
        params: {
          adminName: this.loginData.adminName
        }
      }).then(res => {
        console.log(res);
        if (res.code === "0") {
          this.form2 = res.data;
        } else {
          this.$message({type: "error", message: res.msg})
        }
      });
    },
    encodePassword(form) {
      form.password = md5(form.password).toString();
    },
  }
}
</script>

<style scoped>

</style>