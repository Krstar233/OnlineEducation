import request from '@/utils/request'

export default {
  //注册
  submitRegister(formItem){
    return request({
      url: `/educenter/member/register`,
      method: 'post',
      data: formItem
    })
  }
}
