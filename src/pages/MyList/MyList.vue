<template>
  <div class="page__content">
    <h1 class="page__title">
      历史记录
      </h1>
    <MovieList request-url="/my-list" />
  </div>
</template>

<script>
  import MovieList from '../../components/MovieList/MovieList.vue';

  export default {
    name: 'MyList',
    computed: {
      myMovieList() {
        return this.$store.getters.myList;
      },
    },
    components: {
      MovieList,
    },
  }
</script>
