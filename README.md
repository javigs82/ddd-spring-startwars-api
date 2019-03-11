# Domain Driven Design - Start Wars API
This project aims to develop restful endpoints about start wars entities provided by [swapi](https://swapi.co/) while applying principles of Domain Driven Design.

This project is written in java8 under spring-boot web framework using futures to complete tasks in a asynchronous way.

## Requirements

**Main** requirements of this projects are:
 - Develop user stories
 - DDD framework to design the software
 - SOLID principles is a must
 - Data will never be stored, so avoid usage of persistence mechanisms is a must

### User Stories
#### 1. Searching people and startships

**AS A** Star Wars fan  
**I WANT** to see the information about PEOPLE and STARSHIPS being able to sort the information and search by too  
**SO THAT I** can have fun searching about Star Wars information.


##### Acceptance Criteria

 - Web application to display information from SWAPI (https://swapi.co/).
 - The Web application should read and display information in a web browser, from the API endpoints:
     - people
     - starships
 - The Web application should allow to **sort the data by "name" and "created" fields, in descending and ascending order
  both cases**.
 - The mechanism for sorting should be designed following the "Open-closed" principle.
 - Visualize the data using `json` format.

## Domain Driven Design

This web service is designed under DDD paradigm. Domain-driven design is not a technology or a methodology. 
DDD provides a structure of practices and terminology for making design decisions that focus and accelerate software 
projects dealing with complicated domains.


### Layers in DDD

The layers are a logical artifact, and are not related to the deployment of the service. 
They exist to help developers manage the complexity in the code.

You want to design the system so that each layer communicates only with certain other layers.

Please take a look into following image to have a global vision of how the framework addresses layer design

![DDD layered arch](http://www.joaopauloseixas.com/howtodoit.net/wp-content/uploads/2011/04/Domain-Driven-Design-Overview-of-a-Layered-Architecture.png)

#### Domain Model Layer
Responsible for representing concepts of the business, information about the business situation, 
and business rules. State that reflects the business situation is controlled and used here, even though the technical 
details of storing it are delegated to the infrastructure. This layer is the heart of business software.

#### Application Layer 
Defines the jobs the software is supposed to do and directs the expressive domain objects to work out problems. 
The tasks this layer is responsible for are meaningful to the business or necessary for interaction with the application
layers of other systems. This layer is kept thin. It does not contain business rules or knowledge, but only coordinates
tasks and delegates work to collaborations of domain objects in the next layer down. It does not have state reflecting
the business situation, but it can have state that reflects the progress of a task for the user or the program.

#### Infrastructure Layer 
That is how the data that is initially held in domain entities (in memory) is persisted in databases or another 
persistent store.

In accordance with Persistence and Infrastructure Ignorance principles, the infrastructure layer must not “contaminate” 
the domain model layer. You must keep the domain model entity classes agnostic from the infrastructure that you use to 
persist data (EF or any other framework) by not taking hard dependencies on frameworks.

 
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing 
purposes. See deployment for notes on how to deploy the project on a live system.


To get the code:
-------------------

Clone the repository:

    $ git clone https://github.com/javigs82/ddd-startwars-api.git

If this is your first time using Github, review http://help.github.com to learn the basics.

## Prerequisites

What things you need to install the software and how to install them
* JDK 1.8
* Gradle
* Maven

## Installing

A step by step series of examples that tell you have to get a development env running

```
gradle clean build
```

## Running the tests

Explain how to run the automated tests for this system

```
gradle test
```

## Deployment

Add additional notes about how to deploy this on a live system

```
java -jar build/libs/startwars-api-1.0.0-SNAPSHOT.jar
```

with an expected output like:
```bash
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.5.RELEASE)
...
...
...
2019-03-10 16:26:54.996  INFO 27752 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-03-10 16:26:55.003  INFO 27752 --- [           main] m.j.startwars.SpringApplicationBuilder   : Started SpringApplicationBuilder i

```

## References and Sources

- [Design a DDD-oriented microservices](https://docs.microsoft.com/en-us/dotnet/standard/microservices-architecture/microservice-ddd-cqrs-patterns/ddd-oriented-microservice)
- [Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture)
- [DDD](https://pehapkari.cz/blog/2018/03/28/domain-driven-design-services-factories/)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
