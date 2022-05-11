<template>
  <div class="MovieList__wrapper">
    <transition name="fade">
      <div class="MovieList" v-if="movieList.length !== 0">
        <MovieListItem v-for="(movie, index) in movieList" :key="index" :movie="movie"
          v-on:select-movie="selectMovie" />
      </div>
      <div v-else-if="!loading && movieList.length === 0" class="MovieList__empty">
        {{ emptyTitle }}
      </div>
    </transition>
    <transition name="fade">
      <div v-if="selectedMovie" class="MovieList__details-backdrop" @click="unselectMovie">
        <div @click.stop class="MovieList__details">
          <MovieDetails :movie="selectedMovie" />
          <button type="button" class="btn--close" @click="unselectMovie" />
        </div>
      </div>
    </transition>
    <div v-if="loading" class="Spinner__overflow">
      <Spinner />
    </div>
    <Pagination v-if="totalPages" :current-page="currentPage" :total-pages="totalPages" @load="loadMovies" />
  </div>
</template>

<script>
import axios from 'axios';
import MovieListItem from '../MovieListItem/MovieListItem.vue';
import MovieDetails from '../MovieDetails/MovieDetails.vue';
import Pagination from '../Pagination/Pagination.vue';
import Spinner from '../Spinner/Spinner.vue';
import instance from "../../request";


export default {
  name: 'MovieList',
  props: {
    requestUrl: String,
    emptyListTitle: String,
    initialMovieList: Array,
  },
  data() {
    return {
      emptyTitle: this.emptyListTitle || 'No data here',
      movieList: this.initialMovieList || [],
      selectedMovie: null,
      currentPage: 1,
      totalPages: 0,
      loading: false,
    };
  },
  components: {
    MovieListItem,
    MovieDetails,
    Pagination,
    Spinner,
  },
  watch: {
    requestUrl(value) {
      if (value !== null && value !== undefined) {
        this.loadMovies();
      }
    },
    emptyListTitle(value) {
      this.emptyTitle = value;
    },
    initialMovieList(value) {
      if (value !== null && value !== undefined) {
        this.movieList = value;
        this.selectedMovie = null;
      }
    },
  },
  methods: {
    toggleLoading() {
      this.loading = !this.loading;
    },
    loadMovies() {
      let { path } = this.$route;
      let params;
      if (path.split("/")[1] === "tv-shows") {
        params = { type: path.split("/")[2], post: 0 };
      }
      if (path.split("/")[1] === "movies") {
        params = { type: path.split("/")[2], post: 1 };
      }
      if (path.split("/")[1] === "collection") {
        if (window.localStorage.getItem("id")) {
          params = { id: window.localStorage.getItem("id") }
        }
      }
      if (path.split("/")[1] === "my-list") {
        if (window.localStorage.getItem("id")) {
          params = { id: window.localStorage.getItem("id"), mark: 1 }
        }
      }
      if (path.split("/")[1] === "search") {
        console.log("test");
        params = { info: path.split("/")[2] }
      }

      this.toggleLoading();
      instance.post(`/movie/zone`, { ...params })
        .then(response => {
          this.movieList = response.data.data;
        })
        .then(() => {
          window.scrollTo(0, 0);
        })
        .catch(error => {
          console.log(error);
        })
        .finally(() => {
          this.toggleLoading();
        });

      if (!this.requestUrl) return;
    },
    selectMovie(movie) {
      this.selectedMovie = movie;
    },
    unselectMovie() {
      this.selectedMovie = null;
    },
  },
  mounted() {
    this.loadMovies();
  },
}
</script>

<style lang="scss">
@import 'MovieList';
</style>
