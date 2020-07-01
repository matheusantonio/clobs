<template>
    <div>
        <div v-if="top">
            <a :href="url">{{name}}</a>
            <p>{{quantity}}</p>
        </div>
        <div v-else-if="recent">
            <a :href="url">{{name}}</a>
            <p>{{createdAt}}</p>
        </div>
        <div v-else-if="user">
            <a :href="url">{{name}}</a>
            <p v-if="isPrivate">[Private]</p>
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