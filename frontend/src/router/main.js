import Main from "../views/Main.vue";
import Item from "../views/Public/Content/Item.vue";

export const mainRoutes = [
    {
        path: '/',
        component: Main,
        meta: {
            title: "Главная"
        }
    },
    {
        path: '/item/:id(\\d+)',
        component: Item,
        props: true,
        meta: {
            title: "Anime"
        }
    }
];
