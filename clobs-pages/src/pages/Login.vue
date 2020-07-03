<template>
    <div>
        <div>
            <button @click="login">Login with matheus-user and mypass</button>
            <p id="login"></p>

        </div>
        <div>
            <button @click="logout">Logout :(</button>
            <p id="logout"></p>
            
        </div>
        <div>
            <button @click="loged">Am I loged?</button>
            <p id="loged"></p>
            
        </div>
    </div>
</template>

<script>

import Login from "../services/Login"

export default {

    data : function() {
        return {
            loginMessage : null,
            logoutMessage : null,
            logedMessage : null
        }
    },
    methods :{
        login() {
            Login.login("matheus-user", "mypass", (response) => {
                document.getElementById('login').innerHTML = response.data.message
            })
        },
        logout() {
            Login.logout((response) => {
                //console.log(response)
                document.getElementById('logout').innerHTML = response.data
            })
        },
        loged() {
            Login.loged((response) => {
                if(response.data.message == undefined){
                    document.getElementById('loged').innerHTML = response.data.error
                }
                else {
                    document.getElementById('loged').innerHTML = response.data.message
                }
                
            })
        }
    }

}
</script>

<style>

</style>