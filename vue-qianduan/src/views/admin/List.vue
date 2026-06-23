<template>
  <div class="list-container">
    <el-form :inline="true">
      <el-form-item label="姓名">
        <el-input v-model="queryForm.name" placeholder="姓名" clearable />
      </el-form-item>

      <el-form-item label="手机号">
        <el-input v-model="queryForm.tel" placeholder="手机号" clearable />
      </el-form-item>

      <el-form-item>
        <el-button @click="onSearch" type="primary">查询</el-button>
        <el-button @click="add" type="primary">新增</el-button>
      </el-form-item>
    </el-form>

    <el-divider border-style="dashed" />

    <el-table
      border
      :data="datalist"
      v-loading="listLoading"
      style="width: 100%"
      :header-cell-style="{ background: '#f5f7fa' }"
    >
      <el-table-column
        header-align="center"
        align="center"
        prop="username"
        label="用户名" />

      <el-table-column
        header-align="center"
        align="center"
        prop="name"
        label="姓名" />
      

      <el-table-column
        header-align="center"
        align="center"
        prop="sex"
        label="性别" />

      <el-table-column
        header-align="center"
        align="center"
        prop="tel"
        label="手机号"/>

      <el-table-column
        header-align="center"
        align="center"
        prop="headurl"
        label="头像"/>
      <el-table-column
        header-align="center"
        align="center"
        label="操作"
        width="180"
      >
        <template #default="scope">
          <el-button link type="primary" @click="update(scope.row)">编辑</el-button>
          <el-button link type="danger" @click="confirmDel(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @current-change="handleCurrentChange"
      background
      layout="total, prev, pager, next"
      :total="total"
      :page-size="queryForm.pagesize"
      :current-page="queryForm.pagenum"
      style="float: right; margin: 10px 20px 20px 0"
    />
    <AddorUpdate ref="operateRef" @refresh="getlist" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { adminApi } from '@/api/admin-api'
import Constants from '@/utils/constants'
import AddorUpdate from './Addorupdate.vue'

interface AdminRow {
  id: number
  username?: string
  name?: string
  sex?: string
  tel?: string
  headurl?: string
}

const listLoading = ref(false)
const datalist = ref<AdminRow[]>([])
const total = ref(0)
const operateRef = ref<InstanceType<typeof AddorUpdate>>()

const queryFormState = {
  name: '',
  tel: '',
  pagenum: 1,
  pagesize: Constants.PAGE_SIZE,
}
const queryForm = reactive({ ...queryFormState })

async function getlist() {
  try {
    listLoading.value = true
    const responseModel = await adminApi.queryPageList(queryForm, queryForm.pagenum, queryForm.pagesize)
    datalist.value = responseModel.data.records ?? []
    total.value = responseModel.data.total ?? 0
  } finally {
    listLoading.value = false
  }
}

function onSearch() {
  queryForm.pagenum = 1
  getlist()
}

function handleCurrentChange(val: number) {
  queryForm.pagenum = val
  getlist()
}

function add() {
  operateRef.value?.showModel()
}

function update(row: AdminRow) {
  operateRef.value?.showModel(row)
}

function confirmDel(id: number) {
  ElMessageBox.confirm(
    '是否确认删除？',
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
      del(id)
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '已取消删除',
      })
    })
}

async function del(id: number) {
  try {
    listLoading.value = true
    await adminApi.delete(id)
    ElMessage.success('删除成功')
    if (datalist.value.length === 1 && queryForm.pagenum > 1) {
      queryForm.pagenum -= 1
    }
    await getlist()
  } finally {
    listLoading.value = false
  }
}

onMounted(getlist)
</script>