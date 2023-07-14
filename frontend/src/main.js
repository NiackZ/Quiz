import { createApp } from 'vue'
import { vuetify } from '../vuetify'
import App from './App.vue'
import myComponents from '../src/components'
import router from "./router/router.js";
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import '@mdi/font/css/materialdesignicons.css'
import './styles/custom.css'
import {store} from "./store/index.js"; //Vuex

const app = createApp(App)

myComponents.forEach(component => {
    app.component(component.name, component)
})

app
    .use(store)
    .use(router)
    .use(vuetify)
    .mount('#app')
