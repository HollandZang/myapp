<template>
  <div>
    <div>
      前端项目打包成zip发送到http://119.23.68.6:8763/filesystem/upload/android/web 自动生成app页面
    </div>
    <div>入参file: 打包文件
    </div>
    <van-button type="info" @click="onSearch()">安卓Toast方法</van-button>
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
      console.log('a')
      this.showToast('input your message to android')
    }
  }
}
</script>

<style>
.my-swipe .van-swipe-item {
  height: 150px;
  width: 100%;
}</style>
