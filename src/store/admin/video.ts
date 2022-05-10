import { ElMessage } from "element-plus";
import { routes, actions } from "../../helpers/constants";
import instance from "../../request";
import router from "../../router/index";

export default {
  state: {},
  mutations: {
    setVideos(state, payload) {
      state.videos = payload;
    },
  },
  actions: {
    getMovies({ commit }) {
      commit(actions.setLoading, true);
      commit(actions.clearError);
      instance
        .post(`/movie/list-movie`, { info: "" })
        .then((res) => {
          commit(actions.setLoading, false);
          commit("setVideos", res.data.data);
        })
        .catch((error) => {
          commit(actions.setLoading, false);
          commit(
            actions.setError,
            error.response.data.msg ? error.response.data.msg : error
          );
        });
    },
    clearMovies({ commit }) {
      commit("setVideos", null);
    },
  },
  getters: {
    movies(state) {
      return state.videos;
    },
  },
};
