<template>
    <div class="home">
        <div class="information">
            <b-row>
                <b-card bg-variant="light" class="w-25 m-2" header="Assignees">
                    <b-card class="m-2">
                            <h5> Current Assignees: </h5>
                            <h4> {{ assignees.length }}</h4>
                    </b-card>
                    <b-button pill size="lg" href="#/assignees" variant="outline-dark" class="m-2">
                        <b-icon icon="person"></b-icon>
                        Show all Assignees
                    </b-button>
                </b-card>
                <b-card bg-variant="light" class="w-25 m-2" header="ToDos">
                    <b-card class="m-2">
                            <h5> Current ToDos: </h5>
                            <h4> {{ toDos.length }}</h4>
                            <h5>Finished ToDos:</h5>
                            <h4>{{ toDos.filter(toDo => toDo.finished).length }}</h4>
                    </b-card>
                    <b-button pill size="lg" href="#/todos" variant="outline-dark" class="m-2">
                        <b-icon icon="list"></b-icon>
                        Show all ToDos
                    </b-button>
                </b-card>
            </b-row>
        </div>
    </div>
</template>

<script>
// import JS method to use in this view
import {fetchAllAssignees} from "@/js/assignee-rest-client";
import {fetchAllToDos} from "@/js/toDo-rest-client";

export default {
    name: "Home",
    components: {},
    data() {
        return {
            toDos: [],
            assignees: []
        };
    },
    methods: {
        fetchAllTodos: async function () {
            this.toDos = await fetchAllToDos();
        },
        fetchAllAssignees: async function () {
            this.assignees = await fetchAllAssignees();
        }
    },
    created: function () {
        this.fetchAllTodos();
        this.fetchAllAssignees();
    }
};
</script>
<style>
@import '../assets/stylesheet.css';
</style>
