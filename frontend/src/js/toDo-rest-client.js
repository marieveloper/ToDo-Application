// send GET request to API to fetch all assignees
import axios from "axios";
import config from "@/config";


export async function fetchAllToDos() {
    const response = await axios.get(`${config.apiBaseUrl}/todos`);
    return response.data;
}

export async function fetchToDoById(id) {
    const response = await axios.get(`${config.apiBaseUrl}/todos/${id}`);
    return response.data;
}

export async function deleteToDoById(id) {
    const response = await axios.delete(`${config.apiBaseUrl}/todos/${id}`);
    return response.data;
}

export async function csvExport() {
    const response = await axios.get(`${config.apiBaseUrl}/csv-downloads/todos`);
    const anchor = document.createElement('a');
    anchor.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(response.data);
    anchor.target = '_blank';
    anchor.download = 'todos.csv';
    anchor.click();
    return response.data;
}
export function transformDTO(todo){
    let assignees = [];
    // selects assignees from assigneeList
    todo.assigneeList.forEach(
        assignee => assignees.push(JSON.stringify(assignee.id)));
    todo.assigneeIdList = assignees;
    return todo;
}
export function toDoTableFields(){
    const fields = [
        {key: "action", label: 'Actions'},
        {key: "title", label: "Title", sortable: true},
        {key: "description", label: "Description", sortable: true},
        {key: "assigneeList", label: "Assignees"},
        {key: "createdDate", label: "Created Date", sortable: true},
        {key: "dueDate", label: "Due Date", sortable: true},
        {key: "finished", label: "Finished"}
    ]
    return fields;
}
export function toDoTableFinishedFields(){
    const fields = [
        {key: "action", label: 'Actions'},
        {key: "title", label: "Title", sortable: true},
        {key: "description", label: "Description", sortable: true},
        {key: "assigneeList", label: "Assignees"},
        {key: "createdDate", label: "Created Date", sortable: true},
        {key: "dueDate", label: "Due Date", sortable: true},
        {key: "finishedDate", label: "Finished Date", sortable: true},
        {key: "finished", label: "Finished"}
    ]
    return fields;
}
