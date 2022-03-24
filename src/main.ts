import { createApp } from "vue";
import App from "./App.vue";

/** Add fontawesome */
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import "element-plus/dist/index.css";

/** Module */
import store from "./store";
import router from "./router";

import VuePlyr from "vue-plyr";
import "vue-plyr/dist/vue-plyr.css";

import "./index.scss";
import "./helpers/fontawesome.js";

const app = createApp(App);
app.use(router);
app.use(store);
app.use(VuePlyr);
app.component("font-awesome-icon", FontAwesomeIcon);
app.mount("#app");
