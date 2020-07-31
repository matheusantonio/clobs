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
        const url = urlBase + "/" + id
        console.log(url)
        axios.get(url, {withCredentials : true}).then((response) => {
            callback(response)
        })
    },
    createBookmark : (url, name, isPrivate, tags, callback) => {
        axios.post(urlBase, {
            url : url,
            name : name,
            private : isPrivate,
            tags : tags
        }, {withCredentials : true})
        .then((response) => {
            callback(response)
        }, (error) => {
            callback(error.response)
        })
    },
    removeBookmark : (id, callback) => {
        const url = urlBase + "/" + id
        axios.delete(url, {withCredentials : true}).then((response) => {
            callback(response)
        })
    },
    editBookmark : (id, name, isPrivate, tags, callback) => {
        axios.put(urlBase, {
            id : id,
            name : name,
            private : isPrivate,
            tags : tags
        },
        {withCredentials : true})
        .then((response) => {
            callback(response)
        }, (error) => {
            callback(error.response)
        })
    }
}