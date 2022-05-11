<template>
  <div class="MovieListItem" :style="{ backgroundImage: 'url(' + movie.posterPath + ')' }">
    <div class="MovieListItem__details" @click="selectMovie">
      <h3 class="MovieListItem__title">
        {{ movie.title ? movie.title : movie.name }}
      </h3>
      <MovieLabels :movie="movie" />
      <p class="MovieListItem__description">
        {{ movie.overview }}
      </p>
    </div>
  </div>
</template>

<script>
  import MovieLabels from '../MovieLabels/MovieLabels.vue';
  import getImageUrl from '../../helpers/getImageUrl.js';

  export default {
    name: 'MovieListItem',
    props: {
      movie: {
        name: String,
        title: String,
        overview: String,
        poster_path: String,
      },
    },
    components: {
      MovieLabels,
    },
    methods: {
      selectMovie() {
        this.$emit('select-movie', this.movie);
      },
    },
  }
</script>

<style lang="scss">
  @import "MovieListItem";
</style>
