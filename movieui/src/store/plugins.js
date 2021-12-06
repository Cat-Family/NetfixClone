import { createLogger } from "vuex";
import { STORAGE_KEY } from "./mutations";

const LocalStoragePlugin = (store) => {
  store.subscribe((mutation, { todos }) => {
    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(todos));
  });
};

export default process.env.NODE_ENV !== "production"
  ? [createLogger(), LocalStoragePlugin]
  : [LocalStoragePlugin];
