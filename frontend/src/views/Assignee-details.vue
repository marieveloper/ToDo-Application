<template>
    <div>
        <div>
            <b-card>
                <b-card-header>
                    <b-form-group>
                        <b-button style="float:right" size="sm" class="mb-0" href="#/assignees/">
                            <b-icon icon="x-lg"></b-icon>
                        </b-button>
                        <h1>Assignee Details: {{assignee.prename}} {{ assignee.name }}</h1>
                    </b-form-group>
                </b-card-header>
            <b-form @submit="onSubmit">
                <b-form-group label="Prename:">
                    <b-form-input
                        v-model="assignee.prename"
                        type="text"
                        required
                    ></b-form-input>
                </b-form-group>
                <b-form-group label="Name:">
                <b-form-input
                    v-model="assignee.name"
                    type="text"
                    required
                ></b-form-input>
            </b-form-group>
                <b-form-group label="Email:">
                    <b-form-input
                        v-model="assignee.email"
                        type="text"
                    ></b-form-input>
                </b-form-group>
                <b-button pill type="submit" variant="outline-primary" class="m-1">Submit</b-button>
                <b-button pill variant="outline-danger" class="m-1" href="#/assignees/">Cancel</b-button>
            </b-form>
            </b-card>
        </div>
    </div>
</template>
<script>
// import configuration with API url; @ refers to the src directory
import config from "@/config";
// import library for HTTP requests
import axios from "axios";
// import JS method to use in this view
import {showToastMessage} from "@/js/util";

export default {
    name: "AssigneeDetails",
    data() {
        return {
            assignee: {
                prename: "",
                name: "",
                email: ""
            }
        };
    },
    methods: {
        // executed on form submit
        onSubmit: function (event) {
            event.preventDefault();
            axios
                .put(`${config.apiBaseUrl}/assignees/${this.assignee.id}`, this.assignee)
                .then((response) => {
                    showToastMessage(
                        this,
                        "Alert",
                        `Successfully updated assignee with ID ${response.data.id}!`,
                        "success"
                    );
                    this.$router.push("/assignees");
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
        axios
            .get(`${config.apiBaseUrl}/assignees/${this.$route.params.id}`)
            .then((response) => {
                this.assignee = response.data;
            });
    }
};
</script>
<style>
@import '../assets/stylesheet.css';
</style>