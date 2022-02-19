<template>
  <div style="padding: 10px;">
    <div class="operation-bar">
      <div>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="primary">导入</el-button>
        <el-button type="primary">导出</el-button>

        <el-dialog v-model="dialogVisible" title="提示" width="50%">
          <el-form label-width="120px" :model="form" :rules="rules" ref="form">
            <el-form-item label="题号" prop="id">
              <el-input v-model="form.id" style="width: 85%;" maxlength="255" clearable></el-input>
            </el-form-item>
            <el-form-item label="题目类型" prop="type">
              <el-input v-model="form.type" style="width: 85%;" maxlength="255" clearable></el-input>
            </el-form-item>
            <el-form-item label="题目描述" prop="content">
              <el-input v-model="form.content" style="width: 85%;" type="textarea" maxlength="1024"
                        clearable></el-input>
            </el-form-item>
            <el-form-item :label="String.fromCharCode(index + 65)" v-for="(item, index) in form.choices"
                          :key="(item, index)" prop="choices">
              <div style="display: grid;grid-template-columns: 85% 15%; width: 100%">
                <el-input v-model="item.ccontent" property="value">
                  <template #prepend>
                    <el-button @click="removeChoice(index)">
                      <el-tooltip class="item" effect="dark" content="删除" placement="top">
                        <el-icon>
                          <minus/>
                        </el-icon>
                      </el-tooltip>
                    </el-button>
                  </template>
                  <template #append>
                    <el-button @click="insertChoice(index)">
                      <el-tooltip class="item" effect="dark" content="添加" placement="top">
                        <el-icon>
                          <plus/>
                        </el-icon>
                      </el-tooltip>
                    </el-button>
                  </template>
                </el-input>
                <el-checkbox v-model="item.canswer" style="margin-left: 15px;">答案</el-checkbox>
              </div>
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
      <el-radio-group v-model="queryMethod" style="padding-bottom: 10px;">
        <el-radio :label="0" @click="queryByPage">
          总览
        </el-radio>
        <el-radio :label="1">
          按编号
          <el-input v-model="queryContent.id" clearable style="width: 90%;" @click="queryMethod=1"></el-input>
        </el-radio>
        <el-radio :label="2">
          按类别
          <el-input v-model="queryContent.type" clearable style="width: 90%;" @click="queryMethod=2"></el-input>
        </el-radio>
        <el-button @click="handleQuery" type="primary">
          查询
        </el-button>
      </el-radio-group>
    </div>

    <div v-loading="loading">
      <el-empty description="没有数据" v-if="tableData.length===0"></el-empty>
      <el-descriptions v-for="ex in tableData" border :column="1"
                       style="margin-bottom: 10px; border: 1px solid var(--el-border-color-base); padding: 10px;"
                       :title="'题号 ' + ex.id">
        <template #extra>
          <el-button @click="editEx(ex)">编辑</el-button>
          <el-popconfirm title="确认删除这条数据吗" @confirm="deleteEx(ex)">
            <template #reference>
              <el-button type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
        <el-descriptions-item label="题目类型">{{ ex.type }}</el-descriptions-item>
        <el-descriptions-item label="题目描述">{{ ex.content }}</el-descriptions-item>
        <el-descriptions-item label="选项">
          <el-checkbox v-for="(item, index) in ex.choices" :key="(item, index)"
                       :label="String.fromCharCode(index + 65)"
                       :checked="item.canswer===true"
          >{{ item.cid }}. {{ item.ccontent }}
          </el-checkbox>
        </el-descriptions-item>
      </el-descriptions>
    </div>

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
import {Delete, Edit, Plus, Minus} from "@element-plus/icons-vue";
import request from "../utils/request";

export default {
  name: "Exercises",
  components: {
    Edit, Delete, Plus, Minus
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      tableData: [],
      queryMethod: 0,
      queryContent: {},
      dialogVisible: false,
      form: {},
      rules: {
        id: [
          {required: true, message: '请输入题号', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '请输入题目类型', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入题目描述', trigger: 'blur'}
        ],
        choices: [
          {required: true, message: '请输入选项', trigger: 'blur'}
        ]
      }
    };
  },
  created() {
    this.queryByPage();
  },
  methods: {
    handleSave() {
      this.form.choices.forEach((item, index) => {
        item.cid = String.fromCharCode(index + 65);
      });
      console.log(this.form)
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true;
          // 没有 _id 表示新增
          if (!this.form._id || this.form._id === '') {
            request({
              url: "/exercise/one",
              method: "post",
              data: this.form
            }).then(res => {
              console.log(res)
              if (res.code === "0") {
                this.dialogVisible = false;
                this.handleQuery();
                this.$message({type: "success", message: "添加成功"});
              } else {
                this.$message({type: "error", message: res.msg});
              }
            }).catch(err => {
              this.$message.error(err.message);
            }).finally(() => {
              this.loading = false;
            });
          } else {
            // 有 _id 表示编辑
            request({
              url: "/exercise/one",
              method: "put",
              data: this.form
            }).then(res => {
              console.log(res)
              if (res.code === "0") {
                this.dialogVisible = false;
                this.handleQuery();
                this.$message({type: "success", message: "修改成功"});
              } else {
                this.$message({type: "error", message: res.msg});
              }
            }).catch(err => {
              this.$message.error(err.message);
            }).finally(() => {
              this.loading = false;
            });
          }
        }
      });
    },
    handleAdd() {
      this.form = {
        choices: [{ccontent: "", canswer: false}]
      }
      this.dialogVisible = true;
    },
    editEx(ex) {
      this.form = ex;
      this.dialogVisible = true;
    },
    deleteEx(ex) {
      this.loading = true;
      request({
        url: `/exercise/id/${ex._id}`,
        method: 'delete'
      }).then((res) => {
        if (res.code === "0") {
          this.$message({type: "success", message: "删除成功"});
          this.handleQuery();
        } else {
          this.$message({type: "error", message: res.msg});
        }
      }).catch((err) => {
        this.$message.error(err.message);
      }).finally(() => {
        this.loading = false;
      });
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.handleQuery();
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum;
      this.handleQuery();
    },
    queryById() {
      this.loading = true;
      request.get("/exercise/id/" + this.queryContent.id).then(res => {
        console.log(res);
        if (res.data) {
          this.tableData = [res.data];
        } else {
          this.tableData = [];
        }
        this.total = 1;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"});
      }).finally(() => {
        this.loading = false;
      });
    },
    queryByType() {
      this.loading = true;
      request.get("/exercise/type/" + this.queryContent.type, {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.content;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"});
      }).finally(() => {
        this.loading = false;
      });
    },
    handleQuery() {
      this.tableData = []
      switch (this.queryMethod) {
        case 0: {
          this.queryByPage();
          break;
        }
        case 1: {
          this.queryById()
          break;
        }
        case 2: {
          this.queryByType();
          break;
        }
      }
    },
    queryByPage() {
      this.loading = true;
      request.get("/exercise/page", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.content;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"});
      }).finally(() => {
        this.loading = false;
      });
    },
    removeChoice(index) {
      if (this.form.choices.length > 1) {
        this.form.choices.splice(index, 1);
      }
    },
    insertChoice(index) {
      console.log(index);
      if (!this.form || !this.form.choices) {
        this.form.choices = [{ccontent: "", canswer: false}]
      }
      this.form.choices.splice(index + 1, 0, {ccontent: "", canswer: false});
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