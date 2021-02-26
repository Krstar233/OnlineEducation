import request from '@/utils/request'

export default {
  //注册
  getPlayAuth(vid){
    return request({
      url: `/eduvod/video/get-play-auth/${vid}`,
      method: 'get'
    })
  }
}
