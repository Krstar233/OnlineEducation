<template>
  <div>
    <h1 style="text-align: center">发布新课程</h1>
    <div style="width: 80%; margin: 0px 10%">
      <el-steps :active="3">
        <el-step title="填写课程基本信息"></el-step>
        <el-step title="创建课程大纲"></el-step>
        <el-step title="最终发布"></el-step>
      </el-steps>
      <div class="ccInfo">
        <img :src="coursePublishInfo.cover">
        <div class="main">
          <h2>{{ coursePublishInfo.title }}</h2>
          <p class="gray"><span>共{{ coursePublishInfo.lessonNum }}课时</span></p>
          <p><span>所属分类：{{ coursePublishInfo.subjectLevelOne }} — {{ coursePublishInfo.subjectLevelTwo }}</span></p>
          <p>课程讲师：{{ coursePublishInfo.teacherName }}</p>
          <h3 class="red">￥{{ coursePublishInfo.price }}</h3>
        </div>
      </div>
      <el-button style="margin-top: 12px;" @click="last">返回修改</el-button>
      <el-button type="primary" style="margin-top: 12px;" @click="publishCourse">发布课程</el-button>
    </div>
  </div>
</template>

<script>
  import course from "@/api/edu/course";
    export default {
        name: "publish",
      data() {
        return {
          courseId:'',
          coursePublishInfo: {}
        };
      },
      created() {
          if (this.$route.params && this.$route.params.id){
            this.courseId = this.$route.params.id
            this.getPublishCourseInfo()
          }
      },
      methods: {
        //根据课程id查询 课程确认信息
        getPublishCourseInfo(){
          course.getPublishCourseInfo(this.courseId)
            .then(res=>{
              this.coursePublishInfo = res.data.coursePublishInfo
            })
        },
        publishCourse() {
          course.publishCourse(this.courseId)
            .then(res=>{
              this.$message({
                type: "success",
                message: "课程发布成功！"
              })
              this.$router.push({path: "/course/list"})
            })
        },
        last() {
          this.$router.push({path:"/course/chapter/"+this.$route.params.id})
        }
      }
    }
</script>

<style scoped>
  .ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
  }
  .ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
  }
  .ccInfo .main {
    margin-left: 520px;
  }

  .ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
  }
  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }

  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }
  .ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
  }
</style>
