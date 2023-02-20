// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'

export const vuetify = createVuetify({
    theme: {
        defaultTheme: 'dark'
    },
    locale: {
        locale: 'ru',
        fallback: 'en'
    }
})