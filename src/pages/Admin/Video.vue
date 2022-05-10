<template>
  <div class="page__content">
    <h1 class="page__title">视频管理</h1>
    <br />
    <div class="breadcrumbs">
      <h4>无花果后台 / 视频管理</h4>

      <el-button type="primary" @click="centerDialogVisible = true"
        >添加视频</el-button
      >

      <el-dialog
        v-model="centerDialogVisible"
        title="添加视频"
        destroy-on-close
        center
      >
        <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          label-width="120px"
          class="demo-ruleForm"
          :size="formSize"
        >
          <el-form-item label="电影名" prop="title">
            <el-input v-model="ruleForm.title" />
          </el-form-item>
          <el-form-item label="概述" prop="overview">
            <el-input type="textarea" :rows="3" v-model="ruleForm.overview" />
          </el-form-item>
          <el-form-item label="演员" prop="actors">
            <el-input
              placeholder="请输入电影演员用英文逗号间隔"
              v-model="ruleForm.actors"
            />
          </el-form-item>
          <el-form-item>
            <el-upload
              v-model="ruleForm.movieAddr"
              class="upload-movie"
              prop="movieAddr"
              action="http://m39973w600.zicp.vip/movie/upload"
              :on-success="handleMovieSuccess"
            >
              <el-button type="primary" plain>上传视频</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-upload
              v-on="ruleForm.posterPath"
              class="upload-poster"
              prop="posterPath"
              action="http://m39973w600.zicp.vip/movie/upload"
              :on-success="handlePosterSuccess"
            >
              <el-button type="primary" plain>添加封面</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="风格" prop="type">
            <el-select v-model="ruleForm.type" placeholder="选择电影风格">
              <el-option label="动画" value="1" />
              <el-option label="戏剧" value="2" />
              <el-option label="纪录片" value="3" />
              <el-option label="科幻" value="4" />
              <el-option label="新闻" value="5" />
              <el-option label="犯罪片" value="6" />
              <el-option label="喜剧" value="7" />
              <el-option label="恐怖" value="8" />
              <el-option label="浪漫" value="9" />
              <el-option label="惊悚" value="10" />
              <el-option label="魔幻" value="11" />
            </el-select>
          </el-form-item>
          <el-form-item label="类型" prop="post">
            <el-select v-model="ruleForm.post" placeholder="选择电影类型">
              <el-option label="电影" value="0" />
              <el-option label="电视剧" value="1" />
            </el-select>
          </el-form-item>
          <el-form-item label="是否原创" prop="original">
            <el-switch v-model="ruleForm.original" size="mini" />
          </el-form-item>
          <el-form-item label="评分" prop="voteAverage">
            <el-input v-model="ruleForm.voteAverage" />
          </el-form-item>
          <el-form-item label="上映时间" prop="releaseDate">
            <el-date-picker
              v-model="ruleForm.releaseDate"
              type="date"
              placeholder="选择上映时间"
              :disabled-date="disabledDate"
              :shortcuts="shortcuts"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)"
              >创建</el-button
            >
            <el-button @click="resetForm(ruleFormRef)">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <el-divider />
    <main>
      <table class="container">
        <thead>
          <tr>
            <th><h2>ID</h2></th>
            <th><h2>视频名</h2></th>
            <th><h2>描述</h2></th>
            <th><h2>上映时间</h2></th>
            <th><h2>风格</h2></th>
            <th><h2>类型</h2></th>
            <th><h2>评分</h2></th>
            <th><h2>操作</h2></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="movie in movies" v-bind:key="movie.id">
            <td>{{ movie.id }}</td>
            <td>{{ movie.title }}</td>
            <td>{{ movie.overview }}</td>
            <td>{{ movie.releaseDate }}</td>
            <td>{{ movie.type }}</td>
            <td>{{ movie.post }}</td>
            <td>{{ movie.voteAverage }}</td>
            <td>
              <el-button
                plain
                type="danger"
                size="mini"
                auto-insert-space
                :loading="deleteLoading && actionMovieId === movie.id"
                @click="deleteVideo(movie.id)"
                >删除</el-button
              >
            </td>
          </tr>
        </tbody>
      </table>
      <h2 class="empty-msg" v-if="movies.length === 0">暂无数据</h2>
    </main>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onUnmounted, reactive, ref, watch } from "vue";
import { User, VideoPlay } from "@element-plus/icons-vue";
import { useStore } from "vuex";

const store = useStore();
const users = ref([]);
const centerDialogVisible = ref(false);
const movies = ref([]);
const actionMovieId = ref(null);
const deleteLoading = ref(false);

import { ElMessage, FormInstance, FormRules } from "element-plus";
import instance from "../../request";

const formSize = ref("default");
const ruleFormRef = ref<FormInstance>();
const ruleForm = reactive({
  overview: "",
  original: false,
  movieAddr: "",
  posterPath: "",
  releaseDate: "",
  title: "",
  type: "",
  voteAverage: "",
  post: "",
  actors: "",
});

const rules = reactive<FormRules>({
  title: [],
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      const res = await instance.post("/movie/add-movie", {
        ...ruleForm,
        releaseDate: new Date(ruleForm.releaseDate).getTime(),
      });
      if (res.data.code === 200) {
        centerDialogVisible.value = false;
        ruleForm["overview"] = "";
        ruleForm["original"] = false;
        ruleForm["movieAddr"] = "";
        ruleForm["posterPath"] = "";
        ruleForm["releaseDate"] = "";
        ruleForm["title"] = "";
        ruleForm["type"] = "";
        ruleForm["voteAverage"] = "";
        ruleForm["post"] = "";
        ruleForm["actors"] = "";
        store.dispatch("getMovies");
      }
    }
  });
};

const handlePosterSuccess = (res: any) => {
  ruleForm.movieAddr = res.data;
};

const handleMovieSuccess = (res: any) => {
  ruleForm.posterPath = res.data;
};

const deleteVideo = async (id) => {
  actionMovieId.value = id;
  deleteLoading.value = true;
  const res = await instance.post("/movie/delete-movie", { id });

  if (res.data.code === 200) {
    await store.dispatch("getMovies");
    actionMovieId.value = null;
    deleteLoading.value = false;
  }
  actionMovieId.value = null;
  deleteLoading.value = false;
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

onMounted(() => {
  store.dispatch("getMovies");
});

watch(
  () => store.getters.movies,
  (newVal) => {
    movies.value = newVal;
  }
);

onUnmounted(() => {
  store.dispatch("clearMovies");
});
</script>

<style scoped>
.container {
  text-align: left;
  overflow: hidden;
  width: 80%;
  margin: 0 auto;
  display: table;
  padding: 0 0 8em 0;
}

.empty-msg {
  text-align: center;
}

.breadcrumbs {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1em;
}
</style>
