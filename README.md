<!-- PROJECT LOGO -->
<h3 align="center">JSON Web Token Service</h3>
<p align="center">
  A microservice based Spring Boot API
  <img src="http://jasontokarski.com/wp/Images/JWTKEY.png" alt="drawing" width="175px" height="75px"/>
  <br />
  <a href="https://github.com/jasontokarski/JwtProvider">
  </a>

  <p align="center">
    <br />
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)



<!-- ABOUT THE PROJECT -->
## About The Project

Available Capabilities:
* The ability to stand-up a JSON Web Token microservice on any platform running JDK.
* A RESTful based approach to generating tokens through HTTP requests.
* Validating the authenticity of tokens.

This project was built using JDK 11, please install this version or a later one. 
You will also need to have gradle installed on your system in order to build the service.

This is a fast, lightweight, secure service for authenticating users for a front end login page or allowing access to downstream applications.

### Built With
This application was written in Java.
* [Spring Boot](https://https://spring.io/guides/gs/spring-boot/)
* [Hibernate](https://hibernate.org/)
* [MySQL](https://www.mysql.com/)
* [Gradle](https://gradle.org/)

<!-- GETTING STARTED -->
## Getting Started

JwtProvider uses Gradle for depedency management and building the application. This gradle project also has a set of JUnit test cases and Jacoco coverage reports.
This application will require a MySQL or other RDBMS SQL server where pairs of users and their API keys are stored to prevent unauthorized access to the JWT generator.
After starting the Spring Boot application, two endpoints are available. One for token generation and another for token validation.

### Prerequisites

MySQL Server
Download [MySQL Server 8](https://dev.mysql.com/downloads/mysql/)
* Login to your MySQL database and run the following commands
```mysql
CREATE DATABASE jwt_provider;
CREATE USER 'authuser'@'%' IDENTIFIED BY 'yourpasswordhere';
USE jwt_provider;
GRANT ALL PRIVILEGES ON jwt_provider.* TO 'authuser'@'%';
```

* Insert a user and api_key value within the Hibernate generated auth_entity table.

### Installation

1. Clone the repository to a local destination.
```sh
git clone https://github.com/jasontokarski/JwtProvider.git
```
2. Navigate to the main provider directory and build the project.
```sh
gradle clean build
```
3. Execute the packaged JAR application (Ensure JDK 11+ is installed on your system)
```
java -jar provider-0.0.1-SNAPSHOT.jar
```



<!-- USAGE EXAMPLES -->
## Usage

**Request:**
```json
POST /jwt/request
Accept: application/json
Content-Type: application/json

{
    "user":"testuser123",
    "apiKey":"apikey408374"
}
```
**Successful Response:**
```json
HTTP/1.1 200 OK
Server: localhost
Content-Type: application/json

eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEyMyIsImlhdCI6MTYwMzA1ODYzOSwiaXNzIjoiWVd4cWEzTmtabXBzWVhOa2EyRXdhbVE1WmpCcVlXWXdPV293Wm1Fd09XcG1NRGxxTUdGaCIsImV4cCI6MTYwMzE0NTAzOX0.Zse87DkFCxk0FrnzHBJ8eemwBYH-N2lDExf48bSev0Y
```

**Request:**
```json
POST /jwt/validate
Accept: application/json
Content-Type: application/json

{
"jwt":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWludXNlciIsImlhdCI6MTYwMjcyNDIxNSwiaXNzIjoiWVd4cWEzTmtabXBzWVhOa2EyRXdhbVE1WmpCcVlXWXdPV293Wm1Fd09XcG1NRGxxTUdGaCIsImV4cCI6MTYwMjgxMDYxNX0.44RQJ-S6DSaWkuYJZc7UJEv8P5QC_PDPKoVTdojsF20"
}
```
**Successful Response:**
```json
HTTP/1.1 200 OK
Server: localhost
Content-Type: application/json
```

**Failed Response:**
```json
HTTP/1.1 401 Unauthorized
Server: localhost
Content-Type: application/json
``` 

_For more examples, please refer to the [Documentation](https://jasontokarski.com)_



<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/jasontokarski) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


<!-- CONTACT -->
## Contact

Jason Tokarski - [jasontokarski.com](https://jasontokarski.com)

Project Link: [github.com/jasontokarski/JwtProvider](https://github.com/jasontokarski/JwtProvider)

<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* [JJWT Library](https://github.com/jwtk/jjwt)

