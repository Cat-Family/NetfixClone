<template>
  <v-carousel
    show-arrows="hover"
    hide-delimiter-background
    cycle
    height="400"
    hide-delimiters
    progress="info"
  >
    <v-carousel-item
      v-for="(item, i) in items"
      :key="i"
      :src="item.src"
      cover
    >
      test
    </v-carousel-item>
  </v-carousel>
</template>

<script lang="ts">
export default {
  name: "HomePage",
  data() {
    return {
      model: null,
      items: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg",
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg",
        },
      ],
    };
  },
};
</script>

<style scoped></style>
