# lti-training-app
 Movie Multiplex App (LTI Training)
<br/>
Microservice[movie-discovery]: Eureka Server where all microservices will be registered.<br/>
Microservice[movies]: Eureka Client with the name of "movies" will register itself to "movie-discovery" server.<br/>
Microservice[multiplex]: Eureka Client with the name of "multiplex" will register itself to "movie-discovery" server.<br/>
Microservice[api-gateway]: Spring Cloud Gateway which handles all request from front-end. It contains of Spring Security for admin login and Cloud Gateway implementation. All requests from the front-end go through "api-gateway" to communicate with any other microservice.<br/>
<br/>
Admin Username: admin<br/>
Admin Password: 12345<br/>
