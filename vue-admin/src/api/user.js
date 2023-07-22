import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/boss/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/boss/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/boss/logout',
    method: 'post'
  })
}
