# repo047

Marie Kufner <st173710@stud.uni-stuttgart.de>

Matrikelnummer: 3535116 

Git Repo: [repo47](https://sopra.informatik.uni-stuttgart.de/pe2-ws22/repo047.git) 

Programmentwicklung 2 
***

# ToDo Project 

This is a project for the course PE2. It is a simple ToDo application with a REST API and a vue.js frontend.
## Quickstart

Assuming you've followed the given steps in the README.md file in the api and frontend folder, you can follow these instructions to get to know the application and start it quickly:
- Start the API by navigating into the api folder and executing `./mvnw spring-boot:run`.
- Open http://localhost:8080/api/v1/todos in your browser. It will show all available todos in the system.
- Open http://localhost:8080/api/v1/todos/{id} in your browser. It will show the todo with the given id.
- Open http://localhost:8080/api/v1/todos/1080 in your browser. It will show a `404 Not found` error because there is no todo with ID `1080` in the system.
- Start the frontend by navigating into the frontend folder and executing `npm run serve`.
- Open http://localhost:8000/#/ in your browser. It will show the frontend of the application.
- Open http://localhost:8000/#/todos in your browser. It will show all available todos in the system.

