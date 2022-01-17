<template>
  <div class="SignIn">
    <div class="bg tile">
      <div class="tile__container">
        <h1 class="tile__title">Sign In</h1>
        <form @submit.prevent="onSignIn" class="form">
          <div class="form__field">
            <div v-if="!signInEmail" class="input__wrapper">
              <input
                id="email"
                required
                placeholder="Email"
                v-model="email"
                :class="[
                  {
                    'input--filled': email,
                  },
                  'input',
                ]"
              />
              <label class="input__placeholder" for="email"> Email </label>
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
              <label class="input__placeholder" for="password">
                Password
              </label>
            </div>
          </div>
          <div v-if="signInEmail" class="form__field">
            <div class="input__wrapper">
              <input
                id="phone"
                type="phone"
                required
                placeholder="phone"
                v-model="phone"
                :class="[{ 'input--filled': phone }, 'input']"
              />
              <label class="input__placeholder" for="phone"> Phone </label>
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
              <label class="input__placeholder" for="phone"> Code </label>
            </div>
          </div>
          <div class="form__btns">
            <button
              v-if="!signInEmail"
              type="submit"
              class="btn btn--primary"
              :disabled="loading"
            >
              Sign In
            </button>
            <button
              v-if="signInEmail && signInEmailNext"
              class="btn btn--primary"
              :disabled="loading"
              @click.prevent="onSignInEmail"
            >
              Sign In
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
              Login with Phone
            </button>
            <button
              v-if="signInEmail && !signInEmailNext"
              type="button"
              class="btn btn--primary"
              :disabled="loading"
              @click.prevent="onSignInEmailNext"
            >
              Next
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
              Login with Email
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
                <span class="checkbox__text"> Remember me </span>
              </label>
            </div>
            <a href="https://www.netflix.com/LoginHelp" class="link link--s">
              Need help ?
            </a>
          </div>
        </form>
        <ul class="SignIn__social-list">
          <li class="SignIn_social-item"></li>
        </ul>
        <p>
          New to netflix?
          <router-link class="link link--white" :to="signUpRoute"
            >Sign up now.</router-link
          >
        </p>
        <div class="Spinner__overflow" v-if="loading">
          <Spinner />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" >
import { ElMessage } from "element-plus";
import Spinner from "../../components/Spinner/Spinner.vue";
import { routes, actions } from "../../helpers/constants";

export default {
  name: "SignIn",
  data() {
    return {
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
    error() {},
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
          email: this.email,
          password: this.password,
          rememberMe: this.rememberMe,
        });
      }
    },
    onSignInEmail() {
      ElMessage.error("test");
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
      if (this.phone == "" || this.phone == null) {
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
