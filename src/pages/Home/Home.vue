<template>
  <div class="Home">
    <el-carousel ref="slider" class="Home__main-slider" height="100%">
      <el-carousel-item v-for="(movie, index) in movieList" :key="index">
        <MovieDetails :movie="movie" />
      </el-carousel-item>
    </el-carousel>

    <div class="Home__slider-list">
      <MovieSlider category-title="原创" params="original" />
      <MovieSlider category-title="电视" params="tv" />
      <MovieSlider category-title="电影" params="movie" />
    </div>
  </div>
</template>

<script>
import MovieDetails from "../../components/MovieDetails/MovieDetails.vue";
import MovieSlidater from "../../components/MovieSlider/MovieSlider.vue";
import { ref, onMounted } from "vue";
import instance from "../../request";

export default {
  name: "Home",
  computed: {
    user() {
      return this.$store.getters.user;
    },
  },
  components: {
    MovieDetails,
    MovieSlidater,
  },
  setup() {
    let movieList = ref([]);

    onMounted(async () => {
      const res = await instance.post("/movie/zone", {})
      movieList.value = res.data.data?.slice(0, 7)
    })

    return { movieList };
  },
};
</script>

<style lang="scss">
@import "Home";
</style>
