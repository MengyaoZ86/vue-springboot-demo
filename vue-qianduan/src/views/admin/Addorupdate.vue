<template>
  <el-dialog v-model="visible" :title="form.id ? '修改管理员' : '新增管理员'" width="520px">
    <el-form ref="formRef" :rules="rules" :model="form" label-width="90px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" :readonly="isEditMode" clearable />
      </el-form-item>
      <el-form-item label="密码" prop="userpwd">
        <el-input v-model="form.userpwd" show-password type="password" clearable />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" clearable />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="form.sex" placeholder="请选择性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
      <el-form-item label="电话" prop="tel">
        <el-input v-model="form.tel" clearable />
      </el-form-item>
      <el-form-item label="头像" prop="headurl">
        <el-input v-model="form.headurl" clearable placeholder="可选" />
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" :loading="btnLoading" @click="onSubmit">保存</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { adminApi } from '@/api/admin-api'

interface AdminForm {
  id?: number
  username: string
  userpwd: string
  name: string
  sex: string
  tel: string
  headurl: string
}

const visible = ref(false)
const btnLoading = ref(false)
const isEditMode = ref(false)
const emit = defineEmits(['refresh'])
const formRef = ref<FormInstance>()

const formDefault: AdminForm = {
  id: undefined,
  username: '',
  userpwd: '',
  name: '',
  sex: '',
  tel: '',
  headurl: '',
}

const form = reactive<AdminForm>({ ...formDefault })

const rules: FormRules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  userpwd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  sex: [{ required: true, message: '请选择性别', trigger: 'change' }],
  tel: [{ required: true, message: '请输入电话', trigger: 'blur' }],
})

function resetForm() {
  Object.assign(form, formDefault)
  formRef.value?.clearValidate()
}

function showModel(row?: Partial<AdminForm>) {
  resetForm()
  if (row?.id) {
    isEditMode.value = true
    Object.assign(form, row)
  } else {
    isEditMode.value = false
  }
  visible.value = true
}

async function onSubmit() {
  if (!formRef.value) return
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  btnLoading.value = true
  try {
    if (form.id) {
      await adminApi.update(form)
    } else {
      await adminApi.add(form)
    }
    ElMessage.success(`${form.id ? '修改' : '新增'}成功`)
    visible.value = false
    emit('refresh')
  } finally {
    btnLoading.value = false
  }
}

defineExpose({ showModel })
</script>