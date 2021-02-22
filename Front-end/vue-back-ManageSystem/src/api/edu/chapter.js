import request from '@/utils/request'
export default {
  getChapterList(courseId){
    return request({
      url: `/eduservice/edu-chapter/getChapterVideo/${courseId}`,
      method: 'get'
    });
  },
  //根据id获取章节信息
  getChapterById(chapterId){
    return request({
      url: `/eduservice/edu-chapter/getChapterInfo/${chapterId}`,
      method: 'get'
    });
  },
  //添加章节
  addChapter(chapter){
    return request({
      url: `/eduservice/edu-chapter/addChapter`,
      method: 'post',
      data: chapter
    });
  },
  //修改章节
  updateChapter(chapter){
    return request({
      url: `/eduservice/edu-chapter/updateChapter`,
      method: 'post',
      data: chapter
    });
  },
  //删除章节
  deleteChapter(courseId){
    return request({
      url: `/eduservice/edu-chapter/${courseId}`,
      method: 'delete',
    });
  }
}
