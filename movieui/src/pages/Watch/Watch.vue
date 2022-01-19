<template>
  <div id="app">
    <div id="container">
      <Player playsinline ref="player" @vPlaybackReady="onPlaybackReady">
        <Video poster="https://media.vimejs.com/poster.png">
          <source src="./test.mp4" type="video/mp4" />
        </Video>

        <DefaultUi>
          <!-- Custom UI component. -->
          <TapSidesToSeek />
        </DefaultUi>
      </Player>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { Player, Video, DefaultUi, Vimeo } from "@vime/vue-next";
// Default theme.
import "@vime/core/themes/default.css";
// Optional light theme (extends default).
// import '@vime/core/themes/light.css';
// Custom UI Component.
import TapSidesToSeek from "./TapSidesToSeek.vue";
export default defineComponent({
  name: "App",
  components: {
    Player,
    Video,
    DefaultUi,
    TapSidesToSeek,
    Vimeo,
  },
  setup() {
    // Obtain a ref if you need to call any methods.
    const player = ref<HTMLVmPlayerElement | null>(null);
    return { player };
  },
  methods: {
    onPlaybackReady() {
      // ...
    },
  },
});
</script>

<style>
</style>