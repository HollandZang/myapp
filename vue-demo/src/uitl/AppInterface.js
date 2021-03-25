export default {
  methods: {
    showToast(message) {
      if (window.$App) {
        $App.showToast("test android")
      }
    }
  }
}
