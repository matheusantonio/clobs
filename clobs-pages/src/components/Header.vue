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
                <p class="col-6" @click="logout">Logout</p>
            </div>
            
            <div v-else>
                <FormulateForm @submit="login">

                    <FormulateInput placeholder="username" name="username" class="col-3 m-1"/>

                    <FormulateInput type="password" name="password" class="col-3 m-1"/>

                    <FormulateInput type="submit" class="col-2 m-1"/>

                </FormulateForm>

                <span id="loginErrors" class="text-danger">{{loginErrors}}</span>
              
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
        loginErrors : ""
      }
    },
    methods : {
      login(data) {
        Auth.login(data.username, data.password, (response, error=false) => {
          if(!error) {
            this.loged = true
            this.loginErrors = ""
            Auth.loged((response) => {
              if(response.data != undefined){
                this.username = response.data.username
              }
            })
          } else {
            this.loginErrors = response.data.error
          }
        })
      },

      logout() {
        Auth.logout(() => {
          this.loged = false
          this.username = null
          this.$forceUpdate()
        })
      }
    },
    mounted : function() {
      this.loginErrors = ""
      Auth.loged((response, error=false) => {
        if(!error){
          this.username=response.data.username
          this.loged=true
        }
      })
    }

}
</script>