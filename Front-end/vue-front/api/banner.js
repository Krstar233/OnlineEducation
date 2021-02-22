import request from '@/utils/request'

export default {
  getList(){
    return request({
      url: `/educms/bannerfront/getAllBanner`,
      method: 'get'
    })
  }
}
