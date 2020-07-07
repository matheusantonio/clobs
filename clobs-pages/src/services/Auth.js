import axios from 'axios'

const urlBase = "http://localhost:3000/clobs"

export default {
    login: (username, password, callback) => {
        const urlLogin = urlBase + "/auth/login"
        axios.post(urlLogin, {username:username, password:password}, {withCredentials: true}).then((response) => {
            callback(response)
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
        },
        (error) => {
            return Promise.reject(error)
        })
    }
}