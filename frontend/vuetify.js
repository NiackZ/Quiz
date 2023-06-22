// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import { ru } from 'vuetify/locale'

export const vuetify = createVuetify({
    theme: {
        defaultTheme: 'dark' // dark light
    },
    locale: {
        locale: 'ru',
        fallback: 'en',
        messages: { ru }
    }
})