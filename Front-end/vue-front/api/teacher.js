import request from '@/utils/request'

export default {
  //注册
  pageTeacher(current, limit){
    return request({
      url: `/eduservice/teacher/pageTeacher/${current}/${limit}`,
      method: 'get'
    })
  },
  getById(teacherId){
    return request({
      url: `/eduservice/teacher-front/${teacherId}`,
      method: 'get'
    })
  }
}
