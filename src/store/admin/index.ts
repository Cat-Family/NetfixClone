import { ElMessage } from "element-plus";
import { routes, actions } from "../../helpers/constants";
import instance from "../../request";
import router from "../../router/index";

export default {
    state: {

    },
    mutations: {
        setUsers(state, payload) {
            state.users = payload;
        },
    },
    actions: {
        getAllUsers({ commit }) {
            commit(actions.setLoading, true);
            commit(actions.clearError);
            instance
                .post(`/user/all`)
                .then((res) => {
                    commit(actions.setLoading, false);
                    commit("setUsers", res.data.data);
                })
                .catch((error) => {
                    commit(actions.setLoading, false);
                    commit(
                        actions.setError,
                        error.response.data.msg ? error.response.data.msg : error
                    );
                });
        },
        clearAllUsers({ commit }) {
            commit("setUsers", null);
        }
    },
    getters: {
        users(state) {
            return state.users;
        },
    },
};
