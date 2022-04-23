<template>
  <div class="page__content">
    <h1 class="page__title">视频管理</h1>
    <br />
    <div class="breadcrumbs">
      <h4>无花果后台 / 视频管理</h4>
      <el-button type="primary" plain>添加影视</el-button>
    </div>
    <el-divider />
    <main>
      <table class="container">
        <thead>
          <tr>
            <th><h1>ID</h1></th>
            <th><h1>视频名</h1></th>
            <th><h1>封面</h1></th>
            <th><h1>视频</h1></th>
            <th><h1>描述</h1></th>
            <th><h1>上映时间</h1></th>
            <th><h1>类型</h1></th>
            <th><h1>评分</h1></th>
            <th><h1>操作</h1></th>
          </tr>
        </thead>
        <tbody></tbody>
      </table>
      <h2 class="empty-msg" v-if="users.length === 0">暂无数据</h2>
    </main>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onUnmounted, ref, watch } from "vue";
import { User, VideoPlay } from "@element-plus/icons-vue";
import { useStore } from "vuex";

const store = useStore();
const users = ref([]);

onMounted(() => {});

onUnmounted(() => {});
</script>

<style scoped>
.container {
  text-align: left;
  overflow: hidden;
  width: 80%;
  margin: 0 auto;
  display: table;
  padding: 0 0 8em 0;
}

.empty-msg {
  text-align: center;
}

.breadcrumbs {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1em;
}
</style>
