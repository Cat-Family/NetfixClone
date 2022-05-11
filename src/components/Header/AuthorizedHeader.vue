<template>
  <header :class="[{ 'Header--bg': isScrolled }, 'Header']">
    <router-link :to="homeRoute">
      <router-link to="/home">
        <img
          class="Header__logo Header__logo--un"
          src="../../assets/images/netflix.png"
        />
      </router-link>
    </router-link>

    <nav :class="[{ 'Header__nav--opened': isMenuOpened }, 'Header__nav']">
      <ul class="Header__nav-list">
        <li
          class="Header__nav-item"
          v-for="(navItem, index) in navList"
          :key="index"
        >
          <router-link
            v-if="!navItem.nav"
            class="Header__nav-link"
            :to="navItem.link"
          >
            {{ navItem.title }}
          </router-link>
          <div v-else class="Header__nav-link">
            {{ navItem.title }}
            <div class="dropdown">
              <div
                class="dropdown__list"
                v-for="(dropdownItem, index) in navItem.nav"
                :key="index"
              >
                <router-link
                  class="dropdown__btn"
                  :to="`${navItem.link}/${dropdownItem.id}`"
                >
                  {{ dropdownItem.name }}
                </router-link>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </nav>

    <div
      class="Header__search"
      :class="[{ 'Header__search--active': search }, 'Header__search']"
    >
      <label class="flex-jc">
        <font-awesome-icon
          :icon="['fas', 'search']"
          class="Header__search-icon"
        />
        <input
          name="search"
          type="text"
          placeholder="Titles, characters, geners"
          v-model="search"
          class="Header__search-input"
        />
      </label>
    </div>

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
              ><router-link to="/admin/user"
                >管理员
              </router-link></el-dropdown-item
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
    <el-drawer
      v-model="drawer"
      title="I am the title"
      :direction="direction"
      :before-close="handleClose"
    >
      <span>Hi, there!</span>
    </el-drawer>
  </header>
</template>

<script>
import NetflixLogo from "../../assets/images/netflix.svg";
import debounce from "../../helpers/debounce";
import { routes, actions } from "../../helpers/constants";
import { ref } from "vue";

export default {
  name: "AuthorizedHeader",
  data() {
    return {
      search: "",
      isScrolled: false,
      isMenuOpened: false,
      homeRoute: routes.home,
      navList: [
        { title: "主页", link: routes.home },
        { title: "电视", link: routes.tvShows, nav: [] },
        { title: "电影", link: routes.movies, nav: [] },
        { title: "收藏夹", link: routes.collection },
        { title: "浏览记录", link: routes.myList },
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
          if (item.title === "电视") return { ...item, nav: value.tv };
          if (item.title === "电影") return { ...item, nav: value.movies };
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
