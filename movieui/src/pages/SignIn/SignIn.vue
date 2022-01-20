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
            <button type="submit" class="btn btn--primary" :disabled="loading">
              登录
            </button>
            <button
              type="button"
              class="btn btn--secondary"
              :disabled="loading"
              @click.prevent="onSignInByEmail"
            >
              <i class="SignIn__social-icon">
                <font-awesome-icon :icon="['fas', 'envelope']" />
              </i>
              邮箱验证登录
            </button>
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
            <button
              v-if="signInEmail"
              type="button"
              class="btn btn--primary"
              :disabled="loading"
              disable
              @click="onSignInEmailNext"
            >
              {{ codeBtnWord }}
            </button>
          </div>
          <div class="form__btns">
            <button class="btn btn--primary" :disabled="loading">登录</button>
            <button
              v-if="signInEmail"
              type="button"
              class="btn btn--secondary"
              :disabled="loading"
              @click.prevent="onSignByEmail"
            >
              <i class="SignIn__social-icon">
                <font-awesome-icon :icon="['fas', 'user']" />
              </i>
              帐号密码登录
            </button>
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
</template>

<script>
import { ElMessage } from "element-plus";
import Spinner from "../../components/Spinner/Spinner.vue";
import { routes, actions } from "../../helpers/constants";
import { useStore } from "vuex";
import { ref } from "vue-demi";
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
    let name = ref("");
    let email = ref("");
    let password = ref("");
    let code = ref("");
    let rememberMe = ref(false);
    let signUpRoute = ref(routes.signUp);
    let signInEmailNext = ref(false);
    let codeBtnWord = ref("获取验证码"); // 获取验证码按钮文字
    let waitTime = ref(61); // 获取验证码按钮失效时间
    let signInEmail = ref(localStorage.getItem("signInEmail") || 0);
    const onSignInEmailNext = () => { 
      if (waitTime.value === 61) {
        // store.dispatch(actions.sendEmial, {
        //   email: email.value,
        // });
        instance
          .post(`/user/getCheckCode?email=${email.value}`)
          .then((res) => {
            waitTime.value--;
            codeBtnWord.value = `${waitTime.value}s 后重新获取`;
            let timer = setInterval(function () {
              if (waitTime.value > 1) {
                waitTime.value--;
                codeBtnWord.value = `${waitTime.value}s 后重新获取`;
              } else {
                clearInterval(timer);
                codeBtnWord.value = "获取验证码";
                getCodeBtnDisable.value = false;
                waitTime.value = 61;
              }
            }, 1000);
          })
          .catch((error) => {});
      } else {
        ElMessage.error("请勿频繁请求验证码");
      }
    };
    const onSignIn = () => {
      store.dispatch(actions.signIn, {
        name: name.value,
        password: password.value,
        rememberMe: rememberMe.value,
      });
    };
    const onSignInEmail = () => {
      store.dispatch(actions.signInEmail, {
        email: email.value,
        code: code.value,
        rememberMe: rememberMe.value,
      });
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
