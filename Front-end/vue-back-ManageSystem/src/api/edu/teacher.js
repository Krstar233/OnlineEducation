import request from '@/utils/request'


export default {
  getTeacherListPage(current, limit, teacherQuery){
    return request({
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery
    });
  },
  removeTeacherById(id){
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
    });
  },
  save(teacher){
    return request({
      url: "/eduservice/teacher",
      method: 'post',
      data: teacher
    });
  },
  updateById(teacher){
    return request({
      url: `/eduservice/teacher/${teacher.id}`,
      method: 'put',
      data: teacher
    });
  },
  getTeacherById(id){
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'get',
    });
  }
}
