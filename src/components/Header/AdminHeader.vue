<template>
  <header :class="[{ 'Header--bg': isScrolled }, 'Header']">
    <router-link :to="homeRoute">
      <router-link to="/admin/user">
        <img
          class="Header__logo Header__logo--un"
          src="../../assets/images/netflix.png"
        />
      </router-link>
    </router-link>

    <nav :class="[{ 'Header__nav--opened': isMenuOpened }, 'Header__nav']">
      <ul class="Header__nav-list">
        <li class="Header__nav-item">
          <router-link class="Header__nav-link" to="/admin/user">
            用户管理
          </router-link>
        </li>
        <li class="Header__nav-item">
          <router-link class="Header__nav-link" to="/admin/video">
            视频管理
          </router-link>
        </li>
      </ul>
    </nav>

    <div class="Header__actions">
      <el-dropdown>
        <el-avatar
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        />
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item
              ><router-link to="/profile">
                用户配置
              </router-link></el-dropdown-item
            >
            <el-dropdown-item @click="onLogOut">退出登录</el-dropdown-item>
            <el-dropdown-item
              ><router-link to="/home">用户页面</router-link></el-dropdown-item
            >
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <button
        :class="[{ 'hamburger--active': isMenuOpened }, 'hamburger', 'button']"
        v-on:click="toggleSidebar"
      >
        <span />
        <span />
        <span />
      </button>
    </div>
  </header>
</template>

<script>
import NetflixLogo from "../../assets/images/netflix.svg";
import debounce from "../../helpers/debounce";
import { routes, actions } from "../../helpers/constants";

export default {
  name: "AdminHeader",
  data() {
    return {
      search: "",
      isScrolled: false,
      isMenuOpened: false,
      homeRoute: routes.home,
      navList: [
        { title: "Home", link: routes.home },
        { title: "TV Shows", link: routes.tvShows, nav: [] },
        { title: "Movies", link: routes.movies, nav: [] },
        { title: "New & Popular", link: routes.popular },
        { title: "My List", link: routes.myList },
      ],
    };
  },
  computed: {
    path() {
      return this.$route.path;
    },
    genres() {
      return this.$store.getters.genres;
    },
  },
  components: {
    NetflixLogo,
  },
  watch: {
    path() {
      this.isMenuOpened = false;
    },
    genres(value) {
      if (value !== null && value !== undefined) {
        this.navList = this.navList.map((item) => {
          if (item.title === "TV Shows") return { ...item, nav: value.tv };
          if (item.title === "Movies") return { ...item, nav: value.movies };
          return item;
        });
      }
    },
    search: debounce(function (value) {
      this.$router.push(`${routes.search}/${value}`);
    }, 600),
  },
  methods: {
    handleScroll() {
      const currentScrollPosition =
        window.pageYOffset || document.documentElement.scrollTop;
      if (currentScrollPosition > 30) return (this.isScrolled = true);
      else return (this.isScrolled = false);
    },
    toggleSidebar() {
      this.isMenuOpened = !this.isMenuOpened;
      if (this.isMenuOpened)
        document.documentElement.classList.add("no-scroll");
      else document.documentElement.classList.remove("no-scroll");
    },
    onLogOut() {
      this.$store.dispatch(actions.logout);
    },
  },
  created() {
    window.addEventListener("scroll", this.handleScroll);
  },
  mounted() {
    if (this.$route.params.search) {
      this.search = this.$route.params.search;
    }
    return this.$store.dispatch(actions.setGenres);
  },
  destroyed() {
    window.removeEventListener("scroll", this.handleScroll);
  },
};
</script>

<style lang="scss">
@import "./Header.scss";
</style>
