import { createRouter, createWebHistory } from "vue-router";
import { useStore } from "vuex";
import SignIn from "../pages/SignIn/SignIn.vue";
import SignUp from "../pages/SignUp/SignUp.vue";
import StartNow from "../pages/StartNow/StartNow.vue";
import Home from "../pages/Home/Home.vue";
import Watch from "../pages/Watch/Watch.vue";
import RecoverPassword from "../pages/RecoverPassword/RecoverPassword.vue";
import RecoverPasswordSuccess from "../pages/RecoverPassword/RecoverPasswordSuccess.vue";
import RecoverPasswordForm from "../pages/RecoverPassword/RecoverPasswordForm.vue";
import Profile from "../pages/Profile/Profile.vue";
import AdminUser from "../pages/Admin/User.vue";
import AdminVideo from "../pages/Admin/Video.vue";
import Search from "../pages/Search/Search.vue";
import TVShows from "../pages/TVShows/TVShows.vue";
import Movies from "../pages/Movies/Movies.vue";
import Collection from "../pages/Collection/Collection.vue";
import MyList from "../pages/MyList/MyList.vue";

const store = useStore();
const history = createWebHistory();
const routes = [
  {
    path: "/",
    name: "StartNow",
    component: StartNow,
  },
  {
    path: "/signIn",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/signUp",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/home",
    name: "Home",
    component: Home,
  },
  {
    path: "/watch",
    name: "Watch",
    component: Watch,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/RecoverPassword",
    name: "RecoverPassword",
    component: RecoverPassword,
  },
  {
    path: "/RecoverPasswordSuccess",
    name: "RecoverPasswordSuccess",
    component: RecoverPasswordSuccess,
  },
  {
    path: "/RecoverPasswordForm",
    name: "RecoverPasswordForm",
    component: RecoverPasswordForm,
  },
  {
    path: "/admin/user",
    name: "Admin/user",
    component: AdminUser,
  },
  {
    path: "/admin/video",
    name: "Admin/admin",
    component: AdminVideo,
  },
  {
    path: "/search/:search",
    name: "Search",
    component: Search,
  },
  {
    path: `/movies/:id`,
    name: 'Movies',
    component: Movies,
  },
  {
    path: `/tv-shows/:id`,
    name: 'TVShows',
    component: TVShows,
  },
  {
    path: "/collection",
    name: 'Collection',
    component: Collection,
  },
  {
    path: "/my-list",
    name: 'My List',
    component: MyList,
  },
];

const router = createRouter({
  history,
  routes,
});

router.beforeEach((to, from) => {
  if (to.meta.requiresAuth && store.getters.user?.token) {
    return {
      path: "/signIn",
      query: { redirect: to.fullPath },
    };
  }
});

export default router;
