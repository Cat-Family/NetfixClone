<template>
  <div class="tile bg">
    <div class="tile__container">
      <h1 class="tile__title">找回密码</h1>
      <form @submit.prevent="onRecoverPassword">
        <div class="form__field">
          <div
            :class="[
              { 'input--error': errors.password.length !== 0 },
              'input__wrapper',
            ]"
          >
            <input
              id="password"
              name="password"
              type="password"
              required
              placeholder="password"
              v-model="password"
              :class="[{ 'input--filled': password }, 'input']"
            />
            <label class="input__placeholder" for="password"> 密码 </label>
          </div>
          <ul class="form__error-list">
            <li
              :key="index"
              class="form__error-item"
              v-for="(error, index) in errors.password"
            >
              {{ error }}
            </li>
          </ul>
          <ul class="form__required-list">
            <li
              :class="[
                { 'form__required-item--done': password.length >= 8 },
                'form__required-item',
              ]"
            >
              <span class="form__required-text"> 长度至少为 8 个字符 </span>
            </li>
            <li
              :class="[
                {
                  'form__required-item--done':
                    password !== password.toLowerCase(),
                },
                'form__required-item',
              ]"
            >
              <span class="form__required-text"> 一个大写字母 </span>
            </li>
            <li
              :class="[
                {
                  'form__required-item--done':
                    password !== password.toUpperCase(),
                },
                'form__required-item',
              ]"
            >
              <span class="form__required-text"> 一个小写字母 </span>
            </li>
            <li
              :class="[
                { 'form__required-item--done': isLatin(password) },
                'form__required-item',
              ]"
            >
              <span class="form__required-text"> 仅拉丁字符 </span>
            </li>
          </ul>
        </div>
        <div class="form__field">
          <div
            :class="[
              { 'input--error': errors.password.length !== 0 },
              'input__wrapper',
            ]"
          >
            <input
              id="confirm-password"
              name="confirm-password"
              type="password"
              required
              placeholder="Confirm Password"
              v-model="confirmPassword"
              :class="[{ 'input--filled': confirmPassword }, 'input']"
            />
            <label class="input__placeholder" for="confirm-password">
              确认密码
            </label>
          </div>
          <ul class="form__error-list">
            <li
              class="form__error-item"
              v-for="(error, index) in errors.confirmPassword"
              :key="index"
            >
              {{ error }}
            </li>
          </ul>
        </div>
        <div class="form__btns">
          <button type="submit" class="btn btn--primary" :disabled="loading">
            修改密码
          </button>
        </div>
      </form>
      <div class="Spinner__overflow" v-if="loading">
        <Spinner />
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
import Spinner from "../../components/Spinner/Spinner.vue";
import { routes, actions } from "../../helpers/constants";

export default {
  name: "RecoverPasswordForm",
  data() {
    return {
      password: "",
      confirmPassword: "",
      errors: {
        password: [],
        confirmPassword: [],
      },
    };
  },
  computed: {
    user() {
      return this.$store.getters.user;
    },
    error() {
      return this.$store.getters.error;
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
    isFormValid() {
      const { password, confirmPassword } = this;
      this.errors = {
        password: [],
        confirmPassword: [],
      };
      if (password !== confirmPassword) {
        this.errors.confirmPassword.push("两次密码不匹配");
      }
      if (password.length < 8) {
        this.errors.password.push("长度至少为 8 个字符");
      }
      if (
        password === password.toLowerCase() ||
        password === password.toUpperCase()
      ) {
        this.errors.password.push("至少包含一个大小写字母");
      }
      if (!this.isLatin(password)) {
        this.errors.password.push("仅拉丁字符");
      }
      return Object.values(this.errors).every((field) => field.length === 0);
    },
    isLatin(password) {
      let ifLatin = /^[a-zA-z0-9_]+$/g;
      return ifLatin.test(password);
    },
    onRecoverPassword() {
      if (!this.isFormValid()) return null;
      console.log({
        newPassword: this.password,
      });
      ElMessage("修改成功!");
      this.$router.push("/signIn");
    },
  },
  destroyed() {
    this.$store.dispatch(actions.clearError);
  },
};
</script>
