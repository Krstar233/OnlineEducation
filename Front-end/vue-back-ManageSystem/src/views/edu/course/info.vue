<template>
  <div>
    <h1 style="text-align: center">发布新课程</h1>
    <el-steps :active="1" align-center>
      <el-step title="填写课程基本信息"></el-step>
      <el-step title="创建课程大纲"></el-step>
      <el-step title="最终发布"></el-step>
    </el-steps>
    <div style="padding: 30px">
      <el-form label-width="80px">
        <el-form-item label="课程标题">
          <el-input v-model="courseInfo.title" placeholder="示例：Java Web 编程"></el-input>
        </el-form-item>
        <el-form-item label="课程讲师">
          <el-select v-model="courseInfo.teacherId" placeholder="请选择">
            <el-option
              v-for="item in teacherList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程分类">
          <el-select v-model="courseInfo.subjectParentId" placeholder="一级分类" @change="getTwoSubject">
            <el-option
              v-for="item in subjectOneList"
              :key="item.id"
              :label="item.label"
              :value="item.id">
            </el-option>
          </el-select>
          <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
            <el-option
              v-for="item in subjectTwoList"
              :key="item.id"
              :label="item.label"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="总课时">
          <el-input-number v-model="courseInfo.lessonNum" controls-position="right" :min="0" :max="99999999"></el-input-number>
        </el-form-item>
        <el-form-item label="课程封面">
          <el-upload
            class="avatar-uploader"
            :action='BASE_API+"/eduoss/file/upload"'
            :on-success="handleSuccess"
            :on-error="handleError"
            :before-upload="handleBeforeUpload"
            :show-file-list="false"
          >
            <img v-if="courseInfo.cover" :src="courseInfo.cover" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="课程简介">
          <tinymce :height="300" v-model="courseInfo.description" style="width: 95%;"/>
        </el-form-item>
        <el-form-item label="课程价格">
          <el-input-number v-model="courseInfo.price" controls-position="right" :min="0" :max="99999999"></el-input-number>
          <span style="margin-left: 6px;">元</span>
        </el-form-item>
      </el-form>
      <el-button type="primary" style="margin: 12px 80px;" @click="next">保存并下一步</el-button>
    </div>
  </div>
</template>

<script>
  import course from "@/api/edu/course";
  import subject from "@/api/edu/subject";
  import Tinymce from "@/components/Tinymce";
    export default {
        name: "info",
      components: { Tinymce },
      data() {
        return {
          courseInfo: {
            title: "",
            lessonNum: 0,
            description: "",
            price: 0,
            teacherId: "",
            subjectId: "", //二级分类 id
            subjectParentId: "", //一级分类id
            cover: ""
          },
          BASE_API: process.env.BASE_API,
          teacherList: [], //所有讲师数据
          subjectOneList: [],
          subjectTwoList: []
        };
      },
      created() {
        //初始化
        this.initData()
      },
      watch:{ //监听路由变化
        $route(to, from){
          this.initData();
        }
      },
      methods: {
        //上传课程封面成功
        handleSuccess(res){
          this.courseInfo.cover = res.data.url
          this.$message({
            type: 'success',
            message: '上传成功！'
          })
        },
        handleError(err){
          this.$message.error('上传失败！')
        },
        //上传课程封面之前的处理
        handleBeforeUpload(file){
          const isJPG = file.type === 'image/jpeg'
          const isPNG = file.type === 'image/png'
          const isLt2M = file.size / 1024 /1024 < 2;

          if (!isJPG && !isPNG){
            this.$message.error("只支持上传jpg/png文件！")
          }
          else if (!isLt2M){
            this.$message.error("上传的图片不能超过2M！")
          }
          return (isJPG || isPNG) && isLt2M;
        },
        // 初始化操作
        initData(){
          //查询所有讲师
          course.getTeacherList()
            .then(res=>{
              this.teacherList = res.data.items;
              //查询所有分类信息（一级分类）
              subject.getSubjectList()
                .then(res=>{
                  this.subjectOneList = res.data.subjectTree;
                  //课程数据回显
                  if (this.$route.params && this.$route.params.id) {
                    course.getCourseInfoById(this.$route.params.id)
                      .then(res=>{
                        this.courseInfo = res.data.courseInfo;
                        for (let i = 0; i < this.subjectOneList.length; i++) {
                          if (this.subjectOneList[i].id === this.courseInfo.subjectParentId) {
                            this.subjectTwoList = this.subjectOneList[i].children;
                          }
                        }
                      })
                  }else this.courseInfo = {
                    title: "",
                    lessonNum: 0,
                    description: "",
                    price: 0,
                    teacherId: "",
                    subjectId: "", //二级分类 id
                    subjectParentId: "", //一级分类id
                    cover: ""
                  }
                })
            });

        },
        //获取二级分类信息(选择一级分类触发事件)
        getTwoSubject(value){
          this.courseInfo.subjectId = ""
          for (let i = 0; i < this.subjectOneList.length; i++){
            if (this.subjectOneList[i].id === value){
              this.subjectTwoList = this.subjectOneList[i].children;
            }
          }
        },
        addCourseInfo(){
          course.addCourseInfo(this.courseInfo)
            .then(res=>{
              this.$message({
                type: 'success',
                message: '课程信息保存成功！'
              })
              //跳转到第二步
              this.$router.push({path:"/course/chapter/"+res.data.courseId})
            })
        },
        updateCourseInfo(){
          course.updateCourseInfo(this.courseInfo)
            .then(res=>{
              this.$message({
                type: 'success',
                message: '课程信息修改成功！'
              })
              //跳转到第二步
              this.$router.push({path:"/course/chapter/"+ this.$route.params.id})
            })
        },
        next() {
          if (this.$route.params && this.$route.params.id){
            this.updateCourseInfo();
          }else {
            this.addCourseInfo();
          }
        }
      }
    }
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
