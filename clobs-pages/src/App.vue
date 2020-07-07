<template>
    <div id="app" class="container-fluid bg-light">
        <div id="page-wrapper">
            <Header />
            <div class="p-5">
                <router-view></router-view>
            </div>
        </div>
    </div>
  
</template>

<script>

import Header from './components/Header'

export default {
    components : {
        Header
    }

}
</script>

<style>

</style>