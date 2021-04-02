<template>
    <div id="app">
      <router-view/>

      <van-tabbar route id="tabbar" v-model="active" v-if="$route.meta.show_tabbar">
        <van-tabbar-item replace to="/" icon="home-o">主页</van-tabbar-item>
        <van-tabbar-item replace to="/PrivatePage" icon="manager-o">个人中心</van-tabbar-item>
      </van-tabbar>
    </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      active: 0
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /*text-align: center;*/
  color: #2c3e50;
  high: 100%;
  width: 100%;
}
</style>
