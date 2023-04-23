# glady

For building and running the application you need:

    JDK 11

# Core principals:
* Module must remain very simple: Spring Boot application using Web MVC starter pack.

# Technical details:

* We use all default configuration available:
  * `@SpringBootApplication` annotation allows auto configuration and detection of annotations located in same package and sub-packages.
  * `application.properties` is loaded by default by Spring Boot if detected.
    ``

### Launch Unit Tests:
`mvn clean verify`

### Project Install

* Create the database user to access the database from the spring boot application.
* Set a password for the created user, which was we have using for accessing the application.
* When the application is launched, the database will be initiated with the tables and data previously defined in the resources/sql/insert_data.sql file, in order to be able to directly perform functional tests from a Rest client such as postman

### Launch Spring Boot:
`mvn spring-boot:run` or `mvn spring-boot:run -Dspring-boot.run.fork=false` for debug mode

# Specific management rule:

In order to avoid inaccuracies, amounts and account balance are set to two numbers after the decimal point

# Service documentation
* Get an existing employee amount example( get employee's amount nominal case) : 
curl --location --request GET 'http://localhost:8080/user?employeeId=1'
* Trying to get a not existing employee amount :
curl --location --request GET 'http://localhost:8080/user/balance?employeeId=8'
* Trying to get an existing employee amount, but employee is not attached to any company :
  curl --location --request GET 'http://localhost:8080/user/balance?employeeId=4'


* Company make a gift deposit to attached employee scenario (deposit nominal case):
curl --location --request PUT 'http://localhost:8080/deposit/gift?employeeId=2&amount=3' \
  --header 'companyId: 1'
** Check that the employee's with id "2" balance has increased by 3:
curl --location 'http://localhost:8080/user/balance?employeeId=2'
** Company make a meal deposit to attached employee scenario (deposit nominal case):
  curl --location --request PUT 'http://localhost:8080/deposit/meal?employeeId=2&amount=5' \
  --header 'companyId: 1'
** Check that the employee's with id "2" balance has increased and is 8 now (3+5):

* Expired gift test case :
** According to the initialization data present in the file, display the total amount of the gift and meal paid by the company at "1" to the employee "1", check that the amount of the gift paid on '2020-12-31 ' does not fit into the sum. The endpoint only returns 290 instead of 380
  curl --location 'http://localhost:8080/user/balance?employeeId=1'
  
  
  
  
  Test coverage :
  
  ![image](https://user-images.githubusercontent.com/61843223/233871581-395dc492-5a9a-4532-9578-1173ad1e7dd7.png)
