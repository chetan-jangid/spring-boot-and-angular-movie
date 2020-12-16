# Spring Boot and Angular Movie Application
 Movie Multiplex App (LTI Training)<br/><br/>
 <b>Backend</b><br/>
 List of all microservices:<br/>
<b>movie-discovery:</b> Eureka Server where all microservices will be registered.<br/>
<b>movies:</b> Eureka Client with the name of "movies" will register itself to "movie-discovery" server.<br/>
<b>multiplex:</b> Eureka Client with the name of "multiplex" will register itself to "movie-discovery" server.<br/>
<b>api-gateway:</b> Spring Cloud Gateway which handles all request from front-end. It contains of Spring Security for admin login and Cloud Gateway implementation. All requests from the front-end go through "api-gateway" to communicate with any other microservice.<br/><br/>
Admin Username: admin<br/>
Admin Password: 12345<br/>
<br/>
<b>Frontend</b><br/>
 UI Library: [ng-zorro-antd](https://ng.ant.design/docs/introduce/en)<br/>
 Date Library: [moment](https://momentjs.com/)<br/>
