import request from '@/utils/request'

export default {
  getIndexList(){
    return request({
      url: `/eduservice/indexfront/index`,
      method: 'get'
    })
  }
}
