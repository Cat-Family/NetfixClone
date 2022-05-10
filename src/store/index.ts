import { createStore } from "vuex";
import user from "./User";
import shared from "./shared";
import myList from "./myList";
import admin from "./admin";
import video from "./admin/video";

const store = createStore({
  modules: {
    user: user,
    shared: shared,
    myList: myList,
    admin: admin,
    video: video,
  },
});

export default store;
