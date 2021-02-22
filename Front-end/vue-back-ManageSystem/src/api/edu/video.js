import request from '@/utils/request'
export default {
  //根据ID获取小节信息
  getVideoById(videoId){
    return request({
      url: `/eduservice/edu-video/${videoId}`,
      method: 'get',
    });
  },
  //添加小节
  addVideo(video){
    return request({
      url: `/eduservice/edu-video/addVideo`,
      method: 'post',
      data: video
    });
  },
  //修改章节
  updateVideo(video){
    return request({
      url: `/eduservice/edu-video/updateVideo`,
      method: 'post',
      data: video
    });
  },
  //删除章节
  deleteVideo(videoId){
    return request({
      url: `/eduservice/edu-video/${videoId}`,
      method: 'delete',
    });
  }
}
