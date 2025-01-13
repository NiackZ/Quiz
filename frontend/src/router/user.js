import Profile from "../views/User/Profile.vue";

export const userRoutes = [
    {
        path: '/profile',
        name: 'profile',
        component: () => import('../views/User/Profile.vue'),
        meta: { requiresAuth: true }
    }
];
