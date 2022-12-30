# Assembly Votes(MicroServices)🚧 - In development

## API to creating voting session

## Desenvolved with:
    - SpringBoot 3.0.0
    - Maven 3.8.6
    - Java 17
    - Docker
    - RabbitMQ

## About
Assembly Votes is an API under development, focused on facilitating the creation of voting sessions in polls created by users.
In this version the API works in two instances with shared database

- Circuit break
- Event sourcing
- Spring Actuator with Prometheus and Grafana

## In constrution :construction:
- Remake shared functions 
- Implements new admin resources
 
## Start Server 🌐
### In development

# Project 🚧
## This project has the MVC pattern, having controllers, services and repositories following the demand of the object

 
    ├── user-api                  # UserApi - User and Survey functions
    ├── session-api               # SessionApi - Session, Votes and AllowedUser functions
    └── docker-compose.yml        # Docker - config and start RabbitMQ and PostgreSQL

#### References
 - https://www.baeldung.com/
 - https://docs.railway.app/
