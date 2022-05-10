<template>
  <div class="Home">
    <el-carousel ref="slider" class="Home__main-slider" height="100%">
      <el-carousel-item v-for="(movie, index) in movieList" :key="index">
        <MovieDetails :movie="movie" />
      </el-carousel-item>
    </el-carousel>

    <div class="Home__slider-list">
      <MovieSlider
        category-title="Netflix Originals"
        request-url="now_playing.json"
      />
      <MovieSlider category-title="Trending Now" request-url="week.json" />
      <MovieSlider category-title="Recently Added" request-url="tv.json" />
      <MovieSlider category-title="Top Rated" request-url="top_rated.json" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import MovieDetails from "../../components/MovieDetails/MovieDetails.vue";
import MovieSlidater from "../../components/MovieSlider/MovieSlider.vue";
import { ref } from "vue";

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
    const loading = ref(true);
    axios
      .get("/movie.json")
      .then((response) => {
        movieList.value = response.data.results.splice(0, 10);
        loading.value = false;
      })
      .catch((error) => {
        console.log(error);
      });

    return { movieList, loading };
  },
};
</script>

<style lang="scss">
@import "Home";
</style>
