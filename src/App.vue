<template>
  <v-app>
    <router-view></router-view>
  </v-app>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import HelloWorld from "./components/HelloWorld.vue";

export default defineComponent({
  name: "App",

  components: {
    HelloWorld,
  },

  data() {
    return {
      //
    };
  },
});
</script>
