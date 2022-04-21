<template>
  <div>
    <Form
      @submit="onSubmit"
      :validation-schema="schema"
      @invalid-submit="onInvalidSubmit"
    >
      <TextInput
        name="name"
        type="text"
        label="Full Name"
        placeholder="Your Name"
      />
      <TextInput
        name="email"
        type="email"
        label="E-mail"
        placeholder="Your email address"
      />
      <TextInput
        name="password"
        type="password"
        label="Password"
        placeholder="Your password"
      />
      <TextInput
        name="confirm_password"
        type="password"
        label="Confirm Password"
        placeholder="Type it again"
      />

      <button class="submit-btn" type="submit">Submit</button>
    </Form>
  </div>
</template>

<script>
import { Form } from "vee-validate";
import * as Yup from "yup";
import TextInput from "../../components/TextInput.vue";

export default {
  name: "SignInPage",
  components: {
    TextInput,
    Form,
  },
  setup() {
    function onSubmit(values) {
      alert(JSON.stringify(values, null, 2));
    }

    function onInvalidSubmit() {
      const submitBtn = document.querySelector(".submit-btn");
      submitBtn.classList.add("invalid");
      setTimeout(() => {
        submitBtn.classList.remove("invalid");
      }, 1000);
    }

    // Using yup to generate a validation schema
    // https://vee-validate.logaretm.com/v4/guide/validation#validation-schemas-with-yup
    const schema = Yup.object().shape({
      name: Yup.string().required("请输入您的用户名"),
      email: Yup.string().email("邮箱格式错误").required("请输入您的邮箱"),
      password: Yup.string().min(6, "密码长度至少为六位").required("请输入您的密码"),
      confirm_password: Yup.string()
        .required("请再次输入密码")
        .oneOf([Yup.ref("password")], "密码一致"),
    });

    return {
      onSubmit,
      schema,
      onInvalidSubmit,
    };
  },
};
</script>

<style>
* {
  box-sizing: border-box;
}

:root {
  --primary-color: #0071fe;
  --error-color: #f23648;
  --error-bg-color: #fddfe2;
  --success-color: #21a67a;
  --success-bg-color: #e0eee4;
}

html,
body {
  width: 100%;
  height: 100%;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;

  width: 100%;
  height: 100%;
}

form {
  width: 300px;
  margin: 0px auto;
  padding-bottom: 60px;
}

.submit-btn {
  background: var(--primary-color);
  outline: none;
  border: none;
  color: #fff;
  font-size: 18px;
  padding: 10px 15px;
  display: block;
  width: 100%;
  border-radius: 7px;
  margin-top: 40px;
  transition: transform 0.3s ease-in-out;
  cursor: pointer;
}

.submit-btn.invalid {
  animation: shake 0.5s;
  /* When the animation is finished, start again */
  animation-iteration-count: infinite;
}

@keyframes shake {
  0% {
    transform: translate(1px, 1px);
  }
  10% {
    transform: translate(-1px, -2px);
  }
  20% {
    transform: translate(-3px, 0px);
  }
  30% {
    transform: translate(3px, 2px);
  }
  40% {
    transform: translate(1px, -1px);
  }
  50% {
    transform: translate(-1px, 2px);
  }
  60% {
    transform: translate(-3px, 1px);
  }
  70% {
    transform: translate(3px, 1px);
  }
  80% {
    transform: translate(-1px, -1px);
  }
  90% {
    transform: translate(1px, 2px);
  }
  100% {
    transform: translate(1px, -2px);
  }
}

.submit-btn:hover {
  transform: scale(1.1);
}
</style>
