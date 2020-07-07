import axios from 'axios'

const urlBase = "http://localhost:3000/clobs/bookmarks"

export default {
    getBookmarks : (callback) => {
        axios.get(urlBase, {withCredentials : true}).then((response) => {
            callback(response)
        })
    }
}