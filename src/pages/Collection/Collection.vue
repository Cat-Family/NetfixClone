<template>
  <div class="page__content">
    <h1 class="page__title">
      收藏夹
      </h1>
    <MovieList request-url="/collection" />
  </div>
</template>

<script>
  import MovieList from '../../components/MovieList/MovieList.vue';

  export default {
    name: 'Collection',
    components: {
      MovieList,
    },
  }
</script>
