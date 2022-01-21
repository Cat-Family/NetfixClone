<template>
  <div class="tile bg">
    <div class="tile__container">
      <h1 class="tile__title">找回密码</h1>
      <form @submit.prevent="onRecoverPassword">
        <div class="input__wrapper">
          <input
            id="email"
            name="email"
            type="email"
            required
            placeholder="Email"
            v-model="email"
            :class="[{ 'input--filled': email }, 'input']"
          />
          <label class="input__placeholder" for="email"> 邮箱 </label>
        </div>
        <div class="form__btns">
          <button type="submit" class="btn btn--primary" :disabled="loading">
            发送
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
import { ref } from "vue";
import Spinner from "../../components/Spinner/Spinner.vue";
import { actions } from "../../helpers/constants";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import instance from "../../request";

export default {
  name: "RecoverPassword",
  setup() {
    const email = ref("");
    const router = useRouter();
    const onRecoverPassword = () => {
      console.log(email.value);
      // ElMessage.success("发送成功!");
      // router.push("/RecoverPasswordSuccess");
      instance
        .post(`/user/sendEmailForFindPassword?email=${email.value}`)
        .then((res) => {})
        .catch((err) => {});
    };
    return {
      onRecoverPassword,
      Spinner,
      email,
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
  destroyed() {
    this.$store.dispatch(actions.clearError);
  },
};
</script>
