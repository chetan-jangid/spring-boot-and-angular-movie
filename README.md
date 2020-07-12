# lti-training-app
 Movie Multiplex App (LTI Training)

Microservice[movie-discovery]: Eureka Server where all microservices will be registered.
Microservice[movies]: Eureka Client with the name of "movies" will register itself to "movie-discovery" server.
Microservice[multiplex]: Eureka Client with the name of "multiplex" will register itself to "movie-discovery" server.
Microservice[api-gateway]: Spring Cloud Gateway which handles all request from front-end. It contains of Spring Security for admin login and Cloud Gateway implementation. All requests from the front-end go through "api-gateway" to communicate with any other microservice.

Admin Username: admin
Admin Password: 12345
