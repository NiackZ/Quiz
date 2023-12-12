import {createRouter, createWebHistory} from "vue-router";
import Main from "../views/Main.vue";
import Admin from "../views/Admin/Admin.vue";
import CreateTitle from "../views/Admin/CreateTitle.vue";
import Item from "../views/Public/Content/Item.vue";
import Profile from "../views/User/Profile.vue";
import Error403 from "../views/Error/Error403.vue";
import Error404 from "../views/Error/Error404.vue";
import AuthRequired from "../views/Error/AuthRequired.vue";
import {store} from "../store/index.js";
import {checkRights} from "../utils/utils.js";
import {RIGHTS} from "../constants/constants.js";
import {ANIME_DETAIL_ROUTE, ANIME_GRID_ROUTE} from "./routeConstants.js";

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
        path: '/admin/anime',
        component: () => import('../views/Admin/Anime/AnimeGrid.vue'),
        name: ANIME_GRID_ROUTE,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/anime/:id(\\d+)',
        name: ANIME_DETAIL_ROUTE,
        component: () => import('../views/Admin/Anime/AnimeDetail.vue'),
        props: true,
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
            path: '/auth-required'
        }
    }

    if (to.fullPath.startsWith('/admin')) {
        const allowEnter = await checkRights(store.state.auth.user.id, [RIGHTS.ADMIN_PANEL_READ]);
        if (!allowEnter.data) {
            return {
                path: '/403'
            }
        }
    }
})

export default router