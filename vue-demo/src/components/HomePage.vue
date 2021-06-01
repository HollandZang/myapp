<template>
  <div>
    <!--    <div>-->
    <!--      前端项目打包成zip发送到http://119.23.68.6:8763/filesystem/upload/android/web 自动生成app页面-->
    <!--    </div>-->
    <!--    <div>入参file: 打包文件-->
    <!--    </div>-->
    <div>
      <van-button type="info" @click="onToast(toast)">安卓Toast方法</van-button>
      <input :id="toast"/>
    </div>
    <van-button type="info" @click="onCamera()">Camera拍照接口</van-button>
    <van-button type="info" @click="onCameraX()">CameraX拍照接口</van-button>
  </div>
</template>

<script>
import AppInterface from "../uitl/AppInterface";

window.javaCallJs = function (args) {
  this.onToast("收到参数: " + args)
}

export default {
  name: 'HomePage',
  mixins: [AppInterface],
  data() {
    return {
      toast: null,
    }
  },
}
</script>

<style>
.my-swipe .van-swipe-item {
  height: 150px;
  width: 100%;
}</style>
