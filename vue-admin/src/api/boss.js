import request from '@/utils/request'

export default {
  update(bossForm) {
    return request({
      url: 'boss/passedit',
      method: 'get',
      params: {
        token: bossForm.token,
        newPassword: bossForm.newPassword,
        oldPassword: bossForm.oldPassword
      }
    });
  },

}
