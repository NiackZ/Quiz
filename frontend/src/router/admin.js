import Admin from "../views/Admin/Admin.vue";
import {ADMIN_ROUTE, ANIME_DETAIL_ROUTE} from "./routeConstants.js";
import {RIGHTS} from "../constants/constants.js";

export const adminRoutes = [
    {
        path: '/admin',
        component: Admin,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
    {
        path: '/admin/anime',
        component: () => import('../views/Admin/Anime/AnimeGrid.vue'),
        name: ADMIN_ROUTE.ANIMES,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
    {
        path: '/admin/genre',
        component: () => import('../views/Admin/Genre/GenreGrid.vue'),
        name: ADMIN_ROUTE.GENRES,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
    {
        path: '/admin/anime/:id(\\d+)',
        name: ANIME_DETAIL_ROUTE,
        component: () => import('../views/Admin/Anime/AnimeDetail.vue'),
        props: true,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
    {
        path: ADMIN_ROUTE.ANIME_NEW.path,
        component: ADMIN_ROUTE.ANIME_NEW.component,
        name: ADMIN_ROUTE.ANIME_NEW.name,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
    {
        path: '/admin/type',
        component: () => import('../views/Admin/Type/TypeGrid.vue'),
        name: ADMIN_ROUTE.TYPES,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
    {
        path: '/admin/studio',
        component: () => import('../views/Admin/Studio/StudioGrid.vue'),
        name: ADMIN_ROUTE.STUDIES,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
    {
        path: '/admin/status',
        component: () => import('../views/Admin/Status/StatusGrid.vue'),
        name: ADMIN_ROUTE.STATUSES,
        meta: {
            requiresAuth: true,
            requiredRights: [RIGHTS.ADMIN_PANEL_READ]
        }
    },
]