<template>
  <div>
    <van-search v-model="value" show-action label="地址" placeholder="请输入搜索关键词" @search="onSearch">
      <template #action>
        <div @click="onSearch">搜索</div>
      </template>
    </van-search>

前端项目打包成zip发送到http://119.23.68.6:8763/filesystem/upload/android/web
自动生成app页面
入参file: 打包文件
  </div>
</template>

<script>
import AppInterface from "../uitl/AppInterface";

export default {
  name: 'HomePage',
  mixins: [AppInterface],
  data() {
    return {
      value: null,
    }
  },
  methods: {
    onSearch() {
    }
  }
}
</script>

<style>
.my-swipe .van-swipe-item {
  height: 150px;
  width: 100%;
}</style>
