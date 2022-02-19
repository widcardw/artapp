<template>
  <div style="padding: 10px;">
    <el-dialog v-model="dialogVisible" title="提示" width="30%">
      <el-form label-width="120px" :model="form" :rules="rules" ref="form">
        <el-form-item label="学生 ID" style="display: none;">
          <el-input v-model="form.stuId" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item label="课程 ID" style="display: none;">
          <el-input v-model="form.courseId" style="width: 80%;"></el-input>
        </el-form-item>
        <el-form-item label="成绩">
          <el-input-number v-model="form.score" style="width: 80%;"></el-input-number>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <el-radio-group v-model="queryMethod" style="padding-bottom: 10px;">
      <el-radio :label="1">
        按学生名
        <el-input v-model="queryContent.stuName" clearable style="width: 75%;" @click="queryMethod=1"></el-input>
      </el-radio>
      <el-radio :label="2">
        按课程号
        <el-select-v2 v-model="queryContent.courseNo" filterable style="width: 75%" :options="courseNos"
                      @click="queryMethod=2"/>
      </el-radio>
      <el-button @click="handleLoad" type="primary">
        查询
      </el-button>
      <el-dropdown>
        <el-button type="primary" style="margin-left: 10px;">
          列表展示
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>
              <el-checkbox label="学生 ID" v-model="tableShow[0]"/>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-checkbox label="学生用户名" v-model="tableShow[1]"/>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-checkbox label="学生昵称" v-model="tableShow[2]"/>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-checkbox label="课程 ID" v-model="tableShow[3]"/>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-checkbox label="课程号" v-model="tableShow[4]"/>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-checkbox label="课程名" v-model="tableShow[5]"/>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-checkbox label="成绩" v-model="tableShow[6]"/>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-radio-group>
    <el-table :data="tableData" border style="width: 99%" v-loading="loading">
      <el-table-column prop="stuId" label="学生 ID" sortable v-if="tableShow[0]"/>
      <el-table-column prop="stuName" label="学生名" sortable v-if="tableShow[1]"/>
      <el-table-column prop="stuNickName" label="学生昵称" v-if="tableShow[2]"/>
      <el-table-column prop="courseId" label="课程 ID" v-if="tableShow[3]"/>
      <el-table-column prop="courseNo" label="课程号" sortable v-if="tableShow[4]"/>
      <el-table-column prop="courseName" label="课程名" v-if="tableShow[5]"/>
      <el-table-column prop="score" label="成绩" sortable v-if="tableShow[6]"/>
      <el-table-column fixed="right" label="操作" width="145">
        <template #default="scope">
          <el-button plain size="small" @click="handleEdit(scope.row)">
            <edit style="width: 20px; height: 20px;"/>
          </el-button>
          <el-popconfirm title="确认删除这条数据吗" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button type="danger" size="small">
                <delete style="width: 20px; height: 20px;"/>
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
import request from "../utils/request";
import {ArrowDown, Delete, Edit} from '@element-plus/icons-vue'

export default {
  name: "Score",
  components: {
    ArrowDown, Edit, Delete
  },
  data() {
    return {
      form: {},
      tableData: [],
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      total: 50,
      queryMethod: 2,
      queryContent: {
        stuName: "",
        courseNo: "",
        courseName: "高等数学"
      },
      courseNos: [],
      tableShow: [false, true, true, false, true, true, true],
      loading: false,
    }
  },
  created() {
    this.getCourseInfo();
    // this.handleLoad();
  },
  methods: {
    handleEdit(row) {
      this.form = {};
      this.form.stuId = row.stuId;
      this.form.courseId = row.courseId;
      this.form.score = row.score;
      console.log(this.form);
      this.dialogVisible = true;
    },
    handleSave() {
      request.put("/score", this.form).then(res => {
        console.log(res);
        if (res.code === "0") {
          this.$message({type: "success", message: "更新成功"});
          this.dialogVisible = false;
          this.handleLoad();
        } else {
          this.$message({type: "error", message: res.msg});
        }
      });
    },
    handleDelete(row) {
    },
    handleSizeChange() {
      this.handleLoad();
    },
    handleCurrentChange() {
      this.handleLoad();
    },
    getByStuName() {
      request.get("/score/stuName/" + this.queryContent.stuName, {
        params: {pageSize: this.pageSize, pageNum: this.currentPage}
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"});
        this.loading = false;
      })
    },
    getByCourseNo() {
      request.get("/score/courseNo/" + this.queryContent.courseNo, {
        params: {pageSize: this.pageSize, pageNum: this.currentPage}
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.loading = false;
        this.$message({type: "warning", message: "请求失败"});
      })
    },
    getCourseInfo() {
      request.get("/course/all").then(res => {
        console.log(res);
        this.courseNos = res.data;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "课程信息请求失败"});
      })
    },
    getByCourseName() {
      request.get("/score/courseName/" + this.queryContent.courseName, {
        params: {pageSize: this.pageSize, pageNum: this.currentPage}
      }).then(res => {
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.loading = false;
        this.$message({type: "warning", message: "请求失败"});
      })
    },
    handleLoad() {
      this.loading = true;
      switch (this.queryMethod) {
        case 1: {
          this.getByStuName();
          break;
        }
        case 2: {
          this.getByCourseNo();
          break;
        }
        case 3: {
          this.getByCourseName();
          break;
        }
      }
    },
  }
}
</script>

<style scoped>
.el-radio {
  margin-right: 10px;
}
</style>