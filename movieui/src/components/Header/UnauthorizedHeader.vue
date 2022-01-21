<template>
  <header class="Header--un">
    <router-link to="/">
      <img
        class="Header__logo Header__logo--un"
        src="../../assets/images/netflix.png"
      />
    </router-link>
    <div class="Header__actions">
      <router-link
        v-if="routePath !== signInRoute"
        class="btn btn--primary"
        :to="signInRoute"
      >
        登录
      </router-link>
    </div>
  </header>
</template>

<script>
import NetflixLogo from "../../assets/images/netflix.svg";
import { routes } from "../../helpers/constants";

export default {
  name: "UnauthorizedHeader",
  data() {
    return {
      signInRoute: routes.signIn,
      signUpRoute: routes.signUp,
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
</style>
