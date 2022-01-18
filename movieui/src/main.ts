import { createApp } from "vue";
import App from "./App.vue";

/** Add fontawesome */
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
/* import the fontawesome core */
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPhone } from "@fortawesome/free-solid-svg-icons/";
library.add(faPhone);

import "element-plus/dist/index.css";

import vClickOutside from "click-outside-vue3";

/** Module */
import store from "./store";
import router from "./router";

import "./index.scss";
import "./helpers/fontawesome.js";

const app = createApp(App);
app.use(vClickOutside);
app.use(router);
app.use(store);
app.component("font-awesome-icon", FontAwesomeIcon);
app.mount("#app");
