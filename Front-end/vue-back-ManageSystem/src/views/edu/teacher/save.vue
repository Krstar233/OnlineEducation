<template>

  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>
        <!--
    v-show：是否显示上传组件
    :key：类似于id，如果一个页面多个图片上传控件，可以做区分
    :url：后台上传的url地址
    @close：关闭上传组件
    @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduoss/file/upload'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"></image-cropper>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import teacher from '@/api/edu/teacher'
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'

    export default {
    components: {ImageCropper, PanThumb},
      name: "save",
      data(){
        return {
          teacher: {},
          saveBtnDisabled: false,
          imagecropperShow: false,
          imagecropperKey: 0,
          BASE_API: process.env.BASE_API
        }
      },
      watch:{ //监听路由变化
        $route(to, from){
          this.init();
        }
      },
      created(){
        this.init();
      },
      methods: {
        close(){
          this.imagecropperShow = false;
          this.imagecropperKey++;
        },
        cropSuccess(data){
          this.imagecropperShow = false;
          this.teacher.avatar = data.url;
          this.imagecropperKey = this.imagecropperKey + 1;
        },
        init(){
          if (this.$route.params && this.$route.params.id){
            const id = this.$route.params.id;
            this.getTeacherInfo(id);
          }else
            this.teacher = {}
        },
        updateData(){
          teacher.updateById(this.teacher).then(res=>{
            if (res.success){
              //提示信息
              this.$message({
                type: 'success',
                message: '更新成功!'
              });
            }else {
              this.$message.error('更新失败！');
            }
          }).then(res=>{
            this.$router.push({ path: '/teacher/table' })
          }).catch(err=>{
            this.$message.error('更新失败！');
          });
          this.saveBtnDisabled = false;
        },
        getTeacherInfo(id){
          teacher.getTeacherById(id).then(res=>{
            this.teacher = res.data.teacher;
          })
        },
        saveOrUpdate(){
          this.saveBtnDisabled = true;
          if (!this.teacher.id)
            this.saveData();
          else
            this.updateData();
        },
        saveData(){
          teacher.save(this.teacher).then(res=>{
            if (res.success){
              //提示信息
              this.$message({
                type: 'success',
                message: '添加成功!'
              });
            }else {
              this.$message.error('添加失败！');
            }
          }).then(res=>{
            this.$router.push({ path: '/teacher/table' })
          }).catch(err=>{
            this.$message.error('添加失败！');
          });
          this.saveBtnDisabled = false;
        }
      }
    }
</script>

<style scoped>
</style>
