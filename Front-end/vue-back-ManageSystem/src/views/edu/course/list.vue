<template>
  <div class="app-container">
<!--    查询表单-->
    <el-form :inline="true" :model="courseQuery" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="courseQuery.status" placeholder="状态">
          <el-option label="已发布" value="Normal"></el-option>
          <el-option label="未发布" value="Draft"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="课程名称" />
      <el-table-column label="课程状态">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>
      <el-table-column prop="lessonNum" label="课时数" />
      <el-table-column prop="gmtCreate" label="添加时间" />
      <el-table-column prop="viewCount" label="浏览次数" />
      <el-table-column label="操作" width="200" align="left">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
          </router-link>
          <br>
          <el-button type="text" style="color: #F56C6C;" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="getList">
    </el-pagination>
  </div>
</template>

<script>
  import course from "@/api/edu/course";
  export default {
    name: "list",
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'gray',
          deleted: 'danger'
        }
        return statusMap[status]
      }
    },
    data() {
      return {
        list: null,
        page: 1,
        limit: 10,
        total: 0,
        courseQuery: {}
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList(){
        course.queryCourseTableInfo(this.courseQuery)
          .then(res => {
            this.list = res.data.rows;
            this.total = res.data.total;
          }).catch(err => {
          console.log(err)
        });
      },
      reset(){
        this.courseQuery = {}
        this.getList()
      },
      removeDataById(id){
        this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          course.removeCourseById(id).then(res => {
            //提示信息
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            //回到列表页面
            this.getList()
          }).catch(err => console.log(err));
        })
      }
    }
  }
</script>

<style scoped>

</style>
