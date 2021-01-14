## Project Title

Event Management Project

## Getting Started

This instruction will give some details insight of how to install, run and test the evite sample project. Unit tests have been executed on Postman and curl. Please have a look deployment note to know how to import and run the project and conduct test cases locally.

## Tools and Technologies

1.	Java 8 (1.8.0_211)
2.	Gradle 6 (6.7.1)
3.	IntelliJ ultimate edition 2020.3
4.	Spring  boot 2.3.2
5.	MySQL DB.
6.	JUnit 5 + Mockito
7.	Postman (v.7.36.1)
8.	MacOS (Big Sur)

## Deployment

1.	Download / Clone “evite” from GitHub.
2.	Run IntelliJ and “open” project from IntelliJ
3.	Right click on “build.gradle” and click on “build”. If any problem, please close and open the project to ensure build is performed properly.
4.	Create a MySQL DB “eventmanagement” and create tables “events, users and user_event”. Tables' columns details are described on “DB Table” section.
5.	Right click on “EventManagementApplication.java” class under “src -> main -> java -> com -> sample -> evite” packages and click on “run”. Application will be started on local tomcat with port 8080.
6.	To run EventSerticeTest Unit test, please right click on “test -> java -> com -> sample -> evite -> EventServiceTest”.
7.	To run UserServiceTest Unit test, please right click on “test -> java -> com -> sample -> evite -> UserServiceTest”.
8.  lombok may be required to enable annotation in IntelliJ

## DB Table

DB Name: eventmanagement

Table Name: events

| Column Name | Data Type | Size      | Null        |Default     | Auto Increment | Primary Key | Comments |
| ----------- | ----------| ----------| ----------- |----------- | -----------    | ----------- | -----------|
| event_id      | int       | 11      | -           |-           | Yes            | Yes         |  -          |
| name         | varchar       | 300        | -           |-           | -              | --          |   -         |
| location	        | Varchar   |300      | -         |-           | -              |-            | -           |
| timeZone	| varchar  |100        | -           |-            | -              |-            | -|
| start_time	| dateTime  |-       | -           |-            | -              |-            | DateTime|
| end_time	| dateTime  |-        | -           |-            | -              |-            | DateTime|
| created_at	| dateTime  |-        | -           |-            | -              |-            | CURRENT_TIMESTAMP|
| delete_flg	| tinyint   |1        | -           |0| -              |-            |        -    | -             |


Table Name: users

| Column Name | Data Type | Size      | Null        |Default     | Auto Increment | Primary Key | Comments |
| ----------- | ----------| ----------| ----------- |----------- | -----------    | ----------- | -----------|
| user_id      | int       | 11      | -           |-           | Yes            | Yes         |  -          |
| email         | varchar       | 100        | -           |-           | -              | --          |   -         |
| name	        | varchar   |300      | -         |-           | -              |-            | -           |
| password	| varchar  |300        | -           |-            | -              |-            | -|
| created_at	| dateTime  |-        | -           |-            | -              |-            | CURRENT_TIMESTAMP|
| delete_flg	| tinyint   |1        | -           |0| -              |-            |        -    | -             |

Table Name: user_events

| Column Name | Data Type | Size      | Null        |Default     | Auto Increment | Primary Key | Comments |
| ----------- | ----------| ----------| ----------- |----------- | -----------    | ----------- | -----------|
| user_event_id      | int       | 11      | -           |-           | Yes            | Yes         |  -          |
| email         | varchar       | 100        | -           |-           | -              | --          |   -         |
| event_id	        | int   |11      | -         |-           | -              |-            | -           |
| created_at	| dateTime  |-        | -           |-            | -              |-            | CURRENT_TIMESTAMP|
| delete_flg	| tinyint   |1        | -           |0| -              |-            |        -    | -             |

## Configuration
“application.property” file contains the configuration information for DB setup and other configuration information. Please change password if needed. Property file resides inside resource package.

## API Documentation

### Event API
___

### 1.	Create Event

METHOD  :	POST

URL	    : `http://localhost:8080/event/create`

Comment :  To test the API, content-type should be "application/json".

Request Json : 
```
{
    "name":"Sample Event7",
    "location":"Tokyo, Minato",
    "timeZone":"Asia/Tokyo",
    "startTime":"2021-01-11 10:00",
    "endTime":"2021-01-13 11:00"
}
```

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": null
}
```

### 2.   Get All event list

METHOD  :	GET

URL	    : `http://localhost:8080/event/list`

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": {
        "eventList": [
            {
                "eventId": 1,
                "name": "Sample Event1",
                "location": "Tokyo, Ruppongi",
                "startTime": "2021-01-12 12:00:00",
                "endTime": "2021-01-12 15:00:00"
            },
            {
                "eventId": 2,
                "name": "Sample Event2",
                "location": "Tokyo, Oshiage",
                "startTime": "2021-01-12 14:00:00",
                "endTime": "2021-01-12 15:00:00"
            },
            {
                "eventId": 3,
                "name": "Sample Event3",
                "location": "Tokyo, Shinjuku",
                "startTime": "2021-01-12 14:00:00",
                "endTime": "2021-01-12 15:00:00"
            },
            {
                "eventId": 4,
                "name": "Sample Event4",
                "location": "Tokyo, Shinjuku",
                "startTime": "2021-01-13 14:00:00",
                "endTime": "2021-01-13 15:00:00"
            },
            {
                "eventId": 5,
                "name": "Sample Event5",
                "location": "Tokyo, Shibuya",
                "startTime": "2021-01-13 10:00:00",
                "endTime": "2021-01-13 11:00:00"
            }
        ]
    }
}
```

### 3.   Select event list  participating by a single user

METHOD  :	GET

URL	    : `http://localhost:8080/event/user-event/list?email=test3@test.com`

#### Parameter
| Name | Data Type|
| ------| ------|
|email | String | 


Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": {
        "eventList": [
            {
                "eventId": 1,
                "name": "Sample Event1",
                "location": "Tokyo, Ruppongi",
                "startTime": "2021-01-12 12:00:00",
                "endTime": "2021-01-12 15:00:00"
            }
        ]
    }
}
```

### 4.   User list for a event

METHOD  :	GET  

URL	    : `http://localhost:8080/event/user-list/1`

#### Path variable
| name | Data Type|
| ------| ------|
|eventId | long |

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": {
        "userList": [
            {
                "userId": 2,
                "name": "test1 test1",
                "email": "test1@test.com",
                "createdAt": "2021-01-12 18:51:51"
            },
            {
                "userId": 3,
                "name": "test2 test2",
                "email": "test2@test.com",
                "createdAt": "2021-01-12 18:51:57"
            },
            {
                "userId": 4,
                "name": "test3 test3",
                "email": "test3@test.com",
                "createdAt": "2021-01-12 18:52:03"
            }
        ]
    }
}
```

### 5.  Delete an event

METHOD  :	PUT

URL	    : `http://localhost:8080/event/delete`

Comment : Soft deletiong implemented. delete_flg is set true when event is deleted. Default value of delete_flg is false

Request Json : 
```
{
    "eventId":"6"
}
```

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": null
}
```

### 6.  Update an event

METHOD  :	PUT

URL	    : `http://localhost:8080/event/update`

Request Json :
```
{
    "eventId" : 8,
    "name" : "Sample Project8",
    "startTime" : "2021-01-13 15:00",
    "endTime" : "2021-01-13 16:00"
}
```

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": null
}
```

### User API
___

### 1.	Sign Up a user

METHOD  :	POST

URL	    : `http://localhost:8080/user/sign-up`

Comment :  To test the API, content-type should be "application/json".

Request Json :
```
{
    "name" : "test8 test8",
    "email" : "test8@test.com",
    "password" : "16d7a4fca7442dda3ad93c9a726597e4"
}
```

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": null
}
```

### 2.   Get single user info

METHOD  :	GET

URL	    : `http://localhost:8080/user/info?email=test3@test.com`

#### Parameter
| Name | Data Type|
| ------| ------|
|email | String | 

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": {
        "userId": 4,
        "name": "test3 test3",
        "email": "test3@test.com",
        "password": "16d7a4fca7442dda3ad93c9a726597e4",
        "createdAt": "2021-01-12 18:52:03",
        "deleteFlg": "0"
    }
}
```

### 3.   Add a user to an event

METHOD  :	POST

URL	    : `http://localhost:8080/user/add-event`

Request Json :
```
{
    "eventId" : 3,
    "email" : "test5@test.com"
}
```

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": {
        "eventId": 3,
        "email": "test5@test.com"
    }
}
```

### 4.   Remove user from event

METHOD  :	PUT

URL	    : `http://localhost:8080/user/remove/event`

Comments : Soft deletion implemented. delete_flg is set to true.

Request Json :
```
{
    "email" :"test1@test.com",
    "eventId" : 1
}
```

Response Json :
```
{
    "code": 200,
    "errCode": null,
    "messages": [
        "OK"
    ],
    "errItems": null,
    "result": null
}
```

## Note
1.  Cover first 2 points in "Nice to have" section.
2.	To keep simplicity auto increment number is used as id starting from 1. In use case, UUID might be used for ID.
3.	Trying to keep simplicity of code to follow K.I.S.S
4.	DateTime format “yyyy-MM-dd hh:mm” is implemented. 

#### Thank you very much for your time!!



