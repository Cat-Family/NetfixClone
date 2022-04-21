import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import { router } from './router'
import { library, dom } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import '@mdi/font/css/materialdesignicons.css'

import { fas } from '@fortawesome/free-solid-svg-icons'
library.add(fas);
import { fab } from '@fortawesome/free-brands-svg-icons';
library.add(fab);
import { far } from '@fortawesome/free-regular-svg-icons';
library.add(far);

dom.watch();
loadFonts()

createApp(App)
  .component("font-awesome-icon", FontAwesomeIcon)
  .use(router)
  .use(vuetify)
  .mount('#app')

  