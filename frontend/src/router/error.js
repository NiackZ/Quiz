import Error403 from "../views/Error/Error403.vue";
import Error404 from "../views/Error/Error404.vue";
import AuthRequired from "../views/Error/AuthRequired.vue";

export const errorRoutes = [
    { path: '/403', component: Error403 },
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: Error404 },
    { path: '/auth-required', component: AuthRequired }
];
