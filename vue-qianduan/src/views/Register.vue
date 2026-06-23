<template>
  <div class="login-container">
    <div class="login-container-content">
      <div class="login-container-content-bg">
        <div class="login-container-content-top">
          <div>
            <div style="text-align: center; padding-bottom: 25px;"></div>
            <span class="login-title">用户注册</span>
          </div>
        </div>

        <div class="login-container-content-main">
          <el-form ref="formRef" :model="form" :rules="rules" size="large" autocomplete="off">
            <el-form-item prop="username">
              <el-input
                v-model="form.username"
                :prefix-icon="User"
                clearable
                autocomplete="off"
                style="height: 40px; font-size: 16px"
                placeholder="用户名"
              />
            </el-form-item>
            <el-form-item prop="userpwd">
              <el-input
                v-model="form.userpwd"
                :prefix-icon="Lock"
                clearable
                autocomplete="new-password"
                style="height: 40px; font-size: 16px"
                placeholder="密码"
                show-password
                type="password"
              />
            </el-form-item>
            <el-form-item prop="confirmPassword">
              <el-input
                v-model="form.confirmPassword"
                :prefix-icon="Lock"
                clearable
                autocomplete="new-password"
                style="height: 40px; font-size: 16px"
                placeholder="确认密码"
                show-password
                type="password"
                @keyup.enter="onSubmit"
              />
            </el-form-item>
            <el-form-item prop="name">
              <el-input
                v-model="form.name"
                clearable
                style="height: 40px; font-size: 16px"
                placeholder="姓名"
              />
            </el-form-item>
            <el-form-item prop="sex">
              <el-select
                v-model="form.sex"
                placeholder="请选择性别"
              >
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
            <el-form-item prop="tel">
              <el-input
                v-model="form.tel"
                clearable
                style="height: 40px; font-size: 16px"
                placeholder="手机号"
                @keyup.enter="onSubmit"
              />
            </el-form-item>
            <el-form-item prop="headurl">
              <el-input
                v-model="form.headurl"
                clearable
                style="height: 40px; font-size: 16px"
                placeholder="头像地址（可选）"
              />
            </el-form-item>
            <el-form-item>
              <el-button
                style="width: 100%; height: 40px; font-size: 16px"
                type="primary"
                :loading="btnLoading"
                @click="onSubmit"
              >
                注 册
              </el-button>
            </el-form-item>
            <div class="register-entry">
              <span>已有账号？</span>
              <el-link type="primary" @click="router.push('/login')">返回登录</el-link>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { Lock, User } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { authApi } from '@/api/auth-api'

const router = useRouter()
const formRef = ref<FormInstance>()
const btnLoading = ref(false)

const form = reactive({
  username: '',
  userpwd: '',
  confirmPassword: '',
  name: '',
  sex: '',
  tel: '',
  headurl: '',
})

const validateConfirmPassword = (_rule: unknown, value: string, callback: (error?: Error) => void) => {
  if (!value) {
    callback(new Error('请再次输入密码'))
    return
  }
  if (value !== form.userpwd) {
    callback(new Error('两次输入的密码不一致'))
    return
  }
  callback()
}

const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  userpwd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  confirmPassword: [{ validator: validateConfirmPassword, trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
  tel: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
}

async function onSubmit() {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (!valid) return

    btnLoading.value = true
    try {
      await authApi.register({
        username: form.username,
        userpwd: form.userpwd,
        name: form.name,
        sex: form.sex,
        tel: form.tel,
        headurl: form.headurl,
      })
      ElMessage.success('注册成功，请登录')
      router.push('/login')
    } finally {
      btnLoading.value = false
    }
  })
}
</script>

<style lang="less">
@import '../style/login.less';

.register-entry {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin-top: 8px;
  color: #606266;
}
</style>