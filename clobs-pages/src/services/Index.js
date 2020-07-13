import axios from 'axios'

const urlBase = "http://localhost:3000/clobs"

export default {
    getTopBookmarks: (callback) => {
        const urlTopBookmarks = urlBase + "/top-bookmarks"
        axios.get(urlTopBookmarks).then((bookmarks) => {
            callback(bookmarks)
        })
    },
    getRecentBookmarks: (callback) => {
        const urlRecentBookmarks = urlBase + "/recent-bookmarks"
        axios.get(urlRecentBookmarks).then((bookmarks) => {
            callback(bookmarks)
        })
    }
}