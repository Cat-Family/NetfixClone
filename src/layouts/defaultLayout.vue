<template>
  <v-navigation-drawer v-model="drawer" :rail="rail" permanent>
    <v-list-item :prepend-avatar="logo" title="无花果影视"> </v-list-item>
    <v-divider></v-divider>
    <v-list density="compact" nav>
      <v-list-item
        prepend-icon="mdi-home-outline"
        title="首页"
        value="home"
        to="/"
      ></v-list-item>
      <v-list-item
        prepend-icon="fa-brands fa-searchengin"
        title="探索"
        value="search"
        to="/search"
      ></v-list-item>
      <v-list-item
        prepend-icon="far fa-rectangle-list"
        title="收藏"
        value="collection"
        to="/collection"
      ></v-list-item>
      <v-list-item
        prepend-icon="mdi-history"
        title="历史记录"
        value="history"
        to="/history"
      ></v-list-item>
      <v-list-item
        prepend-icon="fa fa-dashboard"
        title="管理后台"
        value="admin"
        to="/admin"
      >
      </v-list-item>
    </v-list>

    <template v-slot:append>
      <v-divider></v-divider>

      <v-list density="comfortable">
        <v-list-item
          v-if="rail"
          @click="rail = false"
          variant="text"
          prepend-icon="mdi-chevron-right"
          value="open"
        ></v-list-item>
        <v-list-item
          v-if="!rail"
          @click="rail = true"
          variant="text"
          value="close"
        >
          <template v-slot:append>
            <v-btn
              variant="text"
              icon="mdi-chevron-left"
              @click.stop="rail = !rail"
            ></v-btn>
          </template>
        </v-list-item>
        <v-list-item
          prepend-avatar="https://randomuser.me/api/portraits/men/85.jpg"
          title="John Leider"
        >
        </v-list-item>
        <v-list-item
          variant="text"
          prepend-icon="mdi-logout"
          title="退出登录"
          value="lagout"
        ></v-list-item>
      </v-list>
    </template>
  </v-navigation-drawer>

  <!-- Sizes your content based upon application components -->
  <v-main>
    <!-- Provides the application the proper gutter -->
    <v-container fluid>
      <!-- If using vue-router -->
      <router-view></router-view>
    </v-container>
  </v-main>

  <v-footer app> </v-footer>
</template>

<script lang="ts">
import logo from "../assets/logo.svg";

export default {
  name: "DefaultLayout",

  data: () => ({
    drawer: true,
    group: null,
    rail: true,
    logo,
  }),
};
</script>

<style scoped></style>
