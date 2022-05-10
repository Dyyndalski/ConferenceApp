# Readme: Conference Application

## Building the project
You will need:

*	Java JDK 8 or higher
*	Maven 3.1.1 or higher
*	Git

Clone the project and use Maven to run the application:

	$ mvn spring-boot:run

The app will start running at <http://localhost:8080>.

To see the DataBase: <http://localhost:8080/h2-console>


## The app defines following CRUD APIs.
*	Add user to lecture:

`
POST /user-managment/user
`


*	Update user email:

`
PATCH /user-managment/email
`

*	Getting lectures attended by user login:


`GET /user-managment/lectures/{login}`

*	Cancel reservation:

`
POST /user-managment/reservation`


*	Getting all users from db:

`GET /user-managment/users`


*	Getting conference plan:

`GET /lecture-managment/conf-plan`

*	Getting statistic of every lectures:

`GET /lecture-managment/stat-lectures`

*	Getting statistic of path-topic:

`GET /lecture-managment/stat-topics`


## Sample queries

* Add user and cancel reservation example:

```json
{
        "login" : "user1",
        "email" : "user@gmail.com",
        "conferenceIndex" : 1
}
