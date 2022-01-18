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
        v-if="routePath === '/signIn'"
        class="btn btn--primary"
        :to="signUpRoute"
      >
        注册
      </router-link>
      <router-link
        v-if="routePath !== '/signIn'"
        class="btn btn--primary"
        :to="signInRoute"
      >
        登录
      </router-link>
    </div>
  </header>
</template>

<script>
import { routes } from "../../helpers/constants";
export default {
  name: "Header",
  data() {
    return {
      signUpRoute: routes.signUp,
      signInRoute: routes.signIn,
    };
  },
  computed: {
    routePath() {
      return this.$route.path;
    },
  },
  components: {},
};
</script>

<style lang="scss">
@import "./Header.scss";
img {
  color: whitesmoke;
}
</style>
