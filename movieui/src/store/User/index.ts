import router from "../../router";
import { routes, actions } from "../../helpers/constants";
import axios from "axios";

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
      console.log(payload.phone, payload.email, payload.password);
    },

    signIn({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
      console.log(payload);
      axios
        .post("http://m39973w600.zicp.vip/user/login", payload)
        .then((user) => {
          commit(actions.setLoading, false);
          commit(actions.setUser, user);
        })
        .catch((error) => {
          commit(actions.setLoading, false);
          commit(actions.setError, error);
        });
    },
    signInEmail({ commit }, payload) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
      console.log(payload);
    },
  },
  getters: {
    user(state) {
      return state.user;
    },
  },
};
