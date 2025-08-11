let BASE_URL = "http://localhost:8000/LibraryManagement";

let operationButtons = Array.from(document.querySelectorAll(".button-group button"));
let queryParams = document.getElementById("queryParams");
let requestBody = document.getElementById("requestBody");
let resultArea = document.getElementById("resultArea");

let queryParamsJson = {};
let requestBodyJson = {};




queryParams.addEventListener("input", function() {
    queryParamsJson = queryParams.value;

});

requestBody.addEventListener("input", function() {
    requestBodyJson = requestBody.value;
});

operationButtons.forEach(btn =>{
    btn.onclick = function (event) {
        let actionName = event.currentTarget.getAttribute("data-action");
        let action = window[actionName];
        action();
    };
});


// AUTHENTICATION
function getCurrentUser(){
   apiRequest('/auth/me', 'GET', null, (resp) => {
     resultArea.value = JSON.stringify(resp, null, 2);
   });
}

function login(){
    apiRequest('/auth/login', 'POST', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function logout(){
    apiRequest('/auth/logout', 'POST', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

// ROLES
function getAllRoles(){
    apiRequest('/roles', 'GET', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function getRoleById(){
    let id = prompt("Enter Role ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }

    apiRequest(`/roles/${id}`, 'GET', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

// BOOKS
function getBooksPaginated(){
    apiRequest('/books', 'GET', queryParamsJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function getBookById(){
    let id = prompt("Enter Book ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }

    apiRequest(`/books/${id}`, 'GET', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function addBook(){
    apiRequest('/books', 'POST', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function updateBook(){
    let id = prompt("Enter Book ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/books/${id}`, 'PUT', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function deleteBook(){
    let id = prompt("Enter Book ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/books/${id}`, 'DELETE', null, (resp) => {  
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

// AUTHORS
function getAuthorsPaginated(){
    apiRequest('/authors', 'GET', queryParamsJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function getAuthorById(){
    let id = prompt("Enter Author ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/authors/${id}`, 'GET', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function addAuthor(){
    apiRequest('/authors', 'POST', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function updateAuthor(){
    let id = prompt("Enter Author ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/authors/${id}`, 'PUT', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function deleteAuthor(){
    let id = prompt("Enter Author ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/authors/${id}`, 'DELETE', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

// USERS
function getUsersPaginated(){
    apiRequest('/users', 'GET', queryParamsJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function getUserById(){
    let id = prompt("Enter User ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/users/${id}`, 'GET', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function addUser(){
    apiRequest('/users', 'POST', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function updateUser(){
    let id = prompt("Enter User ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/users/${id}`, 'PUT', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function deleteUser(){
    let id = prompt("Enter User ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/users/${id}`, 'DELETE', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

// ALLOCATIONS
function getAllocationsPaginated(){
    apiRequest('/allocations', 'GET', queryParamsJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function getAllocationById(){
    let id = prompt("Enter User ID:");
    if (!id) {
        resultArea.value = "Error: No ID provided";
        return;
    }
    apiRequest(`/allocations/${id}`, 'GET', null, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function borrowBook(){
    apiRequest('/allocations', 'POST', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}

function returnBook(){
    apiRequest('/allocations', 'PUT', requestBodyJson, (resp) => {
        resultArea.value = JSON.stringify(resp, null, 2);
    });
}
function clearInputValues(){
    queryParams.value='';
    requestBody.value='';
    queryParamsJson={};
    requestBodyJson={};
}


function apiRequest(endpoint, method = 'GET', data = null, callback) {
    const options = {
      method,
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
    };
   
    let parsedData = data;
    if (typeof data === 'string') {
        try {
            parsedData = JSON.parse(data);
        } catch (e) {
            console.warn("Invalid JSON string passed to apiRequest");
            parsedData = null;
        }
    }

    if ((method === 'GET' || method === 'DELETE') && parsedData) {
      let queryParams = new URLSearchParams(parsedData).toString();
      if(queryParams){
        endpoint += `?${queryParams}`;
      }
      
    } else if (parsedData) {
      options.body = JSON.stringify(parsedData);
    }
  
    fetch(`${BASE_URL}${endpoint}`, options)
      .then(response => response.json())
      .then(data => {
        clearInputValues();
        callback(data);
      })
      .catch(error => {
        clearInputValues();
        callback(error);
    });
  }