<template>
    <div>
        <div v-for="bookmark in bookmarks" :key="bookmark.id">
            <Bookmark :name="bookmark.definedname" :url="bookmark.url" :isPrivate="bookmark.private" user/>

        </div>

    </div>
</template>

<script>

import Bookmark from '../components/Bookmark'
import User from '../services/User'

export default {

    data : function() {
        return {
            bookmarks : []
        }
    },
    components : {Bookmark},
    mounted : function() {
        User.getBookmarks((response) => {
            if(response.status == 200){
                this.bookmarks = response.data
            } else {
                this.$router.push({ path : "/"})
            }
        })
    }

}
</script>