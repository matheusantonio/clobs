<template>
    
    <div :key="bookmarks">
    <div class="p-2 shadow border">

        <div class="m-4">
            <div class="row justify-content-between mb-2">
                <h2 class="ml-3">
                    <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-bookmarks" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M7 13l5 3V4a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v12l5-3zm-4 1.234l4-2.4 4 2.4V4a1 1 0 0 0-1-1H4a1 1 0 0 0-1 1v10.234z"/>
                        <path d="M14 14l-1-.6V2a1 1 0 0 0-1-1H4.268A2 2 0 0 1 6 0h6a2 2 0 0 1 2 2v12z"/>
                    </svg>
                    Your Bookmarks</h2>
                <div 
                    type="button"
                    class="text-success mr-4"
                    data-toggle="collapse"
                    data-target="#newBookmark"
                    aria-expanded="false"
                    aria-controls="newBookmark">
                    <svg width="2em" height="2em" viewBox="0 0 16 16" class="bi bi-bookmark-plus" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M4.5 2a.5.5 0 0 0-.5.5v11.066l4-2.667 4 2.667V8.5a.5.5 0 0 1 1 0v6.934l-5-3.333-5 3.333V2.5A1.5 1.5 0 0 1 4.5 1h4a.5.5 0 0 1 0 1h-4zm9-1a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1H13V1.5a.5.5 0 0 1 .5-.5z"/>
                        <path fill-rule="evenodd" d="M13 3.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0v-2z"/>
                    </svg>
                </div>
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
                                <tags-input element-id="newTags" v-model="newTags"></tags-input>
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

                        <Bookmark class="col" 
                            :name="bookmark.definedname" :url="bookmark.url" 
                            :isPrivate="bookmark.private" :tags=bookmark.tags user/>

                        <div 
                            type="button"
                            class="text-info px-2"
                            data-toggle="collapse"
                            v-bind:data-target="'#edit-' + bookmark.id"
                            aria-expanded="false"
                            v-bind:aria-controls="'edit-'+bookmark.id">
                                <svg width="1.5em" height="1.5em" viewBox="0 0 16 16" class="bi bi-pencil-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                    <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                </svg>
                        </div>
                        
                        <div 
                            type="button"
                            class="text-danger px-2"
                            @click="remove(bookmark.id, bookmark.definedname)">
                            <svg width="1.5em" height="1.5em" viewBox="0 0 16 16" class="bi bi-trash" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                            </svg>
                        </div>

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
                                    {{editTags.get(bookmarkId)}}
                                    <tags-input :element-id="'editTags'+bookmark.id" 
                                        v-model=bookmark.editTags
                                        placeholder=""></tags-input>
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
            bookmarks : [],
            editTags : new Map(),
            timer : '',
            newTags : []
        }
    },
    methods: {
        listBookmarks() {
            User.getBookmarks((response) => {
                if(response.status == 200){
                    this.bookmarks = response.data
                    this.setEditTags()
                } else {
                    this.$router.push({ path : "/login" })
                }
            })
        },
        setEditTags() {
            this.bookmarks.forEach(bookmark => {
                bookmark.editTags = bookmark.tags.map(
                    (tag) => {
                        return {
                            key : "",
                            value : tag
                        }
                    }
                )
            });
        },
        create(submitElement){
            
            const data = submitElement.target.elements
            const tags = this.newTags.map((tag) => tag["value"])
            
            User.createBookmark(
                data["url"].value,
                data["name"].value,
                data["private"].checked,
                tags,
                (response) => {
                    if(response.status == 201){
                        this.listBookmarks()
                        this.newTags = []
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
            const tags = JSON.parse(data["editTags"+bookmarkId].value).map((tag) => tag.value)

            User.editBookmark(
                bookmarkId,
                data["definedname"].value,
                data["private"].checked,
                tags,
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