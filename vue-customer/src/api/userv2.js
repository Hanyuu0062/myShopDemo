import request from '@/utils/request'

export default {
  update(userForm) {
    return request({
      url: 'user/passedit',
      method: 'get',
      params: {
        token: userForm.token,
        newPassword: userForm.newPassword,
        oldPassword: userForm.oldPassword
      }
    });
  },

  buyGood(cartForm) {
    return request({
      url: 'user/goodbuy',
      method: 'post',
      data: cartForm
    });
  },

  sign(signForm){
    return request({
      url: 'user/sign',
      method: 'post',
      data: signForm
    });
  },

  reset(form){
    return request({
      url: 'user/resetps',
      method: 'get',
      params: {
        username:form.username,
        email:form.email
      }
    });
  }
}
