import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import About from "../views/About.vue";
import Assignees from "../views/Assignees.vue";
import AssigneeDetails from "@/views/Assignee-details";
import ToDos from "@/views/ToDos";
import ToDoDetails from "@/views/ToDo-details";

Vue.use(VueRouter);

// all frontend routes of the app, i.e. bind a path to a Vue component
const routes = [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/about",
        name: "About",
        component: About
    },
    {
        path: "/assignees",
        name: "Assignees",
        component: Assignees
    },
    {
        path: "/assignees/:id",
        name: "AssigneeDetails",
        component: AssigneeDetails
    },
    {
        path: "/todos",
        name: "ToDos",
        component: ToDos
    },
    {
        path: "/todos/:id",
        name: "ToDoDetails",
        component: ToDoDetails
    }
];

const router = new VueRouter({
    routes
});

export default router;
