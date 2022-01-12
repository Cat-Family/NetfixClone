import router from "../../router";
import { routes, actions } from "../../helpers/constants";

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
    },
    signInPhone({ commit }, payload) {
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
