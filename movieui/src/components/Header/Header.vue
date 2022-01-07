<template>
  <header class="Header--un">
    <img
      class="Header__logo Header__logo--un"
      src="../../assets/images/netflix.png"
    />
    <div class="Header__actions">
      <router-link class="btn btn--primary"> Sign in </router-link>
    </div>
  </header>
</template>

<script>
import NetflixLogo from "../../assets/images/netflix.svg";
import { routes } from "../../helpers/constants";

export default {
  name: "Header",
  data() {
    return {
      signInRoute: routes.signIn,
    };
  },
  computed: {
    routePath() {
      return this.$route.path;
    },
  },
  components: {
    NetflixLogo,
  },
};
</script>

<style lang="scss">
@import "./Header.scss";
img {
  color: whitesmoke;
}
</style>
