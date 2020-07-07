import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App'
import Index from './pages/Index'
import User from './pages/User'
import Login from './pages/Login'
import Error from './pages/Error'

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

Vue.use(VueRouter)

var router = new VueRouter({
    routes: [
        { path: '/', component : Index},
        { path: '/user', component : User},
        { path: '/login', component : Login},
        { path: '/error', component : Error}
    ]
})

new Vue({
    router,
    render: h => h(App)
}).$mount("#app")