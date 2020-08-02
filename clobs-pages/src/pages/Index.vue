<template>
    <div class="shadow border">
        <div class="m-4">
            <div class="m-2"><h2>
                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-bookmark-check" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" d="M4.5 2a.5.5 0 0 0-.5.5v11.066l4-2.667 4 2.667V8.5a.5.5 0 0 1 1 0v6.934l-5-3.333-5 3.333V2.5A1.5 1.5 0 0 1 4.5 1h4a.5.5 0 0 1 0 1h-4z"/>
                    <path fill-rule="evenodd" d="M15.854 2.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 4.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                </svg>
                Top Bookmarks</h2></div>
            <div class="list-group list-group-flush">
                <div v-for="bookmark in topBookmarks" :key="'top' + bookmark.id" class="list-group-item d-flex justify-content-between align-items-center">
                    <Bookmark :name="bookmark.name" :url="bookmark.url" :quantity="bookmark.qtd" :tags="bookmark.tags" top/>


                </div>
            </div>

        </div>

        <hr>

        <div class="m-4">
            <div class="m-2"><h2>
                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-clock-history" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" d="M8.515 1.019A7 7 0 0 0 8 1V0a8 8 0 0 1 .589.022l-.074.997zm2.004.45a7.003 7.003 0 0 0-.985-.299l.219-.976c.383.086.76.2 1.126.342l-.36.933zm1.37.71a7.01 7.01 0 0 0-.439-.27l.493-.87a8.025 8.025 0 0 1 .979.654l-.615.789a6.996 6.996 0 0 0-.418-.302zm1.834 1.79a6.99 6.99 0 0 0-.653-.796l.724-.69c.27.285.52.59.747.91l-.818.576zm.744 1.352a7.08 7.08 0 0 0-.214-.468l.893-.45a7.976 7.976 0 0 1 .45 1.088l-.95.313a7.023 7.023 0 0 0-.179-.483zm.53 2.507a6.991 6.991 0 0 0-.1-1.025l.985-.17c.067.386.106.778.116 1.17l-1 .025zm-.131 1.538c.033-.17.06-.339.081-.51l.993.123a7.957 7.957 0 0 1-.23 1.155l-.964-.267c.046-.165.086-.332.12-.501zm-.952 2.379c.184-.29.346-.594.486-.908l.914.405c-.16.36-.345.706-.555 1.038l-.845-.535zm-.964 1.205c.122-.122.239-.248.35-.378l.758.653a8.073 8.073 0 0 1-.401.432l-.707-.707z"/>
                    <path fill-rule="evenodd" d="M8 1a7 7 0 1 0 4.95 11.95l.707.707A8.001 8.001 0 1 1 8 0v1z"/>
                    <path fill-rule="evenodd" d="M7.5 3a.5.5 0 0 1 .5.5v5.21l3.248 1.856a.5.5 0 0 1-.496.868l-3.5-2A.5.5 0 0 1 7 9V3.5a.5.5 0 0 1 .5-.5z"/>
                </svg>
                Recent Bookmarks</h2></div>
            <div class="list-group list-group-flush">
                <div v-for="bookmark in recentBookmarks" :key="bookmark.id + ':' + bookmark.createdat" class="list-group-item d-flex justify-content-between align-items-center">
                    <Bookmark :name="bookmark.name" :url="bookmark.url" :createdAt="bookmark.createdat" :tags="bookmark.tags" recent/>
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
            recentBookmarks : [],
            timer : ''
        }
    },
    components : { Bookmark },
    methods : {
        updateIndex() {
            Index.getTopBookmarks((bookmarks) => {
                this.topBookmarks = bookmarks.data
            })
            Index.getRecentBookmarks((bookmarks) => {
                this.recentBookmarks = bookmarks.data
            })
        }
    },
    mounted : function() {
        this.updateIndex()

        this.timer = setInterval(function() {
            this.updateIndex()
        }.bind(this), 1000)
    },
    beforeDestroy : function() {
        clearInterval(this.timer)
    }
}
</script>

<style>

</style>