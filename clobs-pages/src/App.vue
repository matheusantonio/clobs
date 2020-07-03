<template>
    <div id="app">
        <div id="page-wrapper" class="gray-bg">
            <Header />
            <body>
                <Index />
                <Login />
            </body>
        </div>
    </div>
  
</template>

<script>

import Header from './components/Header'
import Index from './pages/Index'
import Login from './pages/Login'

export default {

    components: {Header, Index, Login}

}
</script>

<style>

</style>