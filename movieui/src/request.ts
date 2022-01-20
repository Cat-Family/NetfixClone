import axios from "axios";
import { ElMessage } from "element-plus";
import { actions } from "./helpers/constants";
import store from "./store";

// toekn
const AUTH_TOKEN = "";

//const baseUrl = "390gq17426.wicp.vip";
// export const baseURL = "http://m39973w600.zicp.vip";
export const baseURL = "http://6gj3vj.natappfree.cc";

const instance = axios.create({
  baseURL,
});

instance.defaults.headers.common["Authorization"] = AUTH_TOKEN;

instance.defaults.timeout = 25000;

// 重写此请求的超时时间，如果该请求需要很长时间
// instance.get("/longRequest", {
//   timeout: 5000,
// });

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (respose) => {
    if (respose.data.code === 200) {
      ElMessage.success(respose.data.msg);
      return respose;
    } else if (respose.data.code === 401) {
      localStorage.clear();
      store.commit(actions.setUser, null);
      ElMessage.error(respose.data.msg);
      return Promise.reject(respose.data.msg);
    } else {
      ElMessage.error(respose.data.msg);
      return Promise.reject(respose.data.msg);
    }
  },
  (error) => {
    ElMessage.error(error.response?.data.msg ? error.response.data.msg : error);
    return Promise.reject(error);
  }
);

export default instance;
