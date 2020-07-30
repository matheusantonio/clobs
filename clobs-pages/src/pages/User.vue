<template>
    
    <div :key="bookmarks">
    <div class="p-2 shadow border">

        <div class="m-4">
            <div class="row justify-content-between mb-2">
                <h2 class="ml-3">Your Bookmarks</h2>
                <button 
                    class="btn btn-outline-success mr-3 "
                            data-toggle="collapse"
                            data-target="#newBookmark"
                            aria-expanded="false"
                            aria-controls="newBookmark">New</button>
            </div>

            <div class="collapse container" id="newBookmark">
                <div class="border-bottom border-right my-3 p-2">
                    <form @submit.prevent="create">
                        <div class="form-group">
                            <label>URL</label>
                            <input name="url" class="form-control">
                        </div>

                        <div class="form-row align-items-center justify-content-between">

                            <div class="form-group col-md-6">
                                <label>Name</label>
                                <input name="name" class="form-control">
                            </div>

                            <div class="form-group col-md-5">
                                <label>Is Private?</label>
                                <div class="form-check">
                                    <input name="private" type="checkbox" class="form-check-input" id="isPrivate">
                                    <label class="form-check-label" for="isPrivate">Mark if bookmark is private</label>
                                </div>
                                
                            </div>

                        </div>

                        <div class="form-group">
                            <div>
                                <label>Tags</label>
                                <tags-input element-id="newTags"></tags-input>
                            </div>
                        </div>

                        <input type="submit" class="btn btn-outline-info" value="Create">

                    </form>
                    <div 
                        id="errorNew"
                        class="collapse alert alert-danger">
                    </div>
                </div>

            </div>
            
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
                        <div class="border-right border-bottom mt-3 p-2">
                            <form class="mr-5" @submit.prevent="edit">
                                <div class="form-row">
                                    <input name="id" type="hidden" :value="bookmark.id" />
                                    <div class="col-8 form-group">
                                        <label>User defined name</label>
                                        <input name="definedname" placeholder="Name" class="form-control" :value="bookmark.definedname"/>
                                    </div>

                                    <div class="col-4 form-group">
                                        <label>Is Private?</label>
                                        <div class="form-check">
                                            <input v-if="bookmark.private" name="private" type="checkbox" class="form-check-input" :id="'editIsPrivate'+bookmark.id" checked>
                                            <input v-else name="private" type="checkbox" class="form-check-input" :id="'editIsPrivate'+bookmark.id">
                                            <label class="form-check-label" :for="'editIsPrivate'+bookmark.id">Mark if bookmark is private</label>
                                        </div>
                                        
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Tags</label>
                                    <tags-input :element-id="'editTags'+bookmark.id"></tags-input>
                                </div>

                                <div class="form-group">
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
    </div>
    </div>

</template>

<script>


import Bookmark from '../components/Bookmark'
import User from '../services/User'
import VoerroTagsInput from '@voerro/vue-tagsinput';

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
        create(submitElement){
            const data = submitElement.target.elements

            console.log(data["newTags"].value)

            User.createBookmark(
                data["url"].value,
                data["name"].value,
                data["private"].checked,
                (response) => {
                    if(response.status == 201){
                        this.listBookmarks()
                    } else {
                        const errorElement = document.getElementById("errorNew")
                        errorElement.innerHTML = response.error
                        errorElement.collapse('show')
                    }
                }
            )

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

            console.log("tags: " + data["editTags"+bookmarkId].value)

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
    components : {Bookmark, "tags-input": VoerroTagsInput},
    mounted : function() {
        this.listBookmarks()
        
    }

}
</script>

