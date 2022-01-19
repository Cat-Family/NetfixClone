<template>
  <div>
    <Header v-if="route.path !== '/Watch'" />
    <main>
      <router-view v-slot="{ Component, route }">
        <transition :name="route.meta.transition || 'fade'">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
  </div>
</template>

<script lang="ts">
import Header from "./components/Header/Header.vue";
import { useRoute } from "vue-router";

export default {
  components: {
    Header,
  },
  setup() {
    const route = useRoute();
    return {
      route,
    };
  },
};
</script>
