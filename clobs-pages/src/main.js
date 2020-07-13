import Vue from 'vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'

import App from './App'
import Index from './pages/Index'
import User from './pages/User'
import Login from './pages/Login'
import Register from './pages/Register'
import Error from './pages/Error'

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

Vue.use(VueRouter)
Vue.use(Vuex)

const store = new Vuex.Store({
    state : {
        loged : false
    },
    mutations : {
        login(state) {
            state.loged = true
        },
        logout(state) {
            state.loged = false
        }
    }
})

var router = new VueRouter({
    routes: [
        {
            path: '/',
            component : Index
        },
        {
            path: '/user',
            component : User
        },
        {
            path: '/login',
            component : Login,
        },
        {
            path : '/register',
            component: Register 
        },
        {
            path: '/error',
            component : Error
        }
    ]
})

new Vue({
    store,
    router,
    render: h => h(App)
}).$mount("#app")