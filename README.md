<!-- PROJECT LOGO -->
<br />
<p align="center">
  <img src="http://jasontokarski.com/wp/Images/JWTKEY.png" alt="drawing" width="250px" height="100px"/>
  <br />
  <a href="https://github.com/jasontokarski/JwtProvider>
  </a>

  <p align="center">
    A microservice based Spring Boot API
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

[![Product Name Screen Shot][product-screenshot]](https://example.com)

Available Capabilities:
* The ability to stand-up a JSON Web Token microservice on any platform running JDK.
* A RESTful based approach to generating tokens through HTTP requests.
* Validating the authenticity of tokens.

Of course, no one template will serve all projects since your needs may be different. So I'll be adding more in the near future. You may also suggest changes by forking this repo and creating a pull request or opening an issue.

A list of commonly used resources that I find helpful are listed in the acknowledgements.

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

Here is an example demonstration of this web application through Postman.

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

