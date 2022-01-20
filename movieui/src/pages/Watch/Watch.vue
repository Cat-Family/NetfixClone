<template>
  <Player>
    <Video>
      <source data-src="/test2.mp4" type="video/mp4" />
    </Video>
    <DefaultUi />
  </Player>
</template>

<script >
import { Player, Video, DefaultUi } from "@vime/vue-next";
import "@vime/core/themes/default.css";

export default {
  name: "App",
  components: {
    Player,
    Video,
    DefaultUi,
  },
};
</script>

