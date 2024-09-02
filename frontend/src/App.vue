<template>
    <div id="app">
        <!-- this navigation will appear on every route -->

        <b-navbar toggleable="lg" type="dark" id="nav">
            <b-navbar-brand to="/">
                <img alt="Vue logo" src="./assets/checklist1.png" class="logo" />
                Home
            </b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item to="/about">About</b-nav-item>
                    <b-nav-item to="/assignees"><b-icon icon="person"></b-icon> Assignees</b-nav-item>
                    <b-nav-item to="/todos"><b-icon icon="check2-square"></b-icon> ToDos</b-nav-item>
                </b-navbar-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>

        <!-- the different view templates will appear in this HTML element based on the route -->
        <div class="main">
            <router-view></router-view>
        </div>
    </div>
</template>

<style>
@import 'assets/stylesheet.css';
</style>
<script>export default {
    components: {}
}
</script>