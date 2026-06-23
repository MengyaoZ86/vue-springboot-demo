<script setup lang="ts">
import { Edit } from '@element-plus/icons-vue'
</script>

<template>
<div class="welcome">
    <el-icon :size="20"">
      <!-- 不能在里面 color="red -->
      <Edit />
    </el-icon>
    Hello!
    <el-button type="primary">Primary</el-button>
</div>
</template>