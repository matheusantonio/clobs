<template>
    <div>

        <div v-if="top">
            <a :href="url">
                {{name}}
            </a>
            <p class="badge badge-info m-1">
                {{quantity}}
                </p>
        </div>

        <div v-else-if="recent">
            <a :href="url">
                {{name}}
            </a>
            <p class="badge badge-info m-1">
                {{createdAt}}
            </p>
        </div>

        <div v-else-if="user">
            <a :href="url" >
                {{name}}
            </a>
            <p v-if="isPrivate" class="badge badge-info m-1">
                Private
            </p>
            <p v-else class="badge badge-info m-1">
                Public
            </p>
        </div>

    </div>
    
  
</template>

<script>
export default {

    props: {
        top : {type: Boolean},
        recent : {type : Boolean},
        user : {type : Boolean},
        url : {},
        name : {},
        createdAt : {},
        isPrivate : {},
        quantity: {}

    }

}
</script>

<style>

</style>