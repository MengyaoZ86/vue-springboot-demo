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
}
