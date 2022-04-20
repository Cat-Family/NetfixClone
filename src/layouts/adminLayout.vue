<template>
  <v-navigation-drawer v-model="drawer" :rail="rail" permanent>
    <v-list-item
      prepend-avatar="https://randomuser.me/api/portraits/men/85.jpg"
      title="John Leider"
    >
      <template v-slot:append>
        <v-btn
          variant="text"
          icon="mdi-chevron-left"
          @click.stop="rail = !rail"
        ></v-btn>
      </template>
    </v-list-item>

    <v-divider></v-divider>

    <v-list density="compact" nav>
      <v-list-item
        prepend-icon="mdi-video-outline"
        title="视频管理"
        value="videos"
        to="/admin"
      ></v-list-item>
      <v-list-item
        prepend-icon="mdi-account-group-outline"
        title="用户管理"
        to="/admin/users"
        value="users"
      ></v-list-item>
    </v-list>

    <template v-slot:append>
      <v-list density="comfortable" nav>
        <v-list-item
          variant="text"
          prepend-icon="mdi-logout"
          title="退出登录"
          value="lagout"
        ></v-list-item>
      </v-list>
    </template>
  </v-navigation-drawer>

  <v-app-bar color="info" density="compact">
    <v-app-bar-nav-icon
      variant="text"
      @click.stop="rail = !rail"
    ></v-app-bar-nav-icon>

    <v-app-bar-title>管理员后台</v-app-bar-title>
    <v-spacer></v-spacer>
    <v-btn icon>
      <v-icon>mdi-dots-vertical</v-icon>
    </v-btn>
  </v-app-bar>

  <!-- Sizes your content based upon application components -->
  <v-main>
    <v-breadcrumbs :items="caveatsItems" divider="-"></v-breadcrumbs>
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
  name: "AdminLayout",

  data: () => ({
    drawer: true,
    group: null,
    rail: true,
    fav: true,
    menu: false,
    message: false,
    hints: true,

    caveatsItems: [
      {
        text: "管理员后台",
        disabled: false,
        href: "/admin",
      },
      {
        text: "用户管理",
        disabled: false,
        href: "/admin/users",
      },
    ],
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