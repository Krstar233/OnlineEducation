import request from '@/utils/request'
export default {
  addCourseInfo(courseInfo){
    return request({
      url: `/eduservice/course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    });
  },
  getCourseInfoById(courseId){
    return request({
      url: `/eduservice/course/getCourseInfo/${courseId}`,
      method: 'get'
    });
  },
  updateCourseInfo(courseInfo){
    return request({
      url: `/eduservice/course/updateCourseInfo`,
      method: 'post',
      data: courseInfo
    });
  },
  getTeacherList(){
    return request({
      url: `/eduservice/teacher/findAll`,
      method: 'get'
    });
  },
  //课程确认信息 获取
  getPublishCourseInfo(courseId) {
    return request({
      url: `/eduservice/course/getPublishCourseInfo/${courseId}`,
      method: 'get'
    });
  },
  //课程最终发布
  publishCourse(courseId){
    return request({
      url: `/eduservice/course/publishCourse/${courseId}`,
      method: 'post'
    });
  },
  //获取 课程表格 信息
  findAllCourseTableInfo(){
    return request({
      url: `/eduservice/course/findAllCourseTableInfo`,
      method: 'get'
    });
  },
  //根据查询条件 获取 课程表格 信息
  queryCourseTableInfo(courseQuery){
    return request({
      url: `/eduservice/course/tableQuery`,
      method: 'post',
      data: courseQuery
    });
  },
  //根据id 删除课程
  removeCourseById(courseId){
    return request({
      url: `/eduservice/course/${courseId}`,
      method: 'delete'
    });
  }
}
