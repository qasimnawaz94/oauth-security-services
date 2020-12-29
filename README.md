# Spring Boot, OAuth 2.0 , Swagger-UI 2

## To Start ?

```
Execute the \oauth-security-services\src\main\resources\script.sql into database before starting.
use below command to start
$ mvn spring-boot:run
```

## Swagger-UI
* After starting the application OPEN Swagger-UI at(http://localhost:8080/oauth-services/swagger-ui.html#/)


## User Data

```
   superadmin | Secure20
```

## APPLICATION CONFIGURATION
* Edit the configuration in the file [application.yml](/oauth-security-services/src/main/resources/application.yml)


## Side Note
```
In this module each user permissions can be changed dynamically, role is not binded directly with privileges.
```

## DB_SCHEMA
![DB_SCHEMA](https://raw.githubusercontent.com/qasimnawaz94/oauth-security-services/master/src/main/resources/IMAGES/DB_SCHEMA.PNG)


