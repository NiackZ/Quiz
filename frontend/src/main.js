import { createApp } from 'vue'
import { vuetify } from '../vuetify'
import App from './App.vue'
//import myComponents from '../src/components'
import router from "./router/router.js";
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import '@mdi/font/css/materialdesignicons.css'
import './styles/custom.css'
import {store} from "./store/index.js"; //Vuex

const app = createApp(App)


app.use(router)
   .use(store)
   .use(vuetify)
   .mount('#app')
