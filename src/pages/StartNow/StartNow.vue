<template>
  <div class="StartNow bg-home">
    <div class="StartNow__container">
      <h1 class="StartNow__title">了解下一步。</h1>
      <p class="StartNow__subtitle">随时随地观看。随时取消。</p>
      <router-link class="btn btn--primary StartNow__btn" :to="signInRoute">
        现在 开始
      </router-link>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { routes } from "../../helpers/constants";
import { useStore } from "vuex";

export default {
  name: "StartNow",
  data() {
    return {
      signInRoute: routes.signIn,
    };
  },
};
</script>

<style lang="scss">
@import "StartNow";

@media (max-width: 30em) {
  .bg-home {
    background-image: url("/login-the-crown_2-1500x1000.jpg");
    background-position: center right;
    background-size: cover;
  }
}

@media (min-width: 30em) {
  .bg-home {
    background-image: url("/login-the-crown_2-1500x1000.jpg");
    background-position: center center;
  }
}
</style>
