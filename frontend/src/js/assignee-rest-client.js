// send GET request to API to fetch all assignees
import axios from "axios";
import config from "@/config";


export async function fetchAllAssignees() {
    const response = await axios.get(`${config.apiBaseUrl}/assignees`);
    return response.data;
}

export async function deleteAssigneeById(id) {
    const response = await axios.delete(`${config.apiBaseUrl}/assignees/${id}`);
    return response.data;
}

export function assigneeTableFields() {
       const fields = [
            {key: "action", label: 'Actions'},
            {key: "prename", label: "Prename", sortable: true},
            {key: "name", label: "Name", sortable: true},
            {key: "email", label: "Email", sortable: true}
        ]
    return fields;
}

export function assigneeSelectTableFields() {
    const fields = [
        {key: "selected", label: 'Selected'},
        {key: "prename", label: "Prename", sortable: true},
        {key: "name", label: "Name", sortable: true}
    ]
    return fields;
}
export function assignee(){
    return {
        prename: "",
        name: "",
        email: ""
    }
}