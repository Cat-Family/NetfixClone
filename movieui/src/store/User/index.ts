import router from "../../router";
import { routes, actions } from "../../helpers/constants";
import axios from "axios";
import { ElMessage } from "element-plus";

export default {
  state: {
    user: null,
  },
  mutations: {
    setUser(state, payload) {
      state.user = payload;
    },
  },
  actions: {
    singnUp({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
    },

    signIn({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
      axios
        .post("http://localhost:8080/user/login", payload)
        .then((user) => {
          commit(actions.setLoading, false);
          commit(actions.setUser, user);
          ElMessage.success("登录成功");
        })
        .catch((error) => {
          commit(actions.setLoading, false);
          commit(
            actions.setError,
            error.response.data.msg ? error.response.data.msg : error
          );
          ElMessage.error(
            error.response.data.msg ? error.response.data.msg : error
          );
        });
    },
    signInEmail({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
    },
  },
  getters: {
    user(state) {
      return state.user;
    },
  },
};
