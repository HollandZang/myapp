export default {
  methods: {
    showToast(message) {
      if (window.$App) {
        $App.showToast(message)
      }
    }
  }
}
