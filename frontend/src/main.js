import { createApp } from 'vue'
import App from './App.vue'
import { vuetify } from '../vuetify'
import myComponents from '../src/components'
import router from "./router/router.js";
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import '@mdi/font/css/materialdesignicons.css'
import './styles/custom.css'

const app = createApp(App)

myComponents.forEach(component => {
    app.component(component.name, component)
})

app
    .use(router)
    .use(vuetify)
    .mount('#app')
