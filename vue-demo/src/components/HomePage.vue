<template>
  <div>
    <van-search v-model="value" show-action label="地址" placeholder="请输入搜索关键词" @search="onSearch">
      <template #action>
        <div @click="onSearch">搜索</div>
      </template>
    </van-search>

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
