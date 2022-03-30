# STUDENT MANAGEMENT SYSTEM

## Parameters
- Long id : Unique id for each student
- String firstName : Student's First name
- String lastName : Student's Last name
- String email : Student's email (**unique**)
- String phoneNumber : Student's phone number **optional**

---
## Endpoints

## Root URL
`http://localhost:8080/student-management-system/api/v1/students`

### GET: Returns all students

### POST: Add new student, (see example below).

```
{
    "firstName": "Gunnar",
    "lastName": "Gunnarsson",
    "email": "Gunnar@gunnar.se",
    "phoneNumber": "07077077070707"
}
```

### PUT: Update student if existing, else create new (requires ID).
```
{
    "id": 1,
    "firstName": "Gunnar",
    "lastName": "Gunnarsson",
    "email": "Gunnar@gunnar.se",
    "phoneNumber": "07077077070707"
}
```

---

`http://localhost:8080/student-management-system/api/v1/students/{id}`

### GET: Returns student with that id.

### DELETE: Deletes student with that id.

### PATCH: Patches student with that id (requires body).

---

`http://localhost:8080/student-management-system/api/v1/students/query?lastName={lastName}`

### GET: Returns student(s) with matching last name.