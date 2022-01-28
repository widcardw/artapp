<template>
  <div style="padding: 10px;">
    <el-radio-group v-model="queryMethod" style="padding-bottom: 10px;">
      <el-radio :label="1">
        按编号
        <el-input v-model="queryContent.id" clearable style="width: 75%;" @click="queryMethod=1"></el-input>
      </el-radio>
      <el-radio :label="2">
        按类别
        <el-input v-model="queryContent.type" clearable style="width: 75%;" @click="queryMethod=2"></el-input>
      </el-radio>
      <el-button @click="handleQuery" type="primary">
        查询
      </el-button>
    </el-radio-group>
    <el-skeleton :loading="loading" :rows="5" animated>
      <el-table :data="tableData" border style="width: 99%;">
        <!--      <el-table-column prop="_id" label="_id" width="100" style="display: none;"/>-->
        <el-table-column prop="id" label="ID" width="140"/>
        <el-table-column
            prop="type"
            label="题目类型"
            width="140"
        ></el-table-column>
        <el-table-column prop="content" label="题目描述"/>
        <el-table-column label="选项内容">
          <template #default="scope">
            <tr v-for="(item, index) in scope.row.choices" :key="(item, index)">
              <td class="el-table__cell choice-cell">{{ index }}</td>
              <td class="el-table__cell choice-cell">{{ item }}</td>
            </tr>
          </template>
        </el-table-column>
        <el-table-column prop="answer" label="答案" width="60" align="center"/>
        <el-table-column fixed="right" label="操作" width="145" align="center">
          <template #default="scope">
            <el-button plain size="small" @click="handleEdit(scope.row)">
              <edit style="width: 20px; height: 20px;"/>
            </el-button>
            <el-popconfirm title="确认删除这条数据吗" @confirm="handleDelete(scope.row._id)">
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
    </el-skeleton>
  </div>
</template>

<script>
import {Delete, Edit} from "@element-plus/icons-vue";
import request from "../utils/request";

export default {
  name: "Exercises",
  components: {
    Edit, Delete
  },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      tableData: [],
      queryMethod: 1,
      queryContent: {}
    };
  },
  created() {
    this.handleLoad();
  },
  methods: {
    handleEdit(row) {
      let data = JSON.parse(JSON.stringify(row))
      console.log(data);
    },
    handleDelete(_id) {
      console.log(_id);
    },
    handleSizeChange() {
      this.handleLoad();
    },
    handleCurrentChange() {
      this.handleLoad();
    },
    queryById() {
      this.loading = true;
      request.get("/api/exercise/id/" + this.queryContent.id, {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }
      }).then(res => {
        console.log(res);
        this.tableData = [res.data];
        this.total = 1;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"});
        this.loading = false;
      })
    },
    queryByType() {
      this.loading = true;
      request.get("/api/exercise/type/" + this.queryContent.type, {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.content;
        this.total = res.data.total;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"});
        this.loading = false;
      })
    },
    handleQuery() {
      switch (this.queryMethod) {
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
    handleLoad() {
      this.loading = true;
      request.get("/api/exercise/page", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data.content;
        this.total = res.data.total;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.$message({type: "warning", message: "请求失败"});
        this.loading = false;
      })
    },
  }
}
</script>

<style scoped>
.choice-cell {
  padding-left: 0.5em !important;
  padding-right: 0.5em !important;
  border-left: none !important;
  border-right: none !important;
}
</style>