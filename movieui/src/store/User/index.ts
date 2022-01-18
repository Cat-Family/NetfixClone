import router from "../../router";
import { routes, actions } from "../../helpers/constants";
import axios from "axios";
import { ElMessage } from "element-plus";

const baseUrl = "http://localhost:8080";
//const baseUrl = "390gq17426.wicp.vip";

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
      axios
        .post(`${baseUrl}/user/register`, payload)
        .then((res) => {
          commit(actions.setLoading, false);
          if (res.data.code == 200) {
            ElMessage.success("注册成功");
          } else {
            commit(actions.setError, res.data.msg);
            ElMessage.error(res.data.msg);
          }
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
    signIn({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
      axios
        .post(`${baseUrl}/user/login`, payload)
        .then((res) => {
          commit(actions.setLoading, false);
          if (res.data.code == 200) {
            commit(actions.setUser, res.data.data);
            const userInfo = res.data.data;
            if (payload.rememberMe) {
              localStorage.setItem("token", userInfo.token);
              localStorage.setItem("email", userInfo.email);
              localStorage.setItem("username", userInfo.username);
              localStorage.setItem("id", userInfo.id);
            }
            ElMessage.success("登录成功");
          } else {
            commit(actions.setError, res.data.msg);
            ElMessage.error(res.data.msg);
          }
        })
        .catch((error) => {
          commit(actions.setLoading, false);
          commit(
            actions.setError,
            error.response?.data.msg ? error.response.data.msg : error
          );
          ElMessage.error(
            error.response?.data.msg ? error.response.data.msg : error
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
