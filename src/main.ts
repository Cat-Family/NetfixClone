import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import { router } from './router'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

loadFonts()

createApp(App)
  .use(vuetify)
  .use(router)
  .mount('#app')
