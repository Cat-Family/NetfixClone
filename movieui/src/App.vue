<template>
  <div>
    <Header />
    <main>
      <router-view />
    </main>
  </div>
</template>

<script lang="ts">
import Header from "./components/Header/Header.vue";

export default {
  components: {
    Header,
  },
};
</script>
