<template>
    <div class="version">
      <a target="_blank" class="copyright" href="https://github.com/MengyaoZ86"> ©2026-{{ currentYear }} Springboot ZMY </a>
      <a target="_blank" class="version" href="https://github.com/MengyaoZ86"> HCS  </a>
    </div>
  </template>
  <script setup>
    import dayjs from 'dayjs';
    const currentYear = dayjs().year();
  </script>
  
  <style lang="less" scoped>
    .version,.copyright {
      font-size: 14px;
      font-size: 14px;
      color: rgba(0, 0, 0, 0.45);
      a:hover {
        color:#0a0e14;
      }
    }
  </style>