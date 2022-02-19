<template>
  <div style="padding: 10px;">
    <div class="operation-bar">
      <div>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="primary">导入</el-button>
        <el-button type="primary">导出</el-button>

        <el-dialog v-model="dialogVisible" title="提示" width="30%">
          <el-form label-width="120px" :model="form" :rules="rules" ref="form">
            <el-form-item label="课程号" prop="courseNo">
              <el-input v-model="form.courseNo" style="width: 80%;" maxlength="18" clearable></el-input>
            </el-form-item>
            <el-form-item label="课程名" prop="courseName">
              <el-input v-model="form.courseName" style="width: 80%;" maxlength="18" clearable></el-input>
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
        <el-input v-model="searchContent" clearable placeholder="输入关键字搜索" style="min-width: 300px;">
          <template #prefix>
            <el-icon class="el-input__icon"><search /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" style="margin-left: 5px;" @click="handleLoad">查询</el-button>
      </div>
    </div>
    <el-table :data="tableData" border style="width: 99%" v-loading="loading">
      <el-table-column prop="id" label="ID" sortable/>
      <el-table-column prop="courseNo" label="课程号" sortable/>
      <el-table-column prop="courseName" label="课程名"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button plain size="small" @click="handleEdit(scope.row)">
            <edit style="width: 20px; height: 20px"/>
          </el-button>
          <el-popconfirm title="确认删除这条数据吗" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small">
                <delete style="width: 20px; height: 20px"/>
              </el-button>
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
import {Delete, Edit, Search} from "@element-plus/icons-vue";
import request from "../utils/request";

export default {
  name: "Course",
  components: {
    Search,
    Edit,
    Delete
  },
  data() {
    return {
      form: {},
      loading: false,
      total: 0,
      currentPage: 1,
      pageSize: 10,
      tableData: [],
      searchContent: '',
      dialogVisible: false,
      rules: {
        courseNo: [{
          required: true, message: "请输入课程号", trigger: "blur"
        }],
        courseName: [{
          required: true, message: "请输入课程名", trigger: "blur"
        }]
      }
    }
  },
  created() {
    this.handleLoad();
  },
  methods: {
    handleLoad() {
      this.loading = true;
      request.get("/course", {
        params: {
          pageSize: this.pageSize,
          pageNum: this.currentPage,
          search: this.searchContent
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.loading = false;
      }).catch(err => {
        console.log(err)
        this.$message({type: "warning", message: "请求失败"})
        this.loading = false;
      })
    },
    handleSave() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.courseNo || this.form.courseName.trim() === "") {
            return;
          }
          if (this.form.id) {
            // 有 id 表示更新
            request.put("/course", this.form
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
            request.post("/course", this.form
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
          this.$message({type: "error", message: "请输入有效的课程号和课程名"});
        }
      });
    },
    handleAdd() {
      this.dialogVisible = true;
      this.form = {};
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible = true;
    },
    handleDelete(id) {
      console.log("delete", id);
      request.delete("/course/" + id).then(res => {
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
  }
}
</script>

<style scoped>
.operation-bar {
  margin: 10px 0;
  display: flex;
  justify-content: space-between;
}
</style>