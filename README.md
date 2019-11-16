# Shopping Cart Web - Spring Boot - Angular

## About

This is a demo project for practicing Spring + Angular. The idea was to build some basic shopping cart web app.

It was made using **Angular**, **Spring Boot**, **Spring Security**, **Spring Data**, **Mongo DB** and **Docker**. 
Database is NoSQL **MongoDB**.

There is a login functionality included.

Users can shop for products. Each user has his own shopping cart.

## Configuration

### Configuration Files

#### API
Folder **api/src/resources/** contains config files for **Shopping Cart** Spring Boot application.

#### WEB
Folder *view/src/enviroments/enviroment.ts** contains config file with the address of API **Shopping Cart**.


## How to run (API)

There are several ways to run the application. You can run it from the command line with included Maven or Docker. 

### Mongo

To run the application is necessary MongoDB database installed, if you don't have you can install using docker, like this:

```bash
$ docker pull mongo
```

The command below list the MongoDB image created:

```bash
$ docker ps -a |grep mongo
b5edf3af5290        mongo     "docker-entrypoint.s…"   53 minutes ago    Exited (0) 11 minutes ago                                                       shopping-cart-mongodb
80663d05925a        mongo     "docker-entrypoint.s…"   4 days ago        Exited (0) About an hour ago                                                      focused_blackburn
```

If you have more than one, choose the id of one and start the image:

```bash
$ docker start 80663d05925a
```

### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java and Maven installed:

```bash
$ java -version
java version "1.8.0_181"
Java(TM) SE Runtime Environment (build 1.8.0_181-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.181-b13, mixed mode)
```

```bash
$ mvn -v
Apache Maven 3.6.2 (40f52333136460af0dc0d7232c0dc0bcf0d9e117; 2019-08-27T12:06:16-03:00)
Maven home: /Users/manny/Java/apache-maven-3.6.2
Java version: 1.8.0_181, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.15.1", arch: "x86_64", family: "mac"
```

#### Using the Maven Plugin

The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. 
Applications run in an exploded form, as they do in your IDE. 
The following example shows a typical Maven command to run a Spring Boot application:

Folder **api/**
 
```bash
$ mvn spring-boot:run
``` 

#### Using Executable Jar

To create an executable jar run:

```bash
$ mvn clean package
``` 

To run that application, use the java -jar command, as follows:

```bash
$ java -jar target/shopping-cart-0.0.1-SNAPSHOT.jar
```

To exit the application, press **ctrl-c**.

## How to run (WEB)

To run the application is necessary Node Js installed, if you don't have you can install visiting `https://nodejs.org/en/download/` and downloading the Node.js:

Folder **view/**

```bash
$ npm install
$ npm install -g @angular/cli
$ ng serve
```

Once the app starts, go to the web browser and visit `http://localhost:4200/`

Admin username: **admincart@gmail.com**

Admin password: **admin@123**

### Docker (API)

It is possible to run **shopping-cart-api** using Docker:

Folder **api/**

Build Docker image:
```bash
$ mvn clean package -Pdocker -DskipTests=true
$ docker-compose build
```

Run Docker container:
```bash
$ docker-compose up
```

### Docker (WEB)

It is possible to run **shopping-cart-web** using Docker:

Folder **view/**

Build Docker image:
```bash
$ docker build -t shopping-cart-view:dev .
```

Run Docker container:
```bash
$ docker run -v ${PWD}:/app -v /app/node_modules -p 4200:4200 --rm shopping-cart-view:dev
```

## Docker 

* **api/Dockerfile** and **web/Dockerfile**  - Docker build file for executing shopping-cart Docker images. 
Instructions to build artifacts, copy build artifacts to docker image and then run app on proper port with proper configuration file.
