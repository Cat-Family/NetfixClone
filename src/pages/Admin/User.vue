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
            <th><h2>ID</h2></th>
            <th><h2>用户名</h2></th>
            <th><h2>手机号</h2></th>
            <th><h2>密码</h2></th>
            <th><h2>邮箱</h2></th>
            <th><h2>等级</h2></th>
            <th><h2>操作</h2></th>
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
                @click="update(user.id)"
                >设为管理员</el-button
              >
              <el-button
                plain
                type="danger"
                size="mini"
                auto-insert-space
                :loading="deleteLoading && actionUserId === user.id"
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
import instance from "../../request";

const store = useStore();
const users = ref([]);
const deleteLoading = ref(false);
const actionUserId = ref(null);

onMounted(() => {
  store.dispatch("getAllUsers");
});

watch(
  () => store.getters.users,
  (newVal) => {
    users.value = newVal;
  }
);

const deleteUser = async (id) => {
  deleteLoading.value = true;
  actionUserId.value = id;
  const res = await instance.post(`/user/delete`, { id });

  if (res.data.code == 200) {
    store.dispatch("getAllUsers");
    deleteLoading.value = false;
    actionUserId.value = null;
  }

  deleteLoading.value = false;
  actionUserId.value = null;
};

const update = async (id) => {};

onUnmounted(() => {
  store.dispatch("clearAllUsers");
});
</script>

<style scoped>
.container {
  text-align: left;
  width: 80%;
  margin: 0 auto;
  display: table;
  padding: 0 0 8em 0;
}

.empty-msg {
  text-align: center;
}
</style>
