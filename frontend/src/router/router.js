import {createRouter, createWebHistory} from "vue-router";
import Main from "../pages/Main.vue";
import Admin from "../pages/Admin/Admin.vue";
import CreateTitle from "../pages/Admin/CreateTitle.vue";
import Item from "../pages/Public/Content/Item.vue";
import Profile from "../pages/User/Profile.vue";
import Error403 from "../pages/Error/Error403.vue";
import Login from "../pages/Public/Login.vue";
import {LOGIN_PATH} from "../constants/constants.js";

const routes = [
    {
        path: '/',
        component: Main
    },
    {
        path: '/admin',
        component: Admin,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/create/title',
        component: CreateTitle,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/item/:id',
        component: Item,
    },
    {
        path: '/profile',
        component: Profile,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/403',
        component: Error403
    },
    {
        path: LOGIN_PATH,
        component: Login,
        meta: {
            requiresAuth: false
        }
    }
]

const router = createRouter({
    routes,
    history: createWebHistory(import.meta.env.BASE_URL)
})

export default router