import { get, post } from '@/utils/request'

export const authApi = {
  getCaptcha: () => {
    return get('/auth/captcha', {})
  },

  login: (param: {
    username: string
    userpwd: string
    captchaCode: string
    captchaKey: string
  }) => {
    return post('/auth/login', param)
  },

  register: (param: {
    username: string
    userpwd: string
    name: string
    sex: string
    tel: string
    headurl?: string
  }) => {
    return post('/auth/register', param)
  },
}