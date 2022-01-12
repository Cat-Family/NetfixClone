import { createApp } from "vue";
import App from "./App.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faPhone } from "@fortawesome/free-solid-svg-icons/";
/* import the fontawesome core */
import { library } from "@fortawesome/fontawesome-svg-core";

import router from "./router";
import "./index.scss";
import "./helpers/fontawesome.js";

library.add(faPhone);

const app = createApp(App);
app.use(router);
app.component("font-awesome-icon", FontAwesomeIcon);
app.mount("#app");
