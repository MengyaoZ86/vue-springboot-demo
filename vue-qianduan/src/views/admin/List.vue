<template #default="scope">
  <div class="list-container">
    <el-form :inline="true">
      <el-form-item label="姓名">
        <el-input v-model="queryForm.name" placeholder="姓名"></el-input>
      </el-form-item>

      <el-form-item label="手机号">
        <el-input v-model="queryForm.tel" placeholder="手机号"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="onSearch()" type="primary">查询</el-button>
        <el-button @click="add()" type="primary">新增</el-button>
        <!-- el-button type="danger">删除</el-button -->
      </el-form-item>
    </el-form>

<el-divider border-style="dashed" />

    <el-table border :data="datalist" v-loading="listLoading" style="width: 100%" :header-cell-style="{background:'#f5f7fa'}">
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
  width="150">

  <template #default="scope">
    <el-button
      type="text"
      size="mini" @click="update(scope.row)">
      编辑
    </el-button>

    <el-button
      type="text"
      size="mini" @click="confirmDel(scope.row.id)">
      删除
    </el-button>
  </template>

</el-table-column>
    </el-table>

<el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next" :total="total" style="float:right; margin: 10px 20px 20px 0px ;"/>
<AddorUpdate ref="operateRef" @refresh="getlist"></AddorUpdate>
  </div>
</template>

<script setup lang="ts">
import {ref,reactive} from 'vue'
import { adminApi } from '@/api/admin-api';
import {onMounted} from 'vue'
import Constants from '@/utils/constants';
import AddorUpdate from './Addorupdate.vue';
import { Operation } from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus'

//定义相关变量
//列表加载状态
const listLoading = ref(false); 
//列表数据 
const datalist = ref([]);
//记录总条数
const total = ref(0);
//定义引用 新增或修改组件的响应式变量
const operateRef=ref();
//表单初始值
const queryFormState = {
    name: '',
    tel: '',
    pagenum: 1,
    pagesize: Constants.PAGE_SIZE,
};
//表单查询 条件 对象
const queryForm = reactive({ ...queryFormState });

//调用后台接口查询列表数据
async function getlist() {
    try {
        //先将加载状态设置为 true  页面变成加载中状态
        listLoading.value = true;
        //调用后台查询管理员列表接口
        let responseModel = await adminApi.queryPageList(queryForm,queryForm.pagenum,queryForm.pagesize);
        //定义 list 变量接收 后台返回的管理员 list数据
        const list = responseModel.data.records;
        //设置记录总条数
        total.value = responseModel.data.total;
        //设置列表数据
        datalist.value = list;
    } catch (e) {
        // ElMessage.error(e.toString());
    } finally {
        //将加载状态设置为 false  将页面的加载中隐藏
        listLoading.value = false;
    }
}

// 点击查询
function onSearch() {
    //设置当前页码为1
    queryForm.pagenum = 1;
    //调用后台接口查询列表数据
    getlist();
}

//点击分页查询
function handleCurrentChange(val:number) {
     //设置当前页码为点击的页码
    queryForm.pagenum = val;
    //调用后台接口查询列表数据
    getlist();
}

//新增管理员
function add(){
    operateRef.value.showModel();
}

function update(row){
operateRef.value.showModel(row);
}
function confirmDel(id){
    console.log(id);
ElMessageBox.confirm(
    '是否确认删除?',
    '确认删除',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  )
    .then(() => {
      del(id);
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Delete canceled 用户已取消删除',
      })
    })
}
async function del(id){
    try{
        listLoading.value = true;
    await adminApi.delete(id);
    ElMessage.success(`删除成功`);
    getlist();
    }catch(err){
    }finally{
listLoading.value = false;
    }
}


//组件渲染完成后立即调用一次查询列表
onMounted(getlist);

</script>