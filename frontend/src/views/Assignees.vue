<template>
    <div>
        <div>
            <b-card>
                <b-card-header><h1>Assignees</h1></b-card-header>
                <div class="createAssignee my-3" style="float: right">
                    <b-btn pill variant="outline-success" @click="show=true">
                        <b-icon icon="plus"></b-icon>
                        Create Assignee
                    </b-btn>
                </div>
                <template>
                    <div id="assignees">
                        <b-table noCollapse hover responsive :items="items" :fields="fields">
                            <template v-slot:cell(action)="data">
                                <router-link :to="'/assignees/' + data.item.id">
                                    <b-btn class="m-1" size="sm" pill variant="outline-primary">
                                        <b-icon icon="pencil"></b-icon>
                                    </b-btn>
                                </router-link>
                                <b-btn class="m-1" size="sm" pill variant="outline-danger"
                                       v-on:click="showDeleteModal(data.item.id)">
                                    <b-icon icon="trash"></b-icon>
                                </b-btn>
                            </template>
                        </b-table>
                    </div>
                </template>
            </b-card>
        </div>
        <b-modal
            v-model="show"
            ref="modal"
            title="Create an Assignee"
            hide-footer
        >
            <div>
                <div>
                    <b-form @submit="onSubmit">
                        <b-form-group label="Prename:">
                            <b-form-input
                                v-model="assignee.prename"
                                type="text"
                                required
                                invalid-feedback="Name is required"
                            ></b-form-input>
                        </b-form-group>
                        <b-form-group label="Name:">
                            <b-form-input
                                v-model="assignee.name"
                                type="text"
                                required
                            ></b-form-input>
                        </b-form-group>
                        <b-form-group label="Email">
                            <b-form-input
                                v-model="assignee.email"
                                type="text"
                            ></b-form-input>
                        </b-form-group>
                        <div class="w-100">
                            <b-button
                                pill
                                variant="outline-danger"
                                class="float-right m-1"
                                @click="show = false"
                            >
                                Cancel
                            </b-button>
                            <b-button
                                pill
                                variant="outline-primary"
                                class="float-right m-1"
                                type="submit"
                            >
                                Submit
                            </b-button>
                        </div>
                    </b-form>
                </div>
            </div>
        </b-modal>
        <b-modal v-model="showDelete" ref="modal" title="Delete" hide-footer>
            Are you sure you want to delete Assignee with ID {{assignee.id}}? You cannot undo this action.
            <div class="w-100 mt-1">
                <b-button pill variant="outline-danger" class="float-right m-1" @click="deleteAssignee(assignee.id)">
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
import {fetchAllAssignees, deleteAssigneeById, assigneeTableFields, assignee} from "@/js/assignee-rest-client";

export default {
    name: "Assignees",
    data() {
        return {
            assignee: {},
            fields: [],
            items: [],
            deleteMessage: "",
            show: false,
            showDelete: false
        };
    },
    methods: {
        // send GET request to API to fetch all assignees
        fetchAllAssignees: async function () {
            this.items = await fetchAllAssignees();
        },
        deleteAssignee: async function (id) {
            await deleteAssigneeById(id);
            // show success message using BootstrapVue toast component
            showToastMessage(
                this,
                "Alert",
                `Successfully deleted assignee with ID ${id}!`,
                "success"
            );
            this.showDelete = false;
            await this.fetchAllAssignees()
        },
        showDeleteModal: function (id) {
            this.showDelete = true;
            this.assignee.id = id;
        },
        // executed on form submit
        onSubmit: function (event) {
            event.preventDefault();
            axios
                .post(`${config.apiBaseUrl}/assignees`, this.assignee)
                .then((response) => {
                    showToastMessage(
                        this,
                        "Alert",
                        `Successfully created a new assignee with ID ${response.data.id}!`,
                        "success"
                    );
                    this.fetchAllAssignees();
                    this.assignee = assignee();
                    this.show = false;
                })
                .catch((error) => {
                    const errObject = error.response.data.errors[0];
                    showToastMessage(
                        this,
                        "Alert",
                        `Error: ${errObject.field} ${errObject.defaultMessage}!`,
                        "danger"
                    );
                });
        }
    },
    // executed after the component has been started
    created: function () {
        this.fetchAllAssignees();
        this.fields = assigneeTableFields();
        this.assignee = assignee();
    }
};
</script>
<style>
@import '../assets/stylesheet.css';
</style>