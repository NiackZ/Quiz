export const userRoutes = [
    {
        path: '/profile',
        name: 'profile',
        component: () => import('../views/User/Profile.vue'),
        meta: {
            requiresAuth: true,
            title: "Профиль"
        }
    }
];
