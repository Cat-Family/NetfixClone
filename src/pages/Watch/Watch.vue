<template>
  <div style="height: 100vh">

    <vue-plyr :options="options">
      <video :src="movie?.movieAddr" controls crossorigin playsinline :data-poster="movie?.posterPath">
        <a download="" :href="movie?.movieAddr">
          下载
        </a>
      </video>
    </vue-plyr>
  </div>
</template>

<script>
import instance from "../../request";

export default {
  name: "Video",
  data() {
    return {
      id: window.location.search.split("=")[1],
      movie: {}
    };
  },

  mounted() {
    instance
      .post("/movie/detail", { id: this.id })
      .then((response) => {
        this.movie = response.data.data
      })
      .then(() => {
        this.$refs.slider.reload();
      })
      .catch((error) => {
        console.log(error);
      });
  }

};
</script>