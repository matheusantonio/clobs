<template>
    <div :key="bookmarks">
    <div class="p-4 shadow border"> 
        <h2>Your Bookmarks</h2>
        <div class="list-group list-group-flush">
            <div v-for="bookmark in bookmarks" :key="bookmark.id" class="list-group-item">
                
                <div class="row justify-content-between">

                    <Bookmark class="col" :name="bookmark.definedname" :url="bookmark.url" :isPrivate="bookmark.private" user/>

                    <button 
                        class="col-1 btn btn-outline-info mr-2"
                        type="button"
                        data-toggle="collapse"
                        v-bind:data-target="'#edit-' + bookmark.id"
                        aria-expanded="false"
                        v-bind:aria-controls="'edit-'+bookmark.id">
                            Edit
                    </button>
                    
                    <button 
                        class="col-1 btn btn-outline-danger"
                        @click="remove(bookmark.id, bookmark.definedname)">
                        Remove
                    </button>
                    

                </div>

                <div class="collapse container" :id="'edit-'+bookmark.id">
                    <form class="row align-items-center mr-5" @submit.prevent="edit">
                        <input name="id" type="hidden" :value="bookmark.id" />
                        <div class="col-4 form-group">
                            <label>User defined name</label>
                            <input name="definedname" placeholder="Name" class="form-control" :value="bookmark.definedname"/>
                        </div>

                        <div class="col-2 form-group">
                            <label class="form-check-label">Is private?</label>
                            <input v-if="bookmark.private" name="private" type="checkbox" class="form-control" checked/>
                            <input v-else type="checkbox" name="private" class="form-control"/>
                            
                        </div>

                        <div class="col-2">
                            <input type="submit" class="btn btn-outline-primary">
                        </div>
                        
                    </form>
                    <div 
                        :id="'errors' + bookmark.id"
                        class="collapse alert alert-danger">
                    </div>
                   

                </div>

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
    methods: {
        listBookmarks() {
            User.getBookmarks((response) => {
            if(response.status == 200){
                this.bookmarks = response.data
            } else {
                this.$router.push({ path : "/login"})
            }
        })
        },
        remove(id, name) {
            if(confirm("Remover " + name + "?")){
                User.removeBookmark(id, (response) => {
                    if(response.status < 300){
                        this.listBookmarks()
                    }
                })
            }
        },
        edit(submitElement) {
            const data = submitElement.target.elements

            const bookmarkId = data["id"].value

            User.editBookmark(
                bookmarkId,
                data["definedname"].value,
                data["private"].checked,
                (response) => {
                    if(response.status == 200) {
                        this.listBookmarks()
                    } else {
                        const errorId = "errors" + bookmarkId
                        const errorElement = document.getElementById(errorId)
                        errorElement.innerHTML = response.error
                        errorElement.collapse('show')
                    }
                }
            )
        }
    },
    components : {Bookmark},
    mounted : function() {
        this.listBookmarks()
        
    }

}
</script>