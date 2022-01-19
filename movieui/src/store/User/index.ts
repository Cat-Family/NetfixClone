import { ElMessage } from "element-plus";
import { routes, actions } from "../../helpers/constants";
import instance from "../../request";
import router from "../../router/index";

export default {
  state: {
    user: {
      token: localStorage.getItem("token"),
      email: localStorage.getItem("email"),
      username: localStorage.getItem("username"),
      id: localStorage.getItem("id"),
    },
  },
  mutations: {
    setUser(state, payload) {
      state.user = payload;
    },
  },
  actions: {
    signUp({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
      instance
        .post(`/user/register`, payload)
        .then((res) => {
          commit(actions.setLoading, false);
        })
        .catch((error) => {
          commit(actions.setLoading, false);
          commit(
            actions.setError,
            error.response.data.msg ? error.response.data.msg : error
          );
        });
    },
    signIn({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
      instance
        .post(`/user/login`, payload)
        .then((res) => {
          commit(actions.setLoading, false);
          commit(actions.setUser, res.data.data);
          const userInfo = res.data.data;
          if (payload.rememberMe) {
            localStorage.setItem("token", userInfo.token);
            localStorage.setItem("email", userInfo.email);
            localStorage.setItem("username", userInfo.username);
            localStorage.setItem("id", userInfo.id);
          }
        })
        .catch((error) => {
          commit(actions.setLoading, false);
          commit(
            actions.setError,
            error.response?.data.msg ? error.response.data.msg : error
          );
        });
    },
    signInEmail({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
    },
    logout({ commit }) {
      commit(actions.setUser, null);
      localStorage.clear();
      router.push(routes.startNow);
      ElMessage.success("注销成功");
    },
  },
  getters: {
    user(state) {
      return state.user;
    },
  },
};
