# MailTrigger

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

MailTrigger is a service that allows you to asynchronously send emails using an HTTP API. Emails are represented as
channels and generated from Markdown templates which are kept in the same source code repository as the software itself.

Hence, email templates can be managed using [GitHub](https://github.com/) / [GitLab](https://about.gitlab.com/)
(or the like) in-browser [Markdown](https://daringfireball.net/projects/markdown/) editors.

Alternatively, you can configure a custom template directory for loading email templates from the filesystem.

## Getting Started

### Prerequisites

* [Java 17](https://openjdk.org/projects/jdk/17/)
* [Maven](https://maven.apache.org/)

### Running the app in dev mode

Run ```mvn spring-boot:run``` from the command line.

## Running the tests

Run ```mvn clean install``` from the command line.

## Deployment

You can run the application by using the standard Spring Boot deployment mechanism (see these three articles for more
information on Spring Boot deployment techniques and alternatives:
[Deploying Spring Boot Applications](https://spring.io/blog/2014/03/07/deploying-spring-boot-applications),
[Running your application](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)
,
[Installing Spring Boot applications](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html)):

```java -jar target/mailtrigger-0.0.2-SNAPSHOT.jar```

The application then should be available under [http://localhost:8080](http://localhost:8080)

### Via Docker

Alternatively, you can run the application in a Docker container
(using [the official MailTrigger Docker image](https://hub.docker.com/r/bjoernkw/mailtrigger)):

```docker run -p 8080:8080 bjoernkw/mailtrigger```

In the project root folder, there's an example `Dockerfile` that can be used to build the image, as well as a sample
`docker-compose.yml` with an example configuration. The latter can be used to run the application via the usual Docker
Compose command:

```docker-compose up```

That sample `docker-compose.yml` configures a custom template directory.

## Configuration

These environment variables are required for running MailTrigger:

* ```SMTP_HOST```
* ```SMTP_PORT```
* ```SMTP_USERNAME```
* ```SMTP_PASSWORD```

## Sending an email

This HTTP call will load a template named ```test_channel.md``` and replace any placeholders with the replacements
provided in the HTTP request body:

```curl -d '{"TO": "test@test.com", "FIRST_NAME": "Jack"}' -H 'Content-Type: application/json' -X POST http://localhost:8080/api/v1/sendMail/test_channel```

## REST API Documentation

Once the app is started the REST API documentation will be available under
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot/)
* [Maven](https://maven.apache.org/)
* [Markdown](https://daringfireball.net/projects/markdown/)

## License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Authors

* **[Björn Wilmsmann](https://bjoernkw.com)**
