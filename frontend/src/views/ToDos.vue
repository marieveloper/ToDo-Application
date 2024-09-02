<template>
    <div>
        <div>
            <b-card>
                <b-card-header><h1>ToDos</h1></b-card-header>
                <b-row class="mb-2">
                    <b-col lg="6" class="my-1">
                        <b-input-group>
                            <b-form-input id="filter-input" v-model="filter" type="search"
                                          placeholder="Search"></b-form-input>
                            <b-icon icon="search" class="align-self-center m-2 h5"></b-icon>
                        </b-input-group>
                    </b-col>
                    <b-col lg="6" class="my-1 ">
                        <div class="createToDo" style="float: right">
                            <b-btn class="m-1" pill variant="outline-success" @click="createToDo">
                                <b-icon icon="plus"></b-icon>
                                Create ToDo
                            </b-btn>
                        </div>
                        <div class="createToDo" style="float: right">
                            <b-btn class="m-1" pill variant="outline-dark" @click="csvExport">
                                <b-icon icon="download"></b-icon>
                                Export ToDo
                            </b-btn>
                        </div>
                    </b-col>
                </b-row>
                <template>
                    <div id="todos">
                        <b-table id="todos" noCollapse hover responsive :items="currentPageItems" :fields="fields"
                                 :filter="filter" @filtered="onFiltered" :filter-included-fields="filterOn"
                                 :per-page="5" :current-page="1" paginated>
                            <template v-slot:cell(action)="data">
                                <router-link :to="'/todos/' + data.item.id">
                                    <b-btn class="m-1" size="sm" pill variant="outline-primary">
                                        <b-icon icon="pencil"></b-icon>
                                    </b-btn>
                                </router-link>
                                <b-btn class="m-1" size="sm" pill variant="outline-danger"
                                       v-on:click="showDeleteModal(data.item.id)">
                                    <b-icon icon="trash"></b-icon>
                                </b-btn>
                            </template>
                            <template v-slot:cell(finished)="data">
                                <b-form>
                                    <b-form-checkbox v-model="data.item.finished"
                                                     v-on:change="updateFinished(data.item)"></b-form-checkbox>
                                </b-form>
                            </template>
                            <template v-slot:cell(assigneeList)="data">
                                <template>
                                    <div class="assignees" v-for="assignee in data.item.assigneeList"
                                         v-bind:key="assignee.id">
                                        <router-link :to="'/assignees/' + assignee.id">
                                            <b-badge pill variant="light" class="text-wrap mb-1" size="l"
                                                     style="font-size: medium;font-weight: normal;">
                                                {{ assignee.name }}, {{ assignee.prename }}
                                            </b-badge>
                                        </router-link>
                                    </div>
                                </template>
                            </template>
                            <template v-slot:cell(createdDate)="data">
                                <template>
                                    <b-form>
                                        {{ data.item.createdDate.toLocaleDateString() }}
                                    </b-form>
                                </template>
                            </template>
                            <template v-slot:cell(dueDate)="data">
                                <template v-if="data.item.dueDate.toLocaleDateString() === '1.1.1970'">
                                    <b-form>
                                        <b-badge pill variant="light" class="text-wrap mb-1" size="l"
                                                 style="font-size: medium;font-weight: normal;">
                                            Not set
                                        </b-badge>
                                    </b-form>
                                </template>
                                <template v-else>
                                    <b-form>
                                        {{ data.item.dueDate.toLocaleDateString() }}
                                    </b-form>
                                </template>
                            </template>
                        </b-table>
                    </div>
                    <b-card-footer>
                        <b-pagination v-model="currentPage" :total-rows="filteredItems.length" :per-page="perPage"
                                      align="center" pills></b-pagination>
                    </b-card-footer>
                </template>
            </b-card>
            <b-card class="my-2">
                <b-button pill v-b-toggle.collapse-1 variant="light">Finished ToDos
                    <b-icon icon="chevron-down"></b-icon>
                </b-button>
                <template>
                    <b-collapse id="collapse-1" class="mt-2">
                        <div id="finishedTodos">
                            <b-table noCollapse hover responsive :items="filteredFinishedItems" :fields="finishedFields"
                                     :filter="filter" @filtered="onFiltered"
                                     :filter-included-fields="filterOn">
                                <template v-slot:cell(action)="data">
                                    <router-link :to="'/todos/' + data.item.id">
                                        <b-btn class="m-1" size="sm" pill variant="outline-primary">
                                            <b-icon icon="pencil"></b-icon>
                                        </b-btn>
                                    </router-link>
                                    <b-btn class="m-1" size="sm" pill variant="outline-danger"
                                           v-on:click="showDeleteModal(data.item.id)">
                                        <b-icon icon="trash"></b-icon>
                                    </b-btn>
                                </template>
                                <template v-slot:cell(finished)="data">
                                    <b-form>
                                        <b-form-checkbox v-model="data.item.finished"
                                                         v-on:change="updateFinished(data.item)"></b-form-checkbox>
                                    </b-form>
                                </template>
                                <template v-slot:cell(assigneeList)="data">
                                    <template>
                                        <div class="assignees" v-for="assignee in data.item.assigneeList"
                                             v-bind:key="assignee.id">
                                            <router-link :to="'/assignees/' + assignee.id">
                                                <b-badge pill variant="light" class="text-wrap mb-1" size="l"
                                                         style="font-size: medium;font-weight: normal;">
                                                    {{ assignee.name }}, {{ assignee.prename }}
                                                </b-badge>
                                            </router-link>
                                        </div>
                                    </template>
                                </template>
                                <template v-slot:cell(createdDate)="data">
                                    <template>
                                        <b-form>
                                            {{ data.item.createdDate.toLocaleDateString() }}
                                        </b-form>
                                    </template>
                                </template>
                                <template v-slot:cell(dueDate)="data">
                                    <template v-if="data.item.dueDate.toLocaleDateString() === '1.1.1970'">
                                        <b-form>
                                            <b-badge pill variant="light" class="text-wrap mb-1" size="l"
                                                     style="font-size: medium;font-weight: normal;">
                                                Not set
                                            </b-badge>
                                        </b-form>
                                    </template>
                                    <template v-else>
                                        <b-form>
                                            {{ data.item.dueDate.toLocaleDateString() }}
                                        </b-form>
                                    </template>
                                </template>
                                <template v-slot:cell(finishedDate)="data">
                                    <template>
                                        <b-form>
                                            {{ data.item.finishedDate.toLocaleDateString() }}
                                        </b-form>
                                    </template>
                                </template>
                            </b-table>
                        </div>
                    </b-collapse>
                </template>
            </b-card>
        </div>
        <b-modal v-model="show" ref="modal" title="Create an ToDo" hide-footer>
            <div>
                <div>
                    <b-form @submit="onSubmitToDo">
                        <b-form-group label="Title:">
                            <b-form-input v-model="todo.title" type="text" required
                                          invalid-feedback="Name is required"></b-form-input>
                        </b-form-group>
                        <b-form-group label="Description:">
                            <b-form-textarea v-model="todo.description" type="text" required></b-form-textarea>
                        </b-form-group>
                        <b-form-group>
                            <b-btn v-b-toggle.collapse-2 class="m-1" pill variant="outline-secondary">
                                Select Assignees
                                <b-icon icon="person-plus"></b-icon>
                            </b-btn>
                            <b-collapse id="collapse-2" class="mt-2">
                                <div id="assignees">
                                    <b-table noCollapse hover responsive :items="assigneeItems" :fields="assigneeFields"
                                             @row-clicked="rowClicked">
                                        <template v-slot:cell(selected)="{ item, field: { key } }">
                                            <b-checkbox v-model="item[key]" type="boolean"></b-checkbox>
                                        </template>
                                    </b-table>
                                </div>
                            </b-collapse>
                        </b-form-group>
                        <b-form-group label="Due Date">
                            <b-form-datepicker id="example-datepicker" v-model="todo.dueDate"
                                               class="mb-2"></b-form-datepicker>
                        </b-form-group>
                        <div class="w-100">
                            <b-button pill variant="outline-danger" class="float-right m-1" @click="show = false">
                                Cancel
                            </b-button>
                            <b-button pill variant="outline-primary" class="float-right m-1" type="submit">
                                Submit
                            </b-button>
                        </div>
                    </b-form>
                </div>
            </div>
        </b-modal>
        <b-modal v-model="showDelete" ref="modal" title="Delete" hide-footer>
            Are you sure you want to delete this ToDo with ID {{ todo.id }}? You cannot undo this action.
            <div class="w-100 mt-1">
                <b-button pill variant="outline-danger" class="float-right m-1" @click="deleteToDo(todo.id)">
                    Delete
                </b-button>
                <b-button pill variant="outline-dark" class="float-right m-1" type="submit"
                          @click="showDelete= false">
                    Cancel
                </b-button>
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
import {
    deleteToDoById,
    fetchAllToDos,
    csvExport,
    transformDTO,
    toDoTableFields,
    toDoTableFinishedFields
} from "@/js/toDo-rest-client";
import {fetchAllAssignees, assigneeSelectTableFields} from "@/js/assignee-rest-client";

export default {
    name: "ToDos",
    data() {
        return {
            todo: {
                title: "",
                description: "",
                finished: false,
                assigneeIdList: [],
                dueDate: "",
                createdDate: ""
            },
            fields: [],
            finishedFields: [],
            assigneeFields: [],
            items: [],
            perPage: 5,
            currentPage: 1,
            totalRows: 0,
            totalFinishedRows: 0,
            finishedToDos: [],
            show: false,
            showDelete: false,
            assignees: [],
            filter: null,
            filterOn: ["title"],
            assigneeItems: []
        };
    },
    computed: {
        DateNow() {
            return new Date(Date.now()).toLocaleDateString();
        },
        currentPageItems() {
            const start = (this.currentPage - 1) * this.perPage;
            return this.filteredItems.slice(start, start + this.perPage);
        },
        filteredItems() {
            return this.items.filter(item => {
                if (!this.filter) return true;
                const filter = this.filter.toLowerCase();
                return (item.title.toLowerCase().indexOf(filter) > -1)
            });
        },
        filteredFinishedItems() {
            return this.finishedToDos.filter(item => {
                if (!this.filter) return true;
                const filter = this.filter.toLowerCase();
                return (item.title.toLowerCase().indexOf(filter) > -1)
            });
        },
        selectedRows() {
            return this.assigneeItems.filter(item => item.selected)
        }
    },
    mounted() {
        // Set the initial number of items
        this.totalRows = this.filteredItems.length
        this.totalFinishedRows = this.filteredFinishedItems.length
    },
    methods: {
        //filter items
        onFiltered(filteredItems) {
            // Trigger pagination to update the number of buttons/pages due to filtering
            this.totalRows = filteredItems.length
            this.totalFinishedRows = filteredItems.length
            this.currentPage = 1
        },
        // send GET request to API to fetch all todos
        fetchAllToDos: async function () {
            this.items = await fetchAllToDos();
            this.finishedToDos = this.items.filter(todo => todo.finished === true);
            this.items.forEach(item => {
                item.dueDate = new Date(item.dueDate);
                item.createdDate = new Date(item.createdDate);
                item.finishedDate = new Date(item.finishedDate);
            });
            this.items = this.items.filter(todo => todo.finished === false);
        },
        //send GET request to API to fetch all assignees
        fetchAllAssignees: async function () {
            this.assigneeItems = await fetchAllAssignees();
            //sets preselected assignees of todos
            for (const element of this.items) {
                for (const assignee of this.assignees) {
                    if (element.id === assignee.id) {
                        this.$set(element, 'selected', true);
                    }
                }
            }
        },
        //show modal to create new todo
        createToDo: function () {
            this.show = true;
            this.assignees = [];
            this.fetchAllAssignees();
            this.todo = {
                title: "",
                description: "",
                finished: false,
                assigneeIdList: [],
                dueDate: "",
                finishedDate: ""
            }
        },
        csvExport: async function () {
            await csvExport();
            showToastMessage(this, "Alert", `Successfully exported all Todos as CSV!`, "success");
        },
        selectAssignees: function (todo) {
            //updates the assigneeIdList of the todo
            let assigneeIds = [];
            this.selectedRows.forEach(
                assignee => assigneeIds.push(JSON.stringify(assignee.id)));
            todo.assigneeIdList = assigneeIds;
            this.assignees = this.selectedRows;
        },
        // sets the selected assignees of the todo
        rowClicked(item) {
            if (item.selected) {
                this.$set(item, 'selected', false)
            } else {
                this.$set(item, 'selected', true)
            }
        },
        deleteToDo: async function (id) {
            await deleteToDoById(id);
            showToastMessage(this, "Alert", `Successfully deleted todo with ID ${id}!`, "success");
            this.showDelete = false;
            await this.fetchAllToDos();
        },
        showDeleteModal: function (id) {
            this.todo.id = id;
            this.showDelete = true;
        },
        updateFinished: function (todo) {
            const toDoToUpdate = transformDTO(todo);
            // send PUT request to API to update a specific todo by ID
            axios.put(`${config.apiBaseUrl}/todos/${todo.id}`, toDoToUpdate
            ).then(() => {
                showToastMessage(this, "Alert", `Successfully updated finished status of todo with ID ${todo.id}!`, "success"
                );
                this.fetchAllToDos();
            });
        },
        // executed on form submit
        onSubmitToDo: function (event) {
            this.selectAssignees(this.todo);
            event.preventDefault();
            axios
                .post(`${config.apiBaseUrl}/todos`, this.todo)
                .then((response) => {
                    this.todo.createdDate = this.DateNow;
                    showToastMessage(this, "Alert", `Successfully created a new todo with ID ${response.data.id}!`, "success");
                    this.fetchAllToDos();
                    this.show = false;
                    this.assignees = [];
                })
                .catch((error) => {
                    const errObject = error.response.data.errors[0];
                    showToastMessage(this, "Alert", `Error: ${errObject.field} ${errObject.defaultMessage}!`, "danger");
                });
        }
    },
    // executed after the component has been started
    created: function () {
        this.fetchAllToDos();
        this.assigneeFields = assigneeSelectTableFields();
        this.fields = toDoTableFields();
        this.finishedFields = toDoTableFinishedFields();
    }
};
</script>
<style>
@import '../assets/stylesheet.css';
</style>