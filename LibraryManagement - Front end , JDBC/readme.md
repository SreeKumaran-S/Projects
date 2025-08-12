PROJECT NAME : Library Management System. This project supports role-based access control (Admin, Librarian, Guest) with RESTful APIs.

## 🧱 Tech Stack

| Layer        | Technology                    |
|--------------|-------------------------------|
| Frontend     | Html, Css , JavaScript        |
| Backend      | Java (Servlets, JDBC)         |
| Database     | MySQL                         |
| Web Server   | Apache Tomcat                 |
| JSON Library | Gson                          |

FLOW :

```
Client 
   ↓
Servlet (Controller)  -->  Filter (Permission Check)
   ↓
Service (Business logic)
   ↓
DAO (Query DB using JDBC)
   ↓
Model (Data representation)
```