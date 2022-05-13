<template>
  <div class="MovieDetails" :style="{ backgroundImage: getBackgroundImageUrl(movie.posterPath) }">
    <div class="MovieDetails__fade--top" />
    <div class="MovieDetails__wrapper">
      <h1 class="MovieDetails__title">
        {{ movie.title || movie.name }}
      </h1>
      <MovieLabels :movie="movie" />
      <div class="MovieDetails__details">
        <p class="MovieDetails__description">
          {{ movie.overview }}
        </p>
        <button v-if="this.movie.isCollect == 0" type="button" class="btn MovieDetails__btn"
          @click="addMovieToMyList(this.movie.id)">
          <font-awesome-icon :icon="['fas', 'plus']" class="MovieDetails__btn-icon" fixed-width />
          收藏
        </button>
        <button v-else type="button" class="btn MovieDetails__btn" @click="removeMovieFromMyList(this.movie.id)">
          <font-awesome-icon :icon="['fas', 'minus']" class="MovieDetails__btn-icon" fixed-width />
          取消收藏
        </button>
        <button type="button" class="btn MovieDetails__btn" @click="toPaly(this.movie.id)">
          <font-awesome-icon :icon="['fas', 'play']" class="MovieDetails__btn-icon" fixed-width />
          立即观看
        </button>
      </div>
    </div>
    <div class="MovieDetails__fade--bottom" />
  </div>
</template>

<script>
import MovieLabels from "../MovieLabels/MovieLabels.vue";
import getImageUrl from "../../helpers/getImageUrl";
import { actions } from "../../helpers/constants";
import { useRouter } from "vue-router";
import instance from "../../request";

export default {
  name: "MovieDetails",
  props: {
    movie: {
      id: Number,
      name: String,
      title: String,
      overview: String,
      posterPath: String,
      isCollect: Number
    },
  },
  computed: {

  },
  components: {
    MovieLabels,
  },
  methods: {
    getBackgroundImageUrl(url) {
      return `url(${url})`;
    },
    addMovieToMyList(id) {
      instance.post("/collect/collection", {
        user_id: window.localStorage.getItem("id"),
        movie_id: id
      })
    },
    removeMovieFromMyList(id) {
      instance.post("/collect/cancel-collection", {
        collectId: id,
        user: window.localStorage.getItem("id")
      }).then(() => {
        window.location.reload();
      })
    }
  },
  setup() {
    const router = useRouter();

    const toPaly = (id) => {
      router.push({
        path: "/Watch",
        query: {
          id: id,
        },
      });
    };
    return {
      toPaly,
    };
  },
};
</script>

<style lang="scss">
@import "MovieDetails";
</style>
