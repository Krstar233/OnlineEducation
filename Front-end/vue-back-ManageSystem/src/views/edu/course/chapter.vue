<template>
  <div>
    <h1 style="text-align: center">发布新课程</h1>
    <div style="width: 80%; margin: 0px 10%">
      <el-steps :active="2">
        <el-step title="填写课程基本信息"></el-step>
        <el-step title="创建课程大纲"></el-step>
        <el-step title="最终发布"></el-step>
      </el-steps>
      <el-button type="text" @click="onDialogOpen" style="display: block">
        添加章节
      </el-button>
      <ul class="chanpterList">
        <li v-for="chapter in chapterList" :key="chapter.id" class="">
          <p>
            {{ chapter.title }}
            <span class="acts">
              <el-button type="text" @click="openVideoDialog(chapter.id)">添加小节</el-button>
              <el-button type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
              <el-button type="text" style="color: #F56C6C" @click="removeChapterById(chapter.id)">删除</el-button>
            </span>
          </p>
          <ul class="chanpterList videoList">
            <li v-for="video in chapter.children" :key="video.id" class="">
              <p>
                {{ video.title }}
                <span class="acts">
                  <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                  <el-button type="text" style="color: #F56C6C" @click="removeVideoById(video.id)">删除</el-button>
                </span>
              </p>
            </li>
          </ul>
        </li>
      </ul>
      <!-- Form -->
      <el-dialog title="添加章节" :visible.sync="dialogFormVisible">
        <el-form :model="chapter" :label-width="formLabelWidth">
          <el-form-item label="章节标题">
            <el-input v-model="chapter.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="章节排序">
            <el-input-number v-model="chapter.sort" controls-position="right" :min="0" :max="99999999"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
      </el-dialog>

      <!-- Form -->
      <el-dialog title="添加小节" :visible.sync="dialogVideoVisible">
        <el-form :model="video" :label-width="formLabelWidth">
          <el-form-item label="课时标题">
            <el-input v-model="video.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="课时排序">
            <el-input-number v-model="video.sort" controls-position="right" :min="0" :max="99999999"></el-input-number>
          </el-form-item>
          <el-form-item label="是否免费">
            <el-radio-group v-model="video.isFree">
              <el-radio :label="false">默认</el-radio>
              <el-radio :label="true">免费</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="上传视频">
            <el-upload
              class="upload-demo"
              :action="BASE_API + '/eduvod/video/uploadAliyunVideo'"
              multiple
              :limit="1"
              :on-success="handleVideoUploadSuccess"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :file-list="fileList">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">上传视频的大小不能超过5GB</div>
            </el-upload>
          </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVideoVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
        </div>
      </el-dialog>
      <el-button style="margin-top: 12px;" @click="last">上一步</el-button>
      <el-button type="primary" style="margin-top: 12px;" @click="next">下一步</el-button>
    </div>
  </div>
</template>

<script>
  import chapter from "@/api/edu/chapter";
  import video from "@/api/edu/video";
    export default {
        name: "chapter",
      data() {
        return {
          fileList: [],
          chapterList: [],
          courseId: "",
          dialogFormVisible: false,
          dialogVideoVisible: false,
          chapter: {
            title: "",
            sort: 0
          },
          BASE_API: process.env.BASE_API,
          video: {
            title: "",
            sort: 0,
            isFree: false,
            videoSourceId: "",
            videoOriginalName: ""
          },
          formLabelWidth: '120px'
        };
      },
      created() {
          if (this.$route.params && this.$route.params.id){
            this.courseId = this.$route.params.id
            this.getChapterList()
          }
      },
      methods: {
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        beforeRemove(file, fileList) {
          return this.$confirm(`确定移除 ${ file.name }？`);
        },
        // 小节 -- 视频上传成功
        handleVideoUploadSuccess(res, file){
          this.video.videoSourceId = res.data.videoId
          this.video.videoOriginalName = file.name
        },
        //打开 小节 => 编辑
        openEditVideo(videoId){
          //清空上传的视频文件列表
          // this.fileList = []
          this.dialogVideoVisible = true
          video.getVideoById(videoId)
            .then(res=>{
              this.video = res.data.video
            })
        },
        //打开 添加小节
        openVideoDialog(chapterId){
          //清空video表单
          this.video = {
              title: "",
              sort: 0,
              isFree: false,
              videoSourceId: "",
              videoOriginalName: ""
          }
          //显示弹框
          this.dialogVideoVisible = true
          //设置章节Id
          this.video.chapterId = chapterId
        },
        saveOrUpdateVideo(){
          //设置课程Id
          this.video.courseId = this.courseId
          if (!this.video.id){
            this.addVideo()
          }else {
            this.updateVideo()
          }
        },
        //添加小节
        addVideo(){
          console.log(this.video)
          video.addVideo(this.video)
            .then(res=>{
              //关弹框
              this.dialogVideoVisible = false;
              //提示信息
              this.$message({
                type: 'success',
                message: '小节添加成功！'
              })
              //刷新页面
              this.getChapterList();
            });
        },
        //修改小节
        updateVideo(){
          console.log(this.video)
          video.updateVideo(this.video)
            .then(res=>{
              //关弹框
              this.dialogVideoVisible = false;
              //提示信息
              this.$message({
                type: 'success',
                message: '小节修改成功！'
              })
              //刷新页面
              this.getChapterList();
            });
        },
        //删除章节
        removeChapterById(id){
          this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            chapter.deleteChapter(id).then(res => {
              //提示信息
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              //刷新列表数据
              this.getChapterList()
            }).catch(err => console.log(err));
          })
        },
        //删除小节
        removeVideoById(id){
          this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            video.deleteVideo(id).then(res => {
              //提示信息
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              //刷新列表数据
              this.getChapterList()
            }).catch(err => console.log(err));
          })
        },
        //添加章节
        addChapter(){
          chapter.addChapter(this.chapter)
            .then(res=>{
              //关弹框
              this.dialogFormVisible = false;
              //提示信息
              this.$message({
                type: 'success',
                message: '章节添加成功！'
              })
              //刷新页面
              this.getChapterList();
            });
        },
        updateChapter(){
          chapter.updateChapter(this.chapter)
            .then(res=>{
              //关弹框
              this.dialogFormVisible = false;
              //提示信息
              this.$message({
                type: 'success',
                message: '章节修改成功！'
              })
              //刷新页面
              this.getChapterList();
            });
        },
        //修改章节时
        openEditChapter(chapterId){
          this.dialogFormVisible = true
          chapter.getChapterById(chapterId)
            .then(res=>{
              this.chapter = res.data.chapter
            })
        },
        //弹框打开时
        onDialogOpen(){
          this.dialogFormVisible = true
          this.chapter= {
            title: "",
            sort: 0
          }
        },
        //保存或者更新章节
        saveOrUpdate(){
          this.chapter.courseId = this.courseId
          if (!this.chapter.id){
            this.addChapter()
          }else {
            this.updateChapter()
          }
        },
        getChapterList(){
          chapter.getChapterList(this.courseId)
            .then(res=>{
              this.chapterList = res.data.items;
            })
        },
        next() {
          this.$router.push({path:"/course/publish/"+this.$route.params.id})
        },
        last(){
          this.$router.push({path:"/course/info/"+this.$route.params.id})
        }
      }
    }
</script>

<style scoped>
  .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>
