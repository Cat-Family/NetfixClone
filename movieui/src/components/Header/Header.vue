<template>
  <div v-if="userIsAuthenticated && begin">
    <AuthorizedHeader />
  </div>
  <div v-else>
    <UnauthorizedHeader />
  </div>
</template>

<script>
import AuthorizedHeader from "./AuthorizedHeader.vue";
import UnauthorizedHeader from "./UnauthorizedHeader.vue";
import { actions } from "../../helpers/constants";
import { useRoute } from "vue-router";
import { toRaw } from "vue";

export default {
  name: "Header",
  computed: {
    userIsAuthenticated() {
      return (
        this.$store.getters.user?.token !== null &&
        this.$store.getters.user?.token !== undefined
      );
    },
    begin() {
      const route = useRoute();
      return (
        route.path != "/" && route.path != "/signIn" && route.path != "/signUp"
      );
    },
  },
  components: {
    AuthorizedHeader,
    UnauthorizedHeader,
  },
  methods: {
    onLogOut() {
      this.$store.dispatch(actions.logout);
    },
  },
};
</script>
