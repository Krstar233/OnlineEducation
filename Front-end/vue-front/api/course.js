import request from '@/utils/request'

export default {
  getSubjectTree(){
    return request({
      url: `/eduservice/edu-subject/findAll`,
      method: 'get'
    })
  },
  getCourseList(query, current, limit){
    return request({
      url: `/eduservice/course-front/query/${current}/${limit}`,
      method: 'post',
      data: query
    })
  },
  getCourseDetail(courseId){
    return request({
      url: `/eduservice/course-front/detail/${courseId}`,
      method: 'get'
    })
  },
  getChapterVideoList(courseId){
    return request({
      url: `/eduservice/edu-chapter/getChapterVideo/${courseId}`,
      method: 'get'
    })
  }
}
