import {createRouter, createWebHistory} from "vue-router";
import {store} from "../store/index.js";
import {checkRights} from "../utils/utils.js";
import {RIGHTS} from "../constants/constants.js";
import {adminRoutes} from "./admin.js";
import {mainRoutes} from "./main.js";
import {errorRoutes} from "./error.js";
import {userRoutes} from "./user.js";

const routes = [
    ...mainRoutes,
    ...adminRoutes,
    ...errorRoutes,
    ...userRoutes
];

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