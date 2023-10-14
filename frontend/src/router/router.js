import {createRouter, createWebHistory} from "vue-router";
import Main from "../pages/Main.vue";
import Admin from "../pages/Admin/Admin.vue";
import CreateTitle from "../pages/Admin/CreateTitle.vue";
import Item from "../pages/Public/Content/Item.vue";
import Profile from "../pages/User/Profile.vue";
import Error403 from "../pages/Error/Error403.vue";
import Error404 from "../pages/Error/Error404.vue";
import AuthRequired from "../pages/Error/AuthRequired.vue";
import {store} from "../store/index.js";

const routes = [
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: Error404 },
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
        name: 'createTitle',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/item/:id(\\d+)',
        component: Item,
        props: true
    },
    {
        path: '/profile',
        name: 'profile',
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
        path: "/auth-required",
        component: AuthRequired
    }
]

const router = createRouter({
    routes,
    history: createWebHistory(import.meta.env.BASE_URL)
})

router.beforeEach(async  (to, from) => {
    const isAuthenticated = await store.dispatch('auth/validateToken');
    if (to.meta.requiresAuth && !isAuthenticated) {
        return {
            path: '/'
        }
    }
})

export default router