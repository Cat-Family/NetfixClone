<template>
  <div class="SignIn">
    <div class="bg tile">
      <div class="tile__container">
        <h1 class="tile__title">登录</h1>
        <form @submit.prevent="onSignIn" class="form">
          <!-- 密码登录 -->
          <div v-if="!signInEmail" class="form__field">
            <div class="input__wrapper">
              <input
                id="name"
                required
                placeholder="Email"
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
          <div v-if="!signInEmail" class="form__field">
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
          <!-- 邮箱验证登录 -->
          <div v-if="signInEmail" class="form__field">
            <div class="input__wrapper">
              <input
                id="email"
                type="email"
                required
                placeholder="email"
                v-model="email"
                :class="[{ 'input--filled': phone }, 'input']"
              />
              <label class="input__placeholder" for="email"> 邮箱 </label>
            </div>
          </div>
          <div v-if="signInEmail && signInEmailNext" class="form__field">
            <div class="input__wrapper">
              <input
                id="code"
                type="code"
                required
                placeholder="code"
                v-model="code"
                :class="[{ 'input--filled': code }, 'input']"
              />
              <label class="input__placeholder" for="phone"> 验证码 </label>
            </div>
          </div>
          <div class="form__btns">
            <button
              v-if="!signInEmail"
              type="submit"
              class="btn btn--primary"
              :disabled="loading"
            >
              登录
            </button>
            <button
              v-if="!signInEmail"
              type="button"
              class="btn btn--secondary"
              :disabled="loading"
              @click.prevent="onSignInByPhone"
            >
              <i class="SignIn__social-icon">
                <font-awesome-icon :icon="['fas', 'phone']" />
              </i>
              帐号密码登录
            </button>
            <button
              v-if="signInEmail && signInEmailNext"
              class="btn btn--primary"
              :disabled="loading"
              @click.prevent="onSignInEmail"
            >
              登录
            </button>
            <button
              v-if="signInEmail && !signInEmailNext"
              type="button"
              class="btn btn--primary"
              :disabled="loading"
              @click.prevent="onSignInEmailNext"
            >
              获取验证码
            </button>
            <button
              v-if="signInEmail"
              type="button"
              class="btn btn--secondary"
              :disabled="loading"
              @click.prevent="onSignByEmail"
            >
              <i class="SignIn__social-icon">
                <font-awesome-icon :icon="['fas', 'phone']" />
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
import Spinner from "../../components/Spinner/Spinner.vue";
import { routes, actions } from "../../helpers/constants";

export default {
  name: "SignIn",
  data() {
    return {
      name: "",
      email: "",
      phone: "",
      password: "",
      code: "",
      rememberMe: false,
      signUpRoute: routes.signUp,
      signInEmail: false,
      signInEmailNext: false,
    };
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
  methods: {
    onSignIn() {
      if (!this.signInEmail) {
        this.$store.dispatch(actions.signIn, {
          name: this.name,
          password: this.password,
          rememberMe: this.rememberMe,
        });
      }
    },
    onSignInEmail() {
      this.$store.dispatch(actions.signInEmail, {
        phone: this.phone,
        code: this.code,
        rememberMe: this.rememberMe,
      });
    },
    onSignInByPhone() {
      this.signInEmail = true;
    },
    onSignInEmailNext() {
      if (this.email == "" || this.email == null) {
      } else {
        this.signInEmailNext = true;
      }
    },
    onSignByEmail() {
      this.signInEmail = false;
    },
  },
  destroyed() {
    this.$store.dispatch(actions.clearError);
  },
};
</script>

<style lang="scss">
@import "SignIn";
</style>
