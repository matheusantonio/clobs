<template>
    <div class="p-4 shadow border"> 
        <h2>Your Bookmarks</h2>
        <div class="list-group list-group-flush">
            <div v-for="bookmark in bookmarks" :key="bookmark.id" class="list-group-item">
                
                <div class="row justify-content-between">

                    <Bookmark class="col" :name="bookmark.definedname" :url="bookmark.url" :isPrivate="bookmark.private" user/>

                    <button class="col-1 btn btn-outline-info mr-2">Edit</button>
                    <button class="col-1 btn btn-outline-danger">Remove</button>
                    

                </div>
            </div>

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
                this.$router.push({ path : "/login"})
            }
        })
    }

}
</script>