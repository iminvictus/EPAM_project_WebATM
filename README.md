# JAVA BTD 08 Uranus 

## Overview
An automated teller machine (ATM) is an electronic banking outlet that allows customers to complete basic transactions without the aid of a branch representative or teller.
Anyone with a credit card or debit card can access cash at most ATMs.

ATMs are convenient, allowing consumers to perform quick self-service transactions such as deposits, cash withdrawals, bill payments, and transfers between accounts.

## Technology stack
Java 11.0.2, Apache Maven 3.8.2, Apache Tomcat 10.0.10, PostgreSQL 11.13, Servlets, JSP, Docker.

## Guidelines

How to run the ATM Service locally

1. Clone this repository into your machine: `https://git.epam.com/epm-lstr/epm-lstr-ura/btd-atm-service.git`
2. Add or edit your configuration on Intellij IDEA:
   - enter the name of your configuration
   - specify the path to your Tomcat Server
   - specify the deployment directory to `/webapp`
   - specify the context path to `/`
   - specify the server port to `8080`
3. Start the application and follow the link http://localhost:8080/

To work with service correctly you have to create your own database in PostgreSQL.

4. Open your QueryTool or SQL Shell (psql) and create database with name `atm_service`
5. Connect to `atm_service` database and paste table schemas from file `10-init-db.sql`.
6. To insert some test data paste commands from file `20-fill-test-data.sql` 

How to deploy ATM service with Docker locally:
1. Install Docker Desktop. Check if Docker service installed properly, run command prompt anywhere and type `docker version` or `docker run hello-world`.
2. `docker-compose.yml` file in project repo serves for deploying two Docker containers, one with ATM application and another with PostgreSQL database. It needs `.war` file in `/target` to deploy Tomcat application and `.sql` files in `/src/main/resources/` to create tables and fill them with test data. Also it creates `postgres-data` folder after deployment.
3. In `/src/main/resources/application.properties` file `postgres.localhost` parameter should be set according to database container name and port, which are set on `container_name` and `ports` parameters on `services: db:` and `services: db: ports` sections of the `docker-compose.yml`.
4. `docker-compose.yml` uses `.war` file to deploy application on Tomcat server. The file should be up-to-date and correctly configured with `application.properties` by Maven. Filename should be `ROOT.war` and it should be placed in `/target`. Still, the path to `.war` file can be changed in `docker-compose.yml`.
5. Run command prompt in folder where the `docker-compose.yml` is and type `docker-compose up -d`. `-d` flag means detached mode, so still you can use console after deployment. Go to http://localhost/ to check if the app is running.
6. Use `docker stop CONTAINER_NAME` to stop running container and `docker rm CONTAINER_NAME` to remove container.

How to deploy ATM service on remote server:
1. Connect to remote server via SSH, e.g. using PUTTY. When you are logged in, remember the server's IP-address.
2. Clone project repository onto server machine - `git clone https://git.epam.com/epm-lstr/epm-lstr-ura/btd-atm-service.git`
3. Check that `postgres.localhost` parameter is set accordingly to the PostgreSQL container name and port, which are described in `docker-compose.yml`.
4. Configure `.war` file using Maven commands: `mvn clean`, `mvn verify`, `mvn install`. Add `sudo` at the beginning of command if it causes errors.
5. Run `docker compose up -d` command. Add `sudo` at the beginning of command if it causes errors.
6. Connect to app via server IP-address you have found when connected to server.
7. Use `docker stop CONTAINER_NAME` to stop running container and `docker rm CONTAINER_NAME` to remove container.

## Functionality

ATM main page:
   - request is GET-method on http://localhost:8080/ 
   - response is JSP page showing general functionality of ATM and user data

ATM deposit page:
- request is GET-method on http://localhost:8080/deposit
- response is JSP page providing the opportunity to deposit money into the user's account

ATM withdraw page:
- request is GET-method on http://localhost:8080/withdraw
- response is JSP page providing the opportunity to withdraw money from the user's account

ATM transaction history page:
- request is GET-method on http://localhost:8080/history 
- response is JSP page providing the opportunity to view the entire transaction history of the user
