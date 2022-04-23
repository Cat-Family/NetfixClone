import { createStore } from "vuex";
import user from "./User";
import shared from "./shared";
import myList from "./myList";
import admin from "./admin";

const store = createStore({
  modules: {
    user: user,
    shared: shared,
    myList: myList,
    admin: admin
  },
});

export default store;
