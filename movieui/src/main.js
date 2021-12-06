import { createApp } from "vue";
import App from "./App.vue";
import naive from "naive-ui";
import router from "./router/index.js";
import store from "./store";

// 通用字体
import "vfonts/Lato.css";
// 等宽字体
import "vfonts/FiraCode.css";

createApp(App).use(router).use(store).use(naive).mount("#app");
