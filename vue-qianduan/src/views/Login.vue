<template>
  <div class="login-container">
    <div class="login-container-content">
      <div class="login-container-content-bg">
        <div class="login-container-content-top">
          <div>
            <div style="text-align: center; padding-bottom: 25px;"></div>
            <span class="login-title"> Vue + Springboot</span>
          </div>
        </div>

        <div class="login-container-content-main">
          <el-form :model="form" ref="formRef" :rules="rules" size="large">
            <el-form-item prop="username">
              <el-input
                v-model="form.username"
                :prefix-icon="User"
                style="width: 368px; height: 40px; font-size: 16px"
                placeholder="用户名"
              />
            </el-form-item>
            <el-form-item prop="userpwd">
              <el-input
                v-model="form.userpwd"
                :prefix-icon="Lock"
                style="width: 368px; height: 40px; font-size: 16px"
                placeholder="密码"
                show-password
                type="password"
                @keyup.enter="onSubmit"
              />
            </el-form-item>
            <el-form-item prop="captchaCode">
              <div class="captcha-row">
                <el-input
                  v-model="form.captchaCode"
                  style="width: 220px; height: 40px; font-size: 16px"
                  placeholder="验证码"
                  @keyup.enter="onSubmit"
                />
                <img
                  class="captcha-img"
                  :src="captchaImage"
                  alt="验证码"
                  title="点击刷新验证码"
                  @click="refreshCaptcha"
                />
              </div>
            </el-form-item>
            <el-form-item>
              <el-button
                style="width: 368px; height: 40px; font-size: 16px"
                type="primary"
                :loading="btnLoading"
                @click="onSubmit"
              >
                登 录
              </el-button>
            </el-form-item>
            <div class="register-entry">
          <span>还没有账号？</span>
         <el-link type="primary" @click="router.push('/register')">立即注册</el-link>
          </div>

          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { authApi } from '@/api/auth-api'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const formRef = ref<FormInstance>()
const btnLoading = ref(false)
const captchaImage = ref('')
const captchaKey = ref('')

const form = reactive({
  username: '',
  userpwd: '',
  captchaCode: '',
})

const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  userpwd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  captchaCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
}

async function refreshCaptcha() {
  try {
    const res: any = await authApi.getCaptcha()
    captchaKey.value = res.data.captchaKey
    captchaImage.value = res.data.captchaImage
  } catch {
    ElMessage.error('获取验证码失败')
  }
}

async function onSubmit() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    btnLoading.value = true
    try {
      await userStore.login({
        username: form.username,
        userpwd: form.userpwd,
        captchaCode: form.captchaCode,
        captchaKey: captchaKey.value,
      })
      ElMessage.success('登录成功')
      const redirect = (route.query.redirect as string) || '/welcome'
      router.push(redirect)
    } catch {
      refreshCaptcha()
      form.captchaCode = ''
    } finally {
      btnLoading.value = false
    }
  })
}

onMounted(() => {
  refreshCaptcha()
})
</script>

<style lang="less">
@import '../style/login.less';

.captcha-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.captcha-img {
  width: 120px;
  height: 40px;
  cursor: pointer;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
}

.register-entry {
  display: flex;
  justify-content: center;
  gap: 8px;
  color: #606266;
}

</style>
