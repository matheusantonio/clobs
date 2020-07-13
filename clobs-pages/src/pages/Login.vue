<template>
    
    <div class="shadow border">

        {{register}}

        <div class="m-4">

            <form @submit.prevent="submitFunction" method="post">

                <h2>{{label}}</h2>

                <div 
                    v-if="errors != null"
                    id="errors"
                    class="alert alert-danger m-2">
                    {{errors}}
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

    </div>
</template>

<script>

import Auth from "../services/Auth"

export default {

    props : {
        register : {type : Boolean}
    },
    data : function() {
        return {
            errors : null,
            label : null,
            username : null,
            password : null,
            submitFunction : null
        }
    },
    methods :{
        loginF() {
            Auth.login(this.username, this.password, (response, error=false) => {
            if(!error) {
                this.errors = ""
                this.$store.commit('login')
                this.$router.push({path : "/user"})
            } else {
                this.password=null
                this.errors = response.data.error
            }
            })
        },
        registerF(data) {
            Auth.register(data.username, data.password, (response) => {
                if(response.status == 201){
                    this.errors = ""
                    this.$router.push({ path : "/"})
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