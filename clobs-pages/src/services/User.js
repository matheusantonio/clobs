import axios from 'axios'

const urlBase = "http://localhost:3000/clobs/bookmarks"

export default {
    getBookmarks : (callback) => {
        axios.get(urlBase, {withCredentials : true}).then((response) => {
            callback(response)
        }, (error) => {
            callback(error.response)
        })
    },
    getBookmark : (id, callback) => {
        const url = urlBase + id
        axios.get(url, {withCredential : true}).then((response) => {
            callback(response)
        })
    }
}