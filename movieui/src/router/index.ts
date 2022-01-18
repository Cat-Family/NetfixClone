import { createRouter, createWebHistory } from "vue-router";

import SignIn from "../pages/SignIn/SignIn.vue";
import SignUp from "../pages/SignUp/SignUp.vue";
import StartNow from "../pages/StartNow/StartNow.vue";
import Home from "../pages/Home/Home.vue";

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
    path: "/Home",
    name: "Home",
    component: Home,
    // meta: {
    //   requiresAuth: true,
    // },
  },
];

const router = createRouter({
  history,
  routes,
});

export default router;
