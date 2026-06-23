<template>
  <div class="header">
    <div class="header-menu">
      <div class="header-logo">
        <div class="header-title">Vue+springboot脚手架 ZhangMengyao</div>
      </div>
      <div class="header-rinfo">
        当前用户：
        <span>{{ displayName }}</span>
        <span class="header-exit">
          <a @click="handleLogout">退出</a>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const displayName = computed(() => {
  return userStore.userInfo?.name || userStore.userInfo?.username || '未登录'
})

function handleLogout() {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    userStore.logout()
  })
}
</script>
