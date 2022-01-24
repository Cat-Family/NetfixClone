import { createStore } from "vuex";
import user from "./User";
import shared from "./shared";
import myList from "./myList";

const store = createStore({
  modules: {
    user: user,
    shared: shared,
    myList: myList,
  },
});

export default store;
