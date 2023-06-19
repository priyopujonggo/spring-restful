# User API Spec

## Register User

Endpoint : POST /api/users


Request Body : 

```json
{
    "email" : "priopujonggo@gmail.com",
    "password" : "rahasia",
    "name" : "Priyo Pujonggo"
}
```
Response Body (Success) :
```json
{
    "data" : "ok"
}
```
Response Body (Failed) :
```json
{
    "error" : "email must not blank, ???"
}
```

## Login User

Endpoint : POST /api/auth/login


Request Body : 

```json
{
    "email" : "priopujonggo@gmail.com",
    "password" : "rahasia",
}
```
Response Body (Success) :
```json
{
    "data" : {
        "token" : "TOKEN",
        "expiredAt" : 23242452325425 //miliseconds
    }
}
```
Response Body (Failed, 401) :
```json
{
    "error" : "Username or password wrong"
}
```

## Get User

Endpoint : GET /api/users/current

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
{
    "data" : {
        "email" : "priopujonggo@gmail.com",
        "name" : "Priyo Pujonggo"
    }
}
```
Response Body (Failed, 401) :
```json
{
    "error" : "Unauthorized"
}
```

## Update User

Endpoint : PATCH /api/users/current

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body : 
```json
{
    "name" : "Priyo Pujonggo", //put if only update name
    "password" : "new password" // put if only update password
}
```


Response Body (Success) :
```json
{
    "data" : {
        "email" : "priopujonggo@gmail.com",
        "name" : "Priyo Pujonggo"
    }
}
```
Response Body (Failed, 401) :
```json
{
    "error" : "Unauthorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :
```json
{
    "data" : "OK"
}
```