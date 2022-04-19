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
import Admin from "../pages/Admin/Admin.vue";

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
    // meta: {
    //   requiresAuth: true,
    // },
  },
  {
    path: "/watch",
    name: "Watch",
    component: Watch,
    // meta: {
    //   requiresAuth: true,
    // },
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
    path: "/admin",
    name: "Admin",
    component: Admin,
  }
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
