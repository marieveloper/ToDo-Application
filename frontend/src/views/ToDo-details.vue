<template>
    <div>
        <div>
            <b-card>
                <b-card-header>
                    <b-form-group>
                        <b-button style="float:right" size="sm" clas="mb-0" href="#/todos/">
                            <b-icon icon="x-lg"></b-icon>
                        </b-button>
                        <h1>ToDo Details: {{ toDoToUpdate.title }}</h1>
                    </b-form-group>
                </b-card-header>
                <b-form @submit="onSubmit">
                    <b-form-group label="Title:" class="mt-1">
                        <b-form-input v-model="toDoToUpdate.title" type="text" required></b-form-input>
                    </b-form-group>
                    <b-form-group label="Description:">
                        <b-form-textarea v-model="toDoToUpdate.description" type="text" required></b-form-textarea>
                    </b-form-group>
                    <b-form-group>
                        <b-form-checkbox v-model="toDoToUpdate.finished" type="boolean" class="m-1"> Finished
                        </b-form-checkbox>
                    </b-form-group>
                    <b-form-group>
                        <b-form-row>
                            <template>
                                <div class="assignees" v-for="assignee in assignees"
                                     v-bind:key="assignee.id">
                                    <router-link :to="'/assignees/' + assignee.id">
                                        <b-badge pill variant="light" class="text-wrap m-1" size="l"
                                                 style="font-size: medium;font-weight: normal;">
                                            {{ assignee.name }}, {{ assignee.prename }}
                                        </b-badge>
                                    </router-link>
                                </div>
                            </template>
                        </b-form-row>
                        <b-btn class="m-1" pill variant="outline-secondary" @click="fetchAllAssignees(toDoToUpdate)">
                            Update Assignees
                            <b-icon icon="person-plus"></b-icon>
                        </b-btn>
                    </b-form-group>
                    <b-form-group label="Due Date">
                        <b-form-datepicker id="example-datepicker" v-model="toDoToUpdate.dueDate"
                                           class="mb-2" valueDefault={null}></b-form-datepicker>
                    </b-form-group>
                    <b-button pill type="submit" variant="outline-primary" class="m-1">Submit</b-button>
                    <b-button pill variant="outline-danger" class="m-1" href="#/todos/">Cancel</b-button>
                </b-form>
            </b-card>
        </div>
        <b-modal v-model="show" ref="modal" title="Update Assignees" hide-footer size="lg">
            <div>
                <div>
                    <b-form @submit="onSubmitAssignees(toDoToUpdate)">
                        <b-table noCollapse hover responsive :items="items" :fields="fields" @row-clicked="rowClicked">
                            <template v-slot:cell(selected)="{ item, field: { key } }">
                                <b-checkbox v-model="item[key]" type="boolean"></b-checkbox>
                            </template>
                        </b-table>
                        <div class="w-100">
                            <b-button pill variant="outline-danger" class="float-right m-1" @click="show = false">
                                Cancel
                            </b-button>
                            <b-button pill variant="outline-primary" class="float-right m-1"
                                      @click="onSubmitAssignees(toDoToUpdate)">
                                Submit
                            </b-button>
                        </div>
                    </b-form>
                </div>
            </div>
        </b-modal>
    </div>
</template>

<script>
// import configuration with API url; @ refers to the src directory
import config from "@/config";
// import library for HTTP requests
import axios from "axios";
// import JS method to use in this view
import {showToastMessage} from "@/js/util";
import {fetchAllAssignees, assigneeSelectTableFields} from "@/js/assignee-rest-client";
import {fetchToDoById} from "@/js/toDo-rest-client";

export default {
    name: "ToDoDetails",
    data() {
        return {
            toDoToUpdate: {
                id: "",
                title: "",
                description: "",
                finished: "",
                assigneeIdList: [],
                assigneeList: [],
                dueDate: ""
            },
            assignees: [],
            assigneeIds: [],
            fields: [],
            items: [],
            selected: [],
            show: false
        };
    },
    computed: {
        selectedRows() {
            return this.items.filter(item => item.selected)
        }
    },
    methods: {
        //selects or deselects a row
        rowClicked(item) {
            if (item.selected) {
                this.$set(item, 'selected', false)
            } else {
                this.$set(item, 'selected', true)
            }
        },
        // send GET request to API to fetch all assignees
        fetchAllAssignees: async function (todo) {
            this.items = await fetchAllAssignees();
            //sets preselected assignees of todos
            for (const element of this.items) {
                for (const assignee of this.assignees) {
                    if (element.id === assignee.id) {
                        this.$set(element, 'selected', true);
                    }
                }
            }
            this.toDoToUpdate = todo;
            this.show = true;
        },
        //updates the assigneeList of the todo
        onSubmitAssignees: function (todo) {
            this.assigneeIds = [];
            this.selectedRows.forEach(
                assignee => this.assigneeIds.push(JSON.stringify(assignee.id)));
            todo.assigneeIdList = this.assigneeIds;
            this.assignees = this.selectedRows;
            this.show = false;
        },
        // executed on form submit
        onSubmit: function (event) {
            event.preventDefault();
            axios
                .put(`${config.apiBaseUrl}/todos/${this.toDoToUpdate.id}`, this.toDoToUpdate)
                .then((response) => {
                    showToastMessage(this, "Alert", `Successfully updated assignee with ID 
                    ${response.data.id}!`, "success");
                    this.$router.push("/todos");
                })
                .catch((error) => {
                    const errObject = error.response.data.errors[0];
                    showToastMessage(this, "Alert", `Error: ${errObject.field} ${errObject.defaultMessage}!`,
                        "danger");
                });
        }
    },
    // executed after the component has been started
    created: async function () {
        this.toDoToUpdate = await fetchToDoById(this.$route.params.id);
        this.toDoToUpdate.assigneeList.forEach(
            assignee => this.assigneeIds.push(JSON.stringify(assignee.id)));
        this.toDoToUpdate.assigneeIdList = this.assigneeIds;
        if (this.toDoToUpdate.dueDate === null) {
            this.toDoToUpdate.dueDate = "";
        } else {
            this.toDoToUpdate.dueDate = new Date(this.toDoToUpdate.dueDate);
        }
        this.assignees = this.toDoToUpdate.assigneeList;
        this.fields = assigneeSelectTableFields();
    }
};
</script>
<style>
@import '../assets/stylesheet.css';
</style>