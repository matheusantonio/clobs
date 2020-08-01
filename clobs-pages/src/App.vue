<template>
    <div id="appbg">
        <div id="app" class="container bg-light">
            <div id="page-wrapper">
                <Header :key="this.$store.state.loged"/>
                <div class="container-flush py-5">
                    <router-view></router-view>
                </div>
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
#appbg {
    padding: 0;
    margin: 0;
    width: 100%;
    min-height: 100vh;
    background-color: rgb(57, 148, 139);
}
</style>