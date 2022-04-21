import { createRouter, createWebHistory } from "vue-router";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import AdminLayout from "@/layouts/AdminLayout.vue";
import HomePage from "@/pages/home/HomePage.vue";
import DashboardPage from "@/pages/dashboard/DashboardPage.vue";
import SignInPage from "@/pages/signin/SignInPage.vue";
import SignUpPage from "@/pages/signup/SignUpPage.vue";

export const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/admin",
            component: AdminLayout,
            children: [
                { path: "", component: DashboardPage },
                { path: "users", component: DashboardPage },
            ]
        },
        {
            path: "/signin",
            component: SignInPage
        },
        {
            path: "/signup",
            component: SignUpPage
        },
        {
            path: "/",
            component: DefaultLayout,
            children: [
                { path: "", component: HomePage },
            ]
        }
    ]
})