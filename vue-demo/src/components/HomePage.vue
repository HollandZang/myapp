<template>
  <div>
    <!--    <div>-->
    <!--      前端项目打包成zip发送到http://119.23.68.6:8763/filesystem/upload/android/web 自动生成app页面-->
    <!--    </div>-->
    <!--    <div>入参file: 打包文件-->
    <!--    </div>-->
    <van-field v-model="toast" center clearable label="安卓提示" placeholder="请输入提示内容">
      <template #button>
        <van-button size="small" type="primary" @click="onToast(toast)">发送</van-button>
      </template>
    </van-field>
    <van-button type="info" @click="onCamera()">Camera拍照接口</van-button>
    <van-button type="info" @click="onCameraX()">CameraX拍照接口</van-button>
  </div>
</template>

<script>
import AppInterface from "../uitl/AppInterface";

export default {
  name: 'HomePage',
  mixins: [AppInterface],
  data() {
    return {
      toast: null,
    }
  }
}
</script>
