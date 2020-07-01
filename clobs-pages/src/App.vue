<template>
    <div id="app">
        <div id="page-wrapper" class="gray-bg">
            <Header />
            <body>
                <Index />
            </body>
        </div>
    </div>
  
</template>

<script>

import Header from './components/Header'
import Index from './pages/Index'

export default {

    components: {Header, Index}

}
</script>

<style>

</style>