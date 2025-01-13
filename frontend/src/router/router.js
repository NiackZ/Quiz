import {createRouter, createWebHistory} from "vue-router";
import {store} from "../store/index.js";
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

router.beforeEach(async (to, from, next) => {
    try {
        const isAuthenticated = await store.dispatch('auth/validateToken');
        if (to.meta.requiresAuth && !isAuthenticated) {
            return next({ path: '/auth-required' });
        }

        const hasAccess = await store.dispatch('auth/checkAccess', to.meta.requiredRights);
        if (!hasAccess) {
            return next({ path: '/403' });
        }

        next();
    } catch (error) {
        console.error("Error in router guard:", error);
        return next({ path: '/error' }); // Например, страница ошибки
    }
});


export default router