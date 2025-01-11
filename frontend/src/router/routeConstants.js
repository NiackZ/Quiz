import AnimeCreate from "../views/Admin/Anime/AnimeCreate.vue";

export const ANIME_DETAIL_ROUTE = "animeDetail";

/** ADMIN GRID */

export const ADMIN_ROUTE = {
    ANIMES: "animeGrid",
    ANIME_NEW: {
        path: '/admin/anime/new',
        component: AnimeCreate,
        name: 'animeCreate'
    },
    GENRES: 'genreGrid',
    TYPES: 'typeGrid',
    STUDIES: 'studioGrid',
    STATUSES: 'statusGrid'
}