import Profile from "../views/User/Profile.vue";

export const userRoutes = [
    {
        path: '/profile',
        name: 'profile',
        component: Profile,
        meta: { requiresAuth: true }
    }
];
