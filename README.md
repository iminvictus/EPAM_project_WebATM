# JAVA BTD 08 Uranus 

## Overview
An automated teller machine (ATM) is an electronic banking outlet that allows customers to complete basic transactions without the aid of a branch representative or teller.
Anyone with a credit card or debit card can access cash at most ATMs.

ATMs are convenient, allowing consumers to perform quick self-service transactions such as deposits, cash withdrawals, bill payments, and transfers between accounts.

## Technology stack
Java 11.0.2, Apache Maven 3.8.2, Apache Tomcat 10.0.10, PostgreSQL 11.13, Servlets, JSP.

## Guidelines

How to run the ATM Service locally

1. Clone this repository into your machine: `https://git.epam.com/epm-lstr/epm-lstr-ura/btd-atm-service.git`
2. Add or edit your configuration on Intellij IDEA:
   - enter the name of your configuration
   - specify the path to your Tomcat Server
   - specify the deployment directory to `/webapp`
   - specify the context path to `/atm`
   - specify the server port to `8080`
3. Start the application and follow the link http://localhost:8080/atm

To work with service correctly you have to create your own database in PostgreSQL.

4. Open your QueryTool or SQL Shell (psql) and create database with name `atm_service`
5. Connect to `atm_service` database and paste table schemas from file `V1_init_db.sql`.
6. To insert some test data paste commands from file `fill_test_data.sql`
    
And also, when you work locally, the service needs to understand with which user and which database to work with.
In application service `ApplicationService` you have to specify your host, username, password and database name.
For example:
```java
DatabaseConnectionManager databaseConnectionManager = new DatabaseConnectionManager("localhost", "postgres", "admin", "atm_service");
```

## Functionality

ATM status page will show general information about ATM users:
   - request is GET-method on http://localhost:8080/atm/service (without parameters)
   - response is JSP page showing information about ATM users