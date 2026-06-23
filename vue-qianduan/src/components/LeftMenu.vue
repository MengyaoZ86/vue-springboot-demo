<template>
  <div class="pdm-sidebar__inner">
    <el-menu
      :collapse-transition="false"
      :default-active="activeMenu"
      class="pdm-sidebar__menu"
      background-color="#263238"
      active-text-color="#fff"
      text-color="#8a979e"
      router
    >
      <el-menu-item index="/welcome">
        <el-icon><Document /></el-icon>
        <span>首页</span>
      </el-menu-item>
      <el-sub-menu index="info" :popper-class="'pdm-sidebar--dark-popper'">
        <template #title>
          <el-icon><Document /></el-icon>
          <span>信息管理</span>
        </template>
        <el-menu-item index="/admin">
          <el-icon><Document /></el-icon>
          <span>管理员信息管理</span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { Document } from '@element-plus/icons-vue'

const route = useRoute()
const activeMenu = computed(() => route.path)
</script>