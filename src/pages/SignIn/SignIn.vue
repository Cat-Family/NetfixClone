<template>
  <div class="SignIn">
    <div class="bg tile">
      <div class="tile__container">
        <h1 class="tile__title">登录</h1>
        <form v-if="signInEmail == 0" @submit.prevent="onSignIn" class="form">
          <!-- 密码登录 -->
          <div class="form__field">
            <div class="input__wrapper">
              <input
                id="name"
                required
                placeholder="name"
                v-model="name"
                :class="[
                  {
                    'input--filled': name,
                  },
                  'input',
                ]"
              />
              <label class="input__placeholder" for="name">
                邮箱/手机/用户名
              </label>
            </div>
          </div>
          <div class="form__field">
            <div class="input__wrapper">
              <input
                id="password"
                type="password"
                required
                placeholder="Password"
                v-model="password"
                :class="[{ 'input--filled': password }, 'input']"
              />
              <label class="input__placeholder" for="password"> 密码 </label>
            </div>
          </div>
          <div class="form__btns">
            <el-button
              style="height: 3rem"
              type="danger"
              class="btn btn--primary"
              :loading="loading"
              @click="onSignIn"
            >
              登录
            </el-button>
            <el-button
              style="height: 3rem; margin: 0"
              color="transition"
              class="btn btn--secondary"
              @click.prevent="onSignInByEmail"
            >
              <i class="SignIn__social-icon">
                <font-awesome-icon :icon="['fas', 'envelope']" />
              </i>
              邮箱验证登录
            </el-button>
          </div>
          <div class="flex-jc">
            <div class="checkbox__wrapper">
              <input
                id="rememberMeCheckbox"
                type="checkbox"
                class="checkbox__input"
                v-model="rememberMe"
              />
              <label for="rememberMeCheckbox" class="checkbox">
                <span>
                  <svg width="12px" height="10px" viewBox="0 0 12 10">
                    <polyline points="1.5 6 4.5 9 10.5 1" />
                  </svg>
                </span>
                <span class="checkbox__text"> 免密登录 </span>
              </label>
            </div>
            <a class="link link--s"> 需要帮助? </a>
          </div>
        </form>
        <form
          v-if="signInEmail == 1"
          @submit.prevent="onSignInEmail"
          class="form"
        >
          <!-- 邮箱验证登录 -->
          <div v-if="signInEmail" class="form__field">
            <div class="input__wrapper">
              <input
                id="email"
                type="email"
                required
                placeholder="email"
                v-model="email"
                :class="[{ 'input--filled': email }, 'input']"
              />
              <label class="input__placeholder" for="email"> 邮箱 </label>
            </div>
          </div>
          <div class="form__field" style="display: flex">
            <div class="input__wrapper" style="flex: 1">
              <input
                id="code"
                type="code"
                required
                placeholder="code"
                v-model="code"
                :class="[{ 'input--filled': code }, 'input']"
              />
              <label class="input__placeholder" for="code"> 验证码 </label>
            </div>
            <el-button
              v-if="signInEmail"
              style="height: 3rem"
              type="danger"
              class="btn btn--primary"
              @click="onSignInEmailNext"
              :plain="btnDisable"
              :disabled="btnDisable"
              :loading="sendMailLoading"
            >
              {{ codeBtnWord }}
            </el-button>
          </div>
          <div class="form__btns">
            <el-button
              style="height: 3rem"
              class="btn btn--primary"
              type="danger"
              :loading="loading"
              @click="onSignInEmail"
              >登录</el-button
            >
            <el-button
              style="height: 3rem; margin: 0"
              v-if="signInEmail"
              color="transition"
              class="btn btn--secondary"
              @click.prevent="onSignByEmail"
            >
              <i class="SignIn__social-icon">
                <font-awesome-icon :icon="['fas', 'user']" />
              </i>
              帐号密码登录
            </el-button>
          </div>
          <div class="flex-jc">
            <div class="checkbox__wrapper">
              <input
                id="rememberMeCheckbox"
                type="checkbox"
                class="checkbox__input"
                v-model="rememberMe"
              />
              <label for="rememberMeCheckbox" class="checkbox">
                <span>
                  <svg width="12px" height="10px" viewBox="0 0 12 10">
                    <polyline points="1.5 6 4.5 9 10.5 1" />
                  </svg>
                </span>
                <span class="checkbox__text"> 免密登录 </span>
              </label>
            </div>
            <a class="link link--s" href="/RecoverPassword"> 需要帮助? </a>
          </div>
        </form>
        <ul class="SignIn__social-list">
          <li class="SignIn_social-item"></li>
        </ul>
        <p>
          还没有帐户？
          <router-link class="link link--white" :to="signUpRoute"
            >立即注册。</router-link
          >
        </p>
        <div class="Spinner__overflow" v-if="loading">
          <Spinner />
        </div>
      </div>
    </div>
  </div>
  <el-dialog
    v-model="dialogVisible"
    title="提示"
    :open-delay="500"
    :close-delay="500"
    top="30vh"
    width="28rem"
  >
    <span>该邮箱尚未注册，使用验证码登录将会自动注册。</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" @click="dialogVisible = false">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { ElMessage } from "element-plus";
import Spinner from "../../components/Spinner/Spinner.vue";
import { routes, actions } from "../../helpers/constants";
import { useStore } from "vuex";
import { ref } from "vue";
import instance from "../../request";

export default {
  name: "SignIn",
  data() {
    return {};
  },
  computed: {
    user() {
      return this.$store.getters.user;
    },
    loading() {
      return this.$store.getters.loading;
    },
  },
  components: {
    Spinner,
  },
  watch: {
    user(value) {
      if (value !== null && value !== undefined) {
        this.$router.push(routes.home);
      }
    },
  },
  setup() {
    const store = useStore();
    const name = ref("");
    const email = ref("");
    const password = ref("");
    const code = ref("");
    const rememberMe = ref(true);
    const signUpRoute = ref(routes.signUp);
    const signInEmailNext = ref(false);
    const codeBtnWord = ref("获取验证码"); // 获取验证码按钮文字
    const waitTime = ref(61); // 获取验证码按钮失效时间
    const signInEmail = ref(localStorage.getItem("signInEmail") || 0);
    const dialogVisible = ref(false);
    const btnDisable = ref(false);
    const sendMailLoading = ref(false);

    const onSignInEmailNext = () => {
      if (email.value == null || email.value == "") {
        ElMessage.error("邮箱不能为空!");
      } else {
        if (
          /^[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/.test(
            email.value
          )
        ) {
          btnDisable.value = true;
          if (waitTime.value === 61) {
            sendMailLoading.value = true;
            instance
              .post(`/user/getCheckCode?email=${email.value}`)
              .then((res) => {
                if (res.data.code === 404) {
                  dialogVisible.value = true;
                  waitTime.value--;
                  codeBtnWord.value = `${waitTime.value}s 后重新获取`;
                  let timer = setInterval(function () {
                    if (waitTime.value > 1) {
                      waitTime.value--;
                      codeBtnWord.value = `${waitTime.value}s 后重新获取`;
                    } else {
                      clearInterval(timer);
                      codeBtnWord.value = "获取验证码";
                      waitTime.value = 61;
                      btnDisable.value = false;
                    }
                  }, 1000);
                  sendMailLoading.value = false;
                } else {
                  waitTime.value--;
                  codeBtnWord.value = `${waitTime.value}s 后重新获取`;
                  let timer2 = setInterval(function () {
                    if (waitTime.value > 1) {
                      waitTime.value--;
                      codeBtnWord.value = `${waitTime.value}s 后重新获取`;
                    } else {
                      clearInterval(timer2);
                      codeBtnWord.value = "获取验证码";
                      waitTime.value = 61;
                      btnDisable.value = false;
                    }
                  }, 1000);
                  sendMailLoading.value = false;
                }
              })
              .catch((error) => {
                sendMailLoading.value = false;
                btnDisable.value = false;
              });
          } else {
            ElMessage.error("请勿频繁请求验证码");
          }
        } else {
          ElMessage.error("邮箱格式错误");
        }
      }
    };
    const onSignIn = () => {
      if (name.value == null || name.value === "") {
        ElMessage.warning("请输入邮箱、手机或用户名!");
      } else if (password.value == null || password.value === "") {
        ElMessage.warning("请输入密码!");
      } else {
        store.dispatch(actions.signIn, {
          name: name.value,
          password: password.value,
          rememberMe: rememberMe.value,
        });
      }
    };
    const onSignInEmail = () => {
      if (email.value == null || email.value === "") {
        ElMessage.warning("请输入邮箱!");
      } else if (code.value == null || code.value === "") {
        ElMessage.warning("请输入验证码!");
      } else {
        store.dispatch(actions.signInEmail, {
          email: email.value,
          code: code.value,
          rememberMe: rememberMe.value,
        });
      }
    };
    const onSignInByEmail = () => {
      localStorage.setItem("signInEmail", 1);
      signInEmail.value = 1;
    };

    const onSignByEmail = () => {
      localStorage.setItem("signInEmail", 0);
      signInEmail.value = 0;
    };
    return {
      onSignInEmailNext,
      onSignIn,
      onSignInEmail,
      onSignInByEmail,
      onSignByEmail,
      signInEmail,
      email,
      name,
      password,
      code,
      signInEmailNext,
      signUpRoute,
      codeBtnWord,
      waitTime,
      rememberMe,
      dialogVisible,
      btnDisable,
      sendMailLoading,
    };
  },
  methods: {},
  destroyed() {
    this.$store.dispatch(actions.clearError);
  },
};
</script>

<style lang="scss">
@import "SignIn";
</style>
