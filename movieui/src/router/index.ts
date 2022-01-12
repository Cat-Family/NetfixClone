import { createRouter, createWebHashHistory } from "vue-router";

import SignIn from "../pages/SignIn/SignIn.vue";
import SignUp from "../pages/SignUp/SignUp.vue";
import StartNow from "../pages/StartNow/StartNow.vue";

const history = createWebHashHistory();
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
];

const router = createRouter({
  history,
  routes,
});

export default router;
