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
