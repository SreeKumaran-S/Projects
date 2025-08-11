********************************** AUTHENTICATION **********************************

1. GET - http://localhost:8000/LibraryManagement/auth/me

2. POST - http://localhost:8000/LibraryManagement/auth/login

{
  "email": "john@email.com",
  "password": "john"
}

3. POST - http://localhost:8000/LibraryManagement/auth/logout

============================================== END ==============================================

********************************** ROLES **********************************

1 . GET ALL ROLES - http://localhost:8000/LibraryManagement/roles
  1.1 GET SINGLE ROLE - GET http://localhost:8000/LibraryManagement/roles/:roleId

============================================== END ==============================================

********************************** USERS **********************************

1. GET - http://localhost:8000/LibraryManagement/users
queryParams
{
  "startIndex":0,
  "limit":10
}
  1.1 GET SINGLE USER - GET http://localhost:8000/LibraryManagement/users/:userId

2. POST - http://localhost:8000/LibraryManagement/users
body
{
  "name": "john_doe",
  "password": "password123",
  "email": "john.doe@example.com",
  "roleId": 1
}

3. PUT - http://localhost:8000/LibraryManagement/users/:userId
{
  "name": "updated_user",
  "password": "newpassword123",
  "email": "updated@example.com",
  "roleId": 2
}
4. DELETE - http://localhost:8000/LibraryManagement/users/:userId

============================================== END ==============================================

********************************** BOOKS **********************************

1. GET  - http://localhost:8000/LibraryManagement/books
queryParams
{
  "startIndex":0,
  "limit":10
}

  1.1 GET SINGLE BOOK - GET http://localhost:8000/LibraryManagement/books/:bookId

2. POST -http://localhost:8000/LibraryManagement/books
body
{
  "name": "The Great Gatsby",
  "authorId": 1,
  "isbn": 1234567890123,
  "genre": "Fiction",
  "availableCopies": 5,
  "totalCopies": 5
}

3. PUT - http://localhost:8000/LibraryManagement/books/:bookdId
{
  "name": "Updated Book Title",
  "authorId": 1,
  "isbn": 1234567890123,
  "genre": "Updated Genre",
  "availableCopies": 3,
  "totalCopies": 5
}

4. DELETE http://localhost:8000/LibraryManagement/books/:bookId

============================================== END ==============================================

********************************** AUTHORS **********************************

1. GET -  http://localhost:8000/LibraryManagement/authors
queryParams
{
  "startIndex":0,
  "limit":10
}
   1.1 GET SINGLE AUTHOR - http://localhost:8000/LibraryManagement/authors/:authorId

2. POST http://localhost:8000/LibraryManagement/authors
body
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}

3. PUT http://localhost:8000/LibraryManagement/authors/:authorId
{
  "name": "Updated Author Name",
  "email": "updated@example.com"
}

4. DELETE http://localhost:8000/LibraryManagement/authors/:authorId

============================================== END ==============================================

********************************** ALLOCATIONS **********************************

1. GET - http://localhost:8000/LibraryManagement/allocations
queryParams
{
  "startIndex":0,
  "limit":10
}
 
 1.1 GET SINGLE ALLOCATION - GET http://localhost:8000/LibraryManagement/allocations/:userId

2. POST - http://localhost:8000/LibraryManagement/allocations
body
{
  "userId": 1,
  "bookId": 1
}

3. PUT - http://localhost:8000/LibraryManagement/allocations
{
  "userId": 1
}

============================================== END ==============================================



