import { createApp } from "vue";
import App from "./App.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import "./index.scss";
import "./helpers/fontawesome.js";

createApp(App).component("font-awesome-icon", FontAwesomeIcon).mount("#app");
