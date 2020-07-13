<template>
    <div id="app" class="container bg-light">
        <div id="page-wrapper">
            <Header />
            <div class="container-flush p-5">
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