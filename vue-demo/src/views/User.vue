<template>
  <div style="padding: 10px;">
    <div class="operation-bar">
      <div>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="primary">导入</el-button>
        <el-button type="primary">导出</el-button>

        <el-dialog v-model="dialogVisible" title="提示" width="30%">
          <el-form label-width="120px" :model="form" :rules="rules" ref="form">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" style="width: 80%;" maxlength="18" clearable></el-input>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="form.nickName" style="width: 80%;" maxlength="18" clearable></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" style="width: 80%;" type="password" show-password
                        maxlength="31" clearable></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="handleSave">确认</el-button>
            </span>
          </template>
        </el-dialog>
      </div>
      <div style="display: flex;">
        <el-input v-model="searchContent" clearable placeholder="输入关键字搜索" style="min-width: 300px;" :prefix-icon="Search"/>
        <el-button type="primary" style="margin-left: 5px;" @click="handleLoad">查询</el-button>
      </div>
    </div>
    <el-table :data="tableData" border style="width: 99%">
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="username" label="用户名" sortable/>
      <el-table-column prop="nickName" label="昵称"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button plain size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除这条数据吗" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        v-model:currentPage="currentPage"
        :page-sizes="[5, 10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>

<script>

import request from "../utils/request";
import md5 from "crypto-js/md5"
import {Search} from "@element-plus/icons-vue";

export default {
  name: 'User',
  components: {
    Search
  },
  data() {
    return {
      total: 50,
      currentPage: 1,
      pageSize: 10,
      searchContent: "",
      dialogVisible: false,
      form: {},
      tableData: [],
      rules: {
        username: [{
          required: true, message: "请输入用户名", trigger: "blur"
        }]
      }
    }
  },
  created() {
    this.handleLoad();
  },
  methods: {
    handleLoad() {
      request.get("/api/user", {
        params: {
          pageSize: this.pageSize,
          pageNum: this.currentPage,
          search: this.searchContent
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err)
        this.$message({type: "warning", message: "请求失败"})
      })
    },
    handleAdd() {
      this.dialogVisible = true;
      this.form = {};
    },
    handleSave() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.username || this.form.username.trim() === "") {
            return;
          }
          if (this.form.id) {
            // 有 id 表示更新
            if (this.form.password && this.form.password.length !== 32) {
              this.encodePassword();
            }
            request.put("/api/user", this.form
            ).then(res => {
              console.log(res);
              if (res.code === "0") {
                this.$message({type: "success", message: "更新成功"})
                this.dialogVisible = false;
                this.handleLoad();
              } else {
                this.$message({type: "error", message: res.msg})
              }
            }).catch(err => {
              console.log(err);
              this.$message({type: "warning", message: err.msg})
            })
          } else {
            // 没有 id 表示新增
            this.encodePassword();
            request.post("/api/user", this.form
            ).then(res => {
              console.log(res);
              if (res.code === "0") {
                this.$message({type: "success", message: "新增成功"})
                this.dialogVisible = false;
                this.handleLoad();
              } else {
                this.$message({type: "error", message: res.msg})
              }
            }).catch(err => {
              console.log(err);
              this.$message({type: "warning", message: err.msg})
            })
          }
        } else {
          this.$message({type: "error", message: "请输入有效的用户名"});
        }
      });
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleDelete(id) {
      console.log("delete", id);
      request.delete("/api/user/" + id).then(res => {
        console.log(res);
        if (res.code === "0") {
          this.$message({type: "success", message: "删除成功"})
          this.handleLoad();
        } else {
          this.$message({type: "error", message: "删除失败"})
        }
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"})
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.handleLoad();
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.handleLoad();
    },
    encodePassword() {
      this.form.password = md5(this.form.password).toString();
    },
  }
}
</script>
<style>
.operation-bar {
  margin: 10px 0;
  display: flex;
  justify-content: space-between;
}
</style>