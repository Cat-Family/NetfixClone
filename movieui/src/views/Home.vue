<template>
  <section class="todoapp">
    <!-- header -->
    <header class="header">
      <h1>todos</h1>
      <n-input
        class="new-todo"
        autofocus
        autocomplete="off"
        placeholder="What needs to be done?"
        @keyup.enter="addTodo"
      />
    </header>
    <!-- main section -->
    <section class="main" v-show="todos.length">
      <n-checkbox
        class="toggle-all"
        id="toggle-all"
        type="checkbox"
        :checked="allChecked"
        @change="toggleAll(!allChecked)"
      />
      <label style="margin: 1rem" for="toggle-all">全选</label>
      <ul class="todo-list">
        <TodoItem
          v-for="(todo, index) in filteredTodos"
          :key="index"
          :todo="todo"
        />
      </ul>
    </section>
    <!-- footer -->
    <footer class="footer" v-show="todos.length">
      <span class="todo-count">
        <strong>{{ remaining }}</strong>
        {{ pluralize(remaining, "item") }} left
      </span>
      <n-list class="filters">
        <n-list-item v-for="(val, key) in filters">
          <a
            :href="'#/' + key"
            :class="{ selected: visibility === key }"
            @click="visibility = key"
            >{{ capitalize(key) }}</a
          >
        </n-list-item>
      </n-list>
      <n-button
        class="clear-completed"
        v-show="todos.length > remaining"
        @click="clearCompleted"
      >
        Clear completed
      </n-button>
    </footer>
  </section>
</template>

<script>
import { mapActions } from "vuex";
import TodoItem from "../components/TodoItem.vue";
import Login from "./Login.vue";
const filters = {
  all: (todos) => todos,
  active: (todos) => todos.filter((todo) => !todo.done),
  completed: (todos) => todos.filter((todo) => todo.done),
};
export default {
  components: { TodoItem, Login },
  data() {
    return {
      visibility: "all",
      filters: filters,
    };
  },
  computed: {
    todos() {
      return this.$store.state.todos;
    },
    allChecked() {
      return this.todos.every((todo) => todo.done);
    },
    filteredTodos() {
      return filters[this.visibility](this.todos);
    },
    remaining() {
      return this.todos.filter((todo) => !todo.done).length;
    },
  },
  methods: {
    ...mapActions(["toggleAll", "clearCompleted"]),
    addTodo(e) {
      const text = e.target.value;
      if (text.trim()) {
        this.$store.dispatch("addTodo", text);
      }
      e.target.value = "";
    },
    pluralize(n, w) {
      return n === 1 ? w : w + "s";
    },
    capitalize(s) {
      return s.charAt(0).toUpperCase() + s.slice(1);
    },
  },
};
</script>