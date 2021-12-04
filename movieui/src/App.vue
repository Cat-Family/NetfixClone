<template>
  <n-config-provider
    :theme="theme"
    :theme-overrides="{ common: { fontWeightStrong: '600' } }"
  >
    <n-message-provider>
      <n-card>
        <n-space>
          <n-button @click="theme = darkTheme">深色</n-button>
          <n-button @click="theme = null">浅色</n-button>
        </n-space>
        <br />
        <Login />
      </n-card>
    </n-message-provider>
  </n-config-provider>
</template>

<script >
import { defineComponent, ref } from "vue";
import { darkTheme } from "naive-ui";
import Login from "./views/Login.vue";
export default defineComponent({
  setup() {
    return {
      darkTheme,
      theme: ref(null),
    };
  },
  components: {
    Login,
  },
});
</script>

<style></style>
