<template>
    <div>
        <div>
            <div v-for="bookmark in topBookmarks" :key="bookmark.id">
                <Bookmark :name="bookmark.name" :url="bookmark.url" :quantity="bookmark.qtd" top/>

            </div>
        </div>

        <hr>

        <div>
            <div v-for="bookmark in recentBookmarks" :key="bookmark.id">
                <Bookmark :name="bookmark.name" :url="bookmark.url" :createdAt="bookmark.createdat" recent/>
            </div>
        </div>

    
    </div>
    
</template>

<script>

import Bookmark from '../components/Bookmark'
import Index from '../services/Index'

export default {

    data : function() {
        return {
            topBookmarks : [],
            recentBookmarks : []
        }
    },
    components : { Bookmark },
    mounted : function() {
        Index.getTopBookmarks(bookmarks => {
            this.topBookmarks = bookmarks.data
        })
        Index.getRecentBookmarks(bookmarks => {
            this.recentBookmarks = bookmarks.data
        })
    }
}
</script>

<style>

</style>