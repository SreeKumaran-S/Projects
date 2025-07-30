const BASE_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:4000'; 

  export function getBooks(data,callback) {
    apiRequest('/books', 'GET', data, callback);
  }
  export function uploadBook(data,callback){
    apiRequest('/books', 'POST', data, callback);
  }
  export function updateBook(isbnNumber, data, callback){
    apiRequest(`/books/${isbnNumber}`, 'PATCH', data, callback);
  }
  export function deleteBook(data,callback){
    apiRequest('/books', 'DELETE', data, callback);
  }
  

  export function getAuthors(data,callback) {
    apiRequest('/authors', 'GET', data, callback);
  }
  
  
  export function validateUser(data,callback){
    apiRequest('/users/validateUser', 'GET', data, callback);
  }
  export function logOutUser(data,callback){
    apiRequest('/users/logout', 'POST', data, callback);
  }
  export function getUserPermission(data,callback){
    apiRequest('/users/getPermissions', 'GET', data, callback);
  }
  

  

  export function apiRequest(endpoint, method = 'GET', data = null, callback) {
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  };
 
  if ((method === 'GET' || method === 'DELETE') && data) {
    const queryParams = new URLSearchParams(data).toString();
    if(queryParams){
      endpoint += `?${queryParams}`;
    }
    
  } else if (data) {
    options.body = JSON.stringify(data);
  }

  fetch(`${BASE_URL}${endpoint}`, options)
    .then(response => response.json())
    .then(data => {
       if(data.success){
        callback.success(data)
       }
       else{
        callback.error(data);
       }
    })
    .catch(error => callback.error(error));
}