<template>
  <div v-if="begin">
    <AuthorizedHeader />
  </div>
  <div v-else-if="adminHeader">
    <AdminHeader />
  </div>
  <div v-else>
    <UnauthorizedHeader />
  </div>
</template>

<script>
import AuthorizedHeader from "./AuthorizedHeader.vue";
import UnauthorizedHeader from "./UnauthorizedHeader.vue";
import AdminHeader from "./AdminHeader.vue";
import { actions } from "../../helpers/constants";
import { useRoute } from "vue-router";

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
        route.path != "/" &&
        route.path != "/signIn" &&
        route.path != "/signUp" &&
        route.path != "/admin/video"&&
        route.path != "/admin/user"
      );
    },
    adminHeader() {
      const route = useRoute();
      return route.path == "/admin/user" || route.path == "/admin/video";
    },
  },
  components: {
    AuthorizedHeader,
    UnauthorizedHeader,
    AdminHeader,
  },
  methods: {
    onLogOut() {
      this.$store.dispatch(actions.logout);
    },
  },
};
</script>
