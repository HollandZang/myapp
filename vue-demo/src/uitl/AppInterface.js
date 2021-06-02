export default {
  methods: {
    onToast(message) {
      if (window.$App) $App.onToast(message)
    },
    onCamera() {
      if (window.$App) $App.onCamera()
    },
    onCameraX() {
      if (window.$App) $App.onCameraX()
    }
  }
}

window.appCallJs = function (type, args0, args1, args2, args3, args4, args5, args6, args7, args8, args9) {
  switch (type) {
    case '1':
      /**
       * 拍照完成，摄像机回传本地图片路径
       * @param arg0 本地图片路径
       */
      break;
    default:
      if (window.$App) window.$App.onToast(`收到参数: ${type}, ${args0}, ${args1}, ${args2}, ${args3}, ${args4}, ${args5}, ${args6}, ${args7}, ${args8}, ${args9}`)
  }
}
