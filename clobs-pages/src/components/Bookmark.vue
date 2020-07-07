<template>
    <div>

        <div v-if="top" class="row align-items-center">
            <a :href="url" class="col">
                {{name}}
            </a>
            <p class="col">
                [{{quantity}}]
                </p>
        </div>

        <div v-else-if="recent" class="row align-items-center">
            <a :href="url" class="col">
                {{name}}
            </a>
            <p class="col">
                [{{createdAt}}]
            </p>
        </div>

        <div v-else-if="user" class="row align-items-center">
            <a :href="url" class="col">
                {{name}}
            </a>
            <p v-if="isPrivate" class="col">
                [Private]
            </p>
            <p v-else class="col">
                [Public]
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