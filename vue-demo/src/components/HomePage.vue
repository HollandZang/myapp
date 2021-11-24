<template>
  <div>
    <van-field v-model="toast" center clearable label="安卓提示" placeholder="请输入提示内容">
      <template #button>
        <van-button size="small" type="primary" @click="onToast(toast)">发送</van-button>
      </template>
    </van-field>
    <van-button type="info" @click="onCamera()">Camera拍照接口</van-button>
    <van-button type="info" @click="onCameraX()">CameraX拍照接口</van-button>
    <van-button type="info" @click="test()">测试按钮</van-button>
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
  },
  methods: {
    test() {
      this.axios({
        method:'get',
        url:'http://www.baidu.com/'
      }).then(function(res){
        console.log(res.data);
      })
    }
  }
}
</script>
