<template>
  <header class="p-2 m-2 bg-primary text-white">

      <div class="row">
        
          <router-link to="/" class="col-1">
            <h1>Clobs</h1>
          </router-link>

          <div class="col-7">
            <h2>Clojure Bookmark Service</h2>
          </div>
        

        <div :key="loged" class="col-4">

          <div class="row">
            <div v-if="loged">
                <span class="col-6">Welcome, {{username}}</span>
                <span class="col-6" @click="logout">Logout</span>
            </div>

            <div v-else>
              
                <form @submit="login">
                  <label class="col-3">Please, login </label>

                  <input type="text" placeholder="username" v-model="login_username" name="login_username" class="col-3 m-1">
                  <input type="password" v-model="login_password" name="login_password" class="col-3 m-1">
                  
                  
                  <input type="submit" class="col-2 m-1">
                </form>
            </div>
          </div>
        </div>

      </div>

      <div class="nav row justify-content-start pl-5">
        <div class="col-1">
            <router-link to="/">Explore</router-link>
        </div>
        <div class="col-1">
            <router-link to="/user">My Bookmarks</router-link>
        </div>
        <div class="col-1 pl-5">
            <input type="text" placeholder="Search" >
        </div>
      </div>

  </header>
</template>

<script>

import Auth from "../services/Auth.js"

export default {
    data : function() {
      return {
        loged : false,
        username : null,
        login_username : null,
        login_password : null
      }
    },
    methods : {
      login() {
        Auth.login(this.login_username, this.login_password, (response) => {
          if(response.data.error == undefined) {
            this.loged = true
          }
        })
      },
      logout() {
        Auth.logout(() => {
          this.loged = false
        })
      }
    },
    mounted : function() {
      Auth.loged((response) => {
        if(response.data.message != undefined){
          this.username=response.data.message
          console.log(this.username)
          this.loged=true
        }

      })
    }

}
</script>