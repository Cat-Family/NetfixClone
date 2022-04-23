<template>
  <div class="page__content">
    <h1 class="page__title">用户管理</h1>
    <br />
    <h4>无花果后台 / 用户管理</h4>
    <el-divider />
    <main>
      <table class="container">
        <thead>
          <tr>
            <th><h1>ID</h1></th>
            <th><h1>用户名</h1></th>
            <th><h1>手机号</h1></th>
            <th><h1>密码</h1></th>
            <th><h1>邮箱</h1></th>
            <th><h1>等级</h1></th>
            <th><h1>操作</h1></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" v-bind:key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.phone }}</td>
            <td>{{ user.password }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.level }}</td>
            <td>
              <el-button
                plain
                size="mini"
                type="primary"
                @click="deleteUser(user.id)"
                >设为管理员</el-button
              >
              <el-button
                plain
                type="danger"
                size="mini"
                auto-insert-space
                @click="deleteUser(user.id)"
                >删除</el-button
              >
            </td>
          </tr>
        </tbody>
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

onMounted(() => {
  store.dispatch("getAllUsers");
});

watch(
  () => store.getters.users,
  (newVal) => {
    users.value = newVal;
  }
);

onUnmounted(() => {
  store.dispatch("clearAllUsers");
});
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
</style>
