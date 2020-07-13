import axios from 'axios'

const urlBase = "http://localhost:3000/clobs"

export default {
    login: (username, password, callback) => {
        const urlLogin = urlBase + "/auth/login"
        axios.post(urlLogin, {username:username, password:password}, {withCredentials: true})
        .then((response) => {
            callback(response)
        }, (error) => {
            callback(error.response, true)
        })
    },
    logout: (callback) => {
        const urlLogout = urlBase + "/auth/logout"
        axios.get(urlLogout, {withCredentials : true}).then((response) => {
            callback(response)
        })
    },

    loged: (callback) => {
        const urlLoged = urlBase + "/auth/loged"
        axios.get(urlLoged, {withCredentials : true}).then((response) => {
            callback(response)
        }, (error) => {
            if(error.response.status == 401){
                callback(error.response, true)
            }
        })
    },

    register: (username, password, callback) => {
        const urlRegister = urlBase + "/user/register"
        axios.post(urlRegister, {username : username, password : password}, {withCredentials: true})
        .then((response) => {
            callback(response)
        }, (error) => {
            callback(error.response)
        })

    }
}