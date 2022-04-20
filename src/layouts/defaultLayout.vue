<template>
  <v-navigation-drawer
    v-model="drawer"
    :rail="rail"
    permanent
    @click="rail = false"
  >
    <template v-slot:prepend>
      <v-list-item
        two-line
        prepend-avatar="https://randomuser.me/api/portraits/women/81.jpg"
        title="Jane Smith"
        subtitle="Logged in"
      ></v-list-item>
    </template>

    <v-divider></v-divider>

    <v-list density="compact" nav>
      <v-list-item
        prepend-icon="mdi-video-outline"
        title="视频管理"
        value="videos"
      ></v-list-item>
      <v-list-item
        prepend-icon="mdi-account-group-outline"
        title="用户管理"
        value="users"
      ></v-list-item>
    </v-list>
  </v-navigation-drawer>

  <v-app-bar color="info" density="compact">
    <v-app-bar-nav-icon
      variant="text"
      @click.stop="rail = !rail"
    ></v-app-bar-nav-icon>

    <v-app-bar-title>Admin</v-app-bar-title>

    <v-btn icon>
      <v-icon>mdi-magnify</v-icon>
    </v-btn>

    <v-avatar> test </v-avatar>
  </v-app-bar>

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

<script>
export default {
  name: "DefaultLayout",

  data: () => ({
    drawer: true,
    group: null,
    rail: true,
    items: [
      {
        title: "Foo",
        value: "foo",
      },
      {
        title: "Bar",
        value: "bar",
      },
      {
        title: "Fizz",
        value: "fizz",
      },
      {
        title: "Buzz",
        value: "buzz",
      },
    ],
  }),
};
</script>

<style scoped>
</style>