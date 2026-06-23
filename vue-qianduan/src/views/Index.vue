<template>
  <div class="common-layout">
    <el-container>
        <!-- 顶部 -->
      <el-header style="height: 90px">
        <Header> </Header>
      </el-header>
      <el-container>
        <el-aside class="pdm-sidebar pdm-sidebar--dark">
          <LeftMenu></LeftMenu>
        </el-aside>
        <el-container>
          <div class="pdm-content__wrapper">
            <el-main class="pdm-content">
               <el-card  :body-style="siteContent.siteContentHeight">
                <!--{ minHeight:'400px'}"-->
                <router-view />
              </el-card>
            </el-main>
            <el-footer class="layout-footer">
              <Footer></Footer>
            </el-footer>
            
          </div>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import Header from '../components/Header.vue'
import LeftMenu from '../components/LeftMenu.vue'
import Footer from '../components/Footer.vue'

import { ref, reactive} from 'vue';
//内容区相关常量 
const siteContent = reactive({
  documentClientHeight: 0,
  siteContentHeight: {}
})

//计算内容区域的高度
function getSiteContentHeight() {
  //窗口的高度
  siteContent.documentClientHeight = document.documentElement.clientHeight 
  //内容区域的高度
  let height = siteContent.documentClientHeight - 90 - 100;
  siteContent.siteContentHeight = { minHeight: height + "px" }

}
getSiteContentHeight();

window.onresize=function(){
    getSiteContentHeight();
}
</script>

<style lang="less">
  @import "@/style/style.less";
</style>