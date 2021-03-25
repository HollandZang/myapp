import Vue from 'vue'
import Router from 'vue-router'
import HomePage from '../components/HomePage'
import PrivatePage from '../components/PrivatePage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: HomePage,
      meta: {
        show_tabbar: true
      }
    },
    {
      path: '/PrivatePage',
      name: 'PrivatePage',
      component: PrivatePage,
      meta: {
        show_tabbar: true
      }
    }
  ]
})
