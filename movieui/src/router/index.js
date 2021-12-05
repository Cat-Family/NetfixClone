import { createRouter, createWebHashHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/Login.vue";

const history = createWebHashHistory();
const routes = [
  {
    path: "/",
    component: Home,
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/all",
    component: Home,
  },
];
const router = createRouter({
  history,
  routes,
});
export default router;
