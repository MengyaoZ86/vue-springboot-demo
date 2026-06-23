<template>
    <el-dialog
      v-model="visible"
      :title="!form.id?'新增':'修改'"
      width="40%">
    <el-form :rules="rules" :model="form" ref="fromRef"  label-width="100px">
<el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" :readonly="ureadonly" style="width:50%"/>
            </el-form-item>
            <el-form-item label="密码" prop="userpwd">
                <el-input  v-model="form.userpwd" type="password" style="width:50%"/>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" style="width:50%"/>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
                <el-select style="width:50%" placeholder="请选择" v-model="form.sex">
                    <el-option label="男" value="男"> </el-option>
                    <el-option label="女" value="女"> </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="电话" prop="tel">
                <el-input v-model="form.tel" style="width:50%"/>
            </el-form-item>
            <el-form-item label="头像" prop="headurl">
                <el-input v-model="form.headurl" style="width:50%"/>
            </el-form-item>
        </el-form>


      <template #footer>
        <span class="dialog-footer">
          <el-button @click="visible = false">Cancel</el-button>
          <el-button type="primary" @click="onsubmit()" :loading="btnloading" >Confirm</el-button>
        </span>
      </template>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { reactive } from 'vue'
import {adminApi} from '@/api/admin-api'
import { Refresh } from '@element-plus/icons-vue';
import { id } from 'element-plus/es/locales.mjs';
 import { ElMessage } from 'element-plus'

//公开父组件可可以调用的方法
defineExpose({showModel});
// 控制弹窗显示隐藏
const visible = ref(false);
const btnloading=ref(false);
//自定义事件
const emit=defineEmits(['Refresh']);

const ureadonly=ref(false);
function showModel(row){
    if(row){
        ureadonly.value=true;
         Object.assign(form,row);
    }
    else{
        ureadonly.value=false;
      Object.assign(form,formDefault);  
    }
    
    visible.value=true;
}

const fromRef=ref();
// 3. 表单初始值定义
const formDefault = {
  id: undefined,
  username: undefined,
  userpwd: undefined,
  name: undefined,
  sex: undefined,
  tel: undefined,
  headurl: undefined
}

// 4. 使用 reactive 初始化表单
let form = reactive({ ...formDefault });

const rules=reactive({
    username: [
        {
            required:true,
            message :"请输入用户名",
            trigger:"blur"
        }
    ],
    userpwd: [
        {
            required:true,
            message :"请输入密码",
            trigger:"blur"
        }
    ],
    name: [
        {
            required:true,
            message :"请输入姓名",
            trigger:"blur"
        }
    ],
    sex: [
        {
            required:true,
            message :"性别",
            trigger:"change"
        }
    ],
    tel: [
        {
            required:true,
            message :"请输入电话",
            trigger:"blur"
        }
    ],
    headurl: [
        {
            required:true,
            message :"头像",
            trigger:"blur"
        }
    ],
})
function onsubmit(){
    try{
    btnloading.value=true;
    fromRef.value.validate().then(async()=>{
        if(form.id){
            await adminApi.update(form);
        }else{
             await adminApi.add(form);
        
        }
        ElMessage.success(`${form.id?'修改':'新增'}成功`)
        btnloading.value=false;
        visible.value=false;
        emit('refresh');
    })
    }
    catch(e){

    }
    finally{
        btnloading.value=false;
    }
console.log("form.username"+form.username)
}

</script>