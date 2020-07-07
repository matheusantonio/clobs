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

import Login from "../services/Auth"

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
                document.getElementById('login').innerHTML = response.data
            })
        },
        logout() {
            Login.logout((response) => {
                document.getElementById('logout').innerHTML = response.data
            })
        },
        loged() {
            Login.loged((response) => {
                if(response.status == 401){
                    document.getElementById('loged').innerHTML = "Não autorizado!"
                }
                else {
                    document.getElementById('loged').innerHTML = response.data
                }
                
            })
        }
    }

}
</script>

<style>

</style>