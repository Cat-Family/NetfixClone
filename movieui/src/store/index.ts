import { createApp } from "vue";
import { createStore } from "vuex";

const app = createApp({
  /* your root component */
});

const store = createStore({});

app.use(store);
