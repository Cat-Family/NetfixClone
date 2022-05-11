<template>
  <div class="MovieLabels">
    <span :class="['MovieLabels__rate', `MovieLabels__rate--${movieRateColor}`]">
      {{ movieRate }} 分
    </span>
    <span class="MovieLabels__date">
      {{ movieReleaseDate}}
    </span>
  </div>
</template>

<script>
  export default {
    name: 'MovieLabels',
    props: {
      movie: {
        voteAverage: String,
        releaseDate: String,
      },
    },
    computed: {
      movieRate() {
        return `${this.movie.voteAverage }`;
      },
      movieRateColor() {
        const percent = this.movie.vote_average * 10;
        if (percent < 30) return 'red';
        if (percent < 50) return 'yellow';
        return 'green';
      },
      movieReleaseDate() {
        return new Date(this.movie.releaseDate).toLocaleDateString();
      },
    },
  }
</script>

<style lang="scss">
  @import 'MovieLabels';
</style>
