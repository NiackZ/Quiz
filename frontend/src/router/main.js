import Main from "../views/Main.vue";
import Item from "../views/Public/Content/Item.vue";
import {PATH} from "../constants/constants.js";

export const mainRoutes = [
    {
        path: '/',
        component: Main,
        meta: {
            title: "Главная"
        }
    },
    {
        path: `/${PATH.ANIME}/:id(\\d+)`,
        component: Item,
        props: true,
        meta: {
            title: "Anime"
        }
    }
];
