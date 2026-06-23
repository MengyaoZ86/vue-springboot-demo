import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import Constants from '@/utils/constants'
import { getLocalStorage, saveLocalStorage, clearLocalStorage } from '@/utils/utils'
import { authApi } from '@/api/auth-api'
import router from '@/router'

export interface UserInfo {
  id: number
  username: string
  name: string
  headurl?: string
}

export const useUserStore = defineStore('user', () => {
  const token = ref(getLocalStorage(Constants.USER_TOKEN))
  const userInfo = ref<UserInfo | null>(loadUserInfo())

  const isLoggedIn = computed(() => !!token.value)

  function loadUserInfo(): UserInfo | null {
    const raw = getLocalStorage(Constants.USER_INFO)
    if (!raw) return null
    try {
      return JSON.parse(raw)
    } catch {
      return null
    }
  }

  function setUser(data: { token: string; id: number; username: string; name: string; headurl?: string }) {
    token.value = data.token
    userInfo.value = {
      id: data.id,
      username: data.username,
      name: data.name,
      headurl: data.headurl,
    }
    saveLocalStorage(Constants.USER_TOKEN, data.token)
    saveLocalStorage(Constants.USER_INFO, JSON.stringify(userInfo.value))
  }

  async function login(params: {
    username: string
    userpwd: string
    captchaCode: string
    captchaKey: string
  }) {
    const res: any = await authApi.login(params)
    setUser(res.data)
    return res
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    clearLocalStorage()
    router.push('/login')
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    login,
    logout,
    setUser,
  }
})
