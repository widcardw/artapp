<template>
  <div class="bg" :style="{backgroundImage: 'url(' + imgSrc + ')'}">
    <div class="login">
      <div style="font-size: 25px; text-align: center; padding-top: 20px;">欢迎</div>
      <el-form :model="form" :rules="rules" label-width="120px" style="padding-bottom: 20px;">
        <el-form-item>
          <el-radio-group v-model="adminSelection" @change="resetForm" prop="adminSelection">
            <el-radio label="1" size="large">教师</el-radio>
            <el-radio label="2" size="large">管理员</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="教师" prop="teacherName" v-if="adminSelection==='1'">
          <el-input v-model="form.teacherName" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="管理员" prop="adminName" v-if="adminSelection==='2'">
          <el-input v-model="form.adminName" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" style="width:80%" type="password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" :disabled="isLogging">登录</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import md5 from "crypto-js/md5";

export default {
  name: "Login",
  created() {
    this.getWallPaper();
  },
  data() {
    return {
      imgSrc: "",
      adminSelection: '1',
      isLogging: false,
      form: {},
      rules: {
        adminSelection: [{
          required: true, message: "请选择登录方式", trigger: "change"
        }],
        teacherName: [{
          required: true, message: "请输入用户名", trigger: "blur"
        }],
        adminName: [{
          required: true, message: "请输入用户名", trigger: "blur"
        }],
        password: [{
          required: true, message: "请输入密码", trigger: "blur"
        }]
      }
    }
  },
  methods: {
    submitForm() {
      this.isLogging = true;
      console.log(this.adminSelection)
      if (this.form.password && this.form.password.length !== 32) {
        this.encodePassword();
      }
      if (this.adminSelection === '1') {
        console.log("teacher")
        this.teacherLogin();
      } else if (this.adminSelection === '2') {
        console.log("admin")
        this.adminLogin();
      }
    },
    resetForm() {
      this.form = {};
    },
    teacherLogin() {
      request.post("/teacher/login", this.form).then(res => {
        console.log(res);
        this.isLogging = false;
        if (res.code === "0") {
          this.$message({type: "success", message: "登陆成功"});
          this.$store.dispatch("Login", {
            adminSelection: 1,
            form: res.data
          });
          this.$router.push({path: "/"})
        } else {
          this.$message({type: "error", message: res.msg});
        }
      }).catch(err => {
        this.isLogging = false;
        this.$message({type: "error", message: err.message});
      }).finally(() => {
        this.isLogging = false;
      });
    },
    adminLogin() {
      request.post("/admin/login", this.form).then(res => {
        console.log(res);
        this.isLogging = false;
        if (res.code === "0") {
          this.$message({type: "success", message: "登陆成功"});
          this.$store.dispatch("Login", {
            adminSelection: 2,
            form: res.data
          });
          this.$router.push({path: "/"})
        } else {
          this.$message({type: "error", message: res.msg});
        }
      }).catch(err => {
        this.isLogging = false;
        this.$message({type: "error", message: err.message});
      }).finally(() => {
        this.isLogging = false;
      });
    },
    getWallPaper() {
      request.get('/wallpaper', {
        params: {
          n: 1,
          mkt: "zh-CN",
          idx: 0,
          format: 'js'
        }
      }).then(res => {
        console.log(res);
        let imgUrl = res.images[0].url;
        this.imgSrc = "https://cn.bing.com" + imgUrl;
        console.log(this.imgSrc)
      })
    },
    encodePassword() {
      this.form.password = md5(this.form.password).toString();
    },
  }
}
</script>

<style scoped>
.bg {
  width: 100%;
  height: 100vh;
  overflow: hidden;
  background-color: lightskyblue;
  background-repeat: no-repeat;
  background-size: cover;
}

.login {
  width: 400px;
  margin: 200px auto;
  background-color: rgb(255, 255, 255, 0.8);
  box-shadow: 0 0 20px grey;
  border-radius: 10px;
  /*filter: blur(3px);*/
}
</style>