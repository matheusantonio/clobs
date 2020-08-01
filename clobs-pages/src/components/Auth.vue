<template>
        <div class="m-4">

            <form @submit.prevent="submitFunction" method="post">

                <h2 class="m-2">
                    <svg v-if="register" width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-plus" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M11 14s1 0 1-1-1-4-6-4-6 3-6 4 1 1 1 1h10zm-9.995-.944v-.002.002zM1.022 13h9.956a.274.274 0 0 0 .014-.002l.008-.002c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664a1.05 1.05 0 0 0 .022.004zm9.974.056v-.002.002zM6 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zm4.5 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
                        <path fill-rule="evenodd" d="M13 7.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0v-2z"/>
                    </svg>
                    <svg v-else width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M13 14s1 0 1-1-1-4-6-4-6 3-6 4 1 1 1 1h10zm-9.995-.944v-.002.002zM3.022 13h9.956a.274.274 0 0 0 .014-.002l.008-.002c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664a1.05 1.05 0 0 0 .022.004zm9.974.056v-.002.002zM8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    </svg>
                    
                    {{label}}</h2>

                <div 
                    v-if="errors != null"
                    id="errors"
                    class="alert alert-danger m-2">
                    {{errors}}
                </div>
                <div
                    v-if="messages != null"
                    id="message"
                    class="alert alert-info alert-dismissible m-2">
                    {{messages}}
                </div>

                <div class="bg-white p-3 container">
                    
                    <div class="form-group">
                        <label>Username</label>
                        <input name="username" class="form-control" v-model="username"/>
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input name="password" type="password" class="form-control" v-model="password" />
                    </div>

                    <button type="submit" class="btn btn-info">Send</button>
                </div>

            </form>

        </div> 
</template>

<script>

import Auth from "../services/Auth"

export default {

    props :{
        register : { type : Boolean}

    },
    data : function() {
        return {
            errors : null,
            messages : null,
            label : null,
            username : null,
            password : null,
            submitFunction : null
        }
    },
    methods :{
        loginF() {
            Auth.login(this.username, this.password, (response) => {
            if(response.status == 200) {
                this.errors = null
                this.messages = null
                this.$store.commit('login')
                this.$router.push({path : "/user"})
            } else {
                this.password=null
                this.errors = response.data.error
            }
            })
        },
        registerF() {
            Auth.register(this.username, this.password, (response) => {
                if(response.status == 201){
                    this.errors = null
                    this.username = null
                    this.password = null
                    this.messages = response.data.message
                } else {
                    this.errors = response.data.error
                }
            })

        }
    },
    created : function() {
        this.username=null
        this.password=null
        if(!this.register){
            this.submitFunction = this.loginF
            this.label = "Login"
        } else {
            this.submitFunction = this.registerF
            this.label = "Register"
        }
    }
}
</script>

<style>

</style>