<template>
  <div class="MovieSlider__wrapper">
    <h2 class="MovieSlider__title">{{ categoryTitle }}</h2>
    <div v-click-outside="unselectMovie">
      <Slider ref="slider" :options="options" :class="{ 'Slider--has-selected': selectedMovie }">
        <div v-for="(movie, index) in movieList" :key="movie.id" :class="`slide--${movie.id}`">
          <MovieSliderItem :movie="movie" v-on:select-movie="selectMovie" />
        </div>
      </Slider>
      <transition name="fade-in-up">
        <div v-if="selectedMovie" class="MovieSlider__details">
          <div @click.stop>
            <MovieDetails :movie="selectedMovie" />
            <button type="button" class="btn--close" @click="unselectMovie" />
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import vClickOutside from "click-outside-vue3";
import Slider from "../../components/Slider/Slider.vue";
import MovieSliderItem from "../../components/MovieSliderItem/MovieSliderItem.vue";
import MovieDetails from "../../components/MovieDetails/MovieDetails.vue";
// import clickOutside from "../../directives/clickOutside";
import instance from "../../request";

export default {
  name: "MovieSlider",
  props: {
    categoryTitle: String,
    params: String,
  },
  data() {
    return {
      movieList: [],
      selectedMovie: null,
      param: {},
      options: {
        dots: false,
        navButtons: false,
        slidesToShow: 5,
        slidesToScroll: 5,
        mobileFirst: false,
        speed: 300,
        timing: "ease-in-out",
        extraClass: "MovieSlider",
        responsive: [
          { breakpoint: 576, settings: { slidesToShow: 1, slidesToScroll: 1 } },
          { breakpoint: 768, settings: { slidesToShow: 2, slidesToScroll: 2 } },
          { breakpoint: 992, settings: { slidesToShow: 3, slidesToScroll: 3 } },
          {
            breakpoint: 1100,
            settings: { slidesToShow: 4, slidesToScroll: 4 },
          },
        ],
      },
    };
  },
  components: {
    Slider,
    MovieSliderItem,
    MovieDetails,
  },
  directives: {
    clickOutside: vClickOutside.directive,
  },
  methods: {
    selectMovie(movie) {
      if (this.selectedMovie && this.selectedMovie === movie)
        this.selectedMovie = null;
      else this.selectedMovie = movie;
    },
    unselectMovie() {
      this.selectedMovie = null;
    },
  },
  mounted() {
    this.$refs.slider.toggleLoading();

    if (this.params === "original") {
      this.param = { original: 0 }
    }
    if (this.params === "tv") {
      this.param = { post: 1 }
    }
    if (this.params === "movie") {
      this.param = { post: 0 }
    }

    instance
      .post("/movie/zone", this.param)
      .then((response) => {
        this.movieList = response.data.data;
      })
      .then(() => {
        this.$refs.slider.reload();
        this.$refs.slider.toggleLoading();
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style lang="scss">
@import "MovieSlider";
</style>
