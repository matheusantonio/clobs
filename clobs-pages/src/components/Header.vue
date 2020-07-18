<template>
  <header class="p-2 m-2">

      <div class="row">
        
          <router-link to="/" tag="button" class="btn btn-outline-info btn-lg">
            Clobs
          </router-link>

          <div class="col-5">
            <h2>Clojure Bookmark Service</h2>
          </div>
        

        <div  class="col-6">

          <div>
            <div v-if="this.$store.state.loged">
                <span class="m-4">Welcome, {{username}}</span>
                <p class="btn btn-outline-info" @click="logout">Logout</p>
            </div>
            
            <div v-else>
              
              <router-link to="/login" tag="button" class="ml-2 mr-2 btn btn-outline-info">
                    Login
              </router-link>

              or 

              <router-link to="/register" tag="button" class="ml-2 mr-2 btn btn-outline-info">
                    Register
              </router-link>
              
            </div>
          </div>
        </div>

      </div>

      <ul class="nav nav-tabs mt-2">
        <li class="nav-item">
            <router-link class="nav-link" to="/">Explore</router-link>
        </li>
        <li class="nav-item">
            <router-link class="nav-link" to="/user">My Bookmarks</router-link>
        </li>
        <li class="nav-item">
            <form class="nav-link form-inline" @submit.prevent="search">
                <input placeholder="Search" name="search" class="form-control" />
                <input type="submit" class="btn btn-primary" value="Search"/>
            </form>
        </li>
      </ul>

  </header>
</template>

<script>

import Auth from "../services/Auth.js"

export default {
    data : function() {
      return {
        username : null
      }
    },
    methods : {
      logout() {
        Auth.logout(() => {
          this.$store.commit('logout')
          this.username = null
          this.$router.push({ path : "/"})
        })
      },
      search(data) {
        console.log("search: " + data.search)
      }
    },
    mounted : function() {
      Auth.loged((response) => {
        if(response.status == 200){
          this.username=response.data.username
          this.$store.commit('login')
        }
      })
    }

}
</script>