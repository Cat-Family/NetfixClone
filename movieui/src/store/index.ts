import { createStore } from "vuex";
import user from "./User";
import shared from "./shared";

const store = createStore({
  modules: {
    user: user,
    shared: shared,
  },
});

export default store;
