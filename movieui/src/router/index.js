import { createRouter, createWebHashHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/Login.vue";
import Layout from "../views/Layout.vue";

const history = createWebHashHistory();
const routes = [
  {
    path: "/",
    component: Layout,
    children: [
      {
        path: '/',
        component: Home,
      },
    ],
  },
  {
    path: "/login",
    component: Login,
  },
];
const router = createRouter({
  history,
  routes,
});
export default router;
