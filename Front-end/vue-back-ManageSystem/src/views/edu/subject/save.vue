<template>
  <div style="padding: 14px">
    <div>
      <span style="font-weight: bold">信息描述</span>
      <el-tag type="info">Excel模板声明</el-tag>
      <a href="/static/data.xlsx">
        <el-tag>
          <i class="el-icon-download"></i>
          点击下载模板
        </el-tag>
      </a>
    </div>
    <h3>选择Excel文件</h3>
    <el-upload
      class="upload-demo"
      ref="upload"
      :action="BASE_API+'/eduservice/edu-subject/addSubject'"
      :on-success="handleSuccess"
      :on-error="handleError"
      :file-list="fileList"
      :auto-upload="false"
      :limit="1">
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
    </el-upload>
  </div>
</template>

<script>
    export default {
      name: "save",
      data() {
        return {
          fileList: [],
          BASE_API: process.env.BASE_API,
        };
      },
      methods: {
        submitUpload() {
          this.$refs.upload.submit();
        },
        handleSuccess(res) {
          console.log(res)
          this.$message({
            message: res.message,
            type: 'success'
          });
          this.fileList = []
        },
        handleError(err) {
          console.log(err);
          this.$message.error(err.message);
        }
      }
    }
</script>

<style scoped>

</style>
