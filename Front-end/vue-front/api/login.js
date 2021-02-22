import request from '@/utils/request'

export default {
  //注册
  submitLogin(formItem){
    return request({
      url: `/educenter/member/login`,
      method: 'post',
      data: formItem
    })
  },
  getLoginInfo(){
    return request({
      url: `/educenter/member/getLoginInfo`,
      method: 'get'
    })
  }
}
