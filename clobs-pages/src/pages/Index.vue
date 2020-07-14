<template>
    <div class="shadow border">
        <div class="m-4">
            <div><h2>Top Bookmarks</h2></div>
            <div class="list-group list-group-flush">
                <div v-for="bookmark in topBookmarks" :key="'top' + bookmark.id" class="list-group-item d-flex justify-content-between align-items-center">
                    <Bookmark :name="bookmark.name" :url="bookmark.url" :quantity="bookmark.qtd" top/>


                </div>
            </div>

        </div>

        <hr>

        <div class="m-4">
            <div><h2>Recent Bookmarks</h2></div>
            <div class="list-group list-group-flush">
                <div v-for="bookmark in recentBookmarks" :key="bookmark.id + ':' + bookmark.createdat" class="list-group-item d-flex justify-content-between align-items-center">
                    <Bookmark :name="bookmark.name" :url="bookmark.url" :createdAt="bookmark.createdat" recent/>
                </div>
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

        Index.getTopBookmarks((bookmarks) => {
            this.topBookmarks = bookmarks.data
        })

        Index.getRecentBookmarks((bookmarks) => {
            this.recentBookmarks = bookmarks.data
        })
        
    }
}
</script>

<style>

</style>