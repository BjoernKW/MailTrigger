# MailTrigger

MailTrigger is a service that allows you to asynchronously send emails using an HTTP API. Emails are represented as
channels and generated from Markdown templates which are kept in the same source code repository as the software itself.

Hence, the email templates can be managed using [GitHub](https://github.com/) / [GitLab](https://about.gitlab.com/)
(or the like) [Markdown](https://daringfireball.net/projects/markdown/) editors.

## Getting Started

### Prerequisites

* [Java 9](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)
* [Maven](https://maven.apache.org/)
* a servlet container such as [Apache Tomcat](https://tomcat.apache.org/) or [Jetty](https://www.eclipse.org/jetty/)

### Running the app in dev mode

Run ```mvn spring-boot:run``` from the command line.

## Running the tests

Run ```mvn clean install``` from the command line.

## Deployment

The application can be deployed as a standard WAR file in any servlet container. Please follow these steps:

1. Run ```mvn clean install``` from the command line.
2. Copy the generated WAR file (e.g. ```mailTrigger-0.0.x-SNAPSHOT.war```) from the ```target```
folder in the project directory to the ```webapps``` directory of your servlet container.
3. *Optional*: Restart your servlet container.
4. The application should be available under ```http://HOST:PORT/NAME_OF_THE_WAR_FILE```,
e.g. ```http://localhost:8080/mailTrigger-0.0.x-SNAPSHOT```

## Sending an email

This HTTP call will load a template named ```test_channel.md``` and replace any placeholders with the replacements
provider in the HTTP request body:

```curl -d '{"TO": "test@test.com", "FIRST_NAME": "Jack"}' -H 'Content-Type: application/json' -X POST http://localhost:8080/api/v1/sendMail/test_channel```

## REST API Documentation

Once the app is started the REST API documentation is be available under ```/swagger-ui.html```, e.g.
```http://localhost:8080/mailTrigger-0.0.x-SNAPSHOT/swagger-ui.html```

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Maven](https://maven.apache.org/)
* [Markdown](https://daringfireball.net/projects/markdown/)

## Authors

* **[Björn Wilmsmann](https://bjoernkw.com)**
