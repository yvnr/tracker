This repository is Named as Tracker Service and is one of the microservices of the **Campus Job Board** Project for CS520.
This Microservice is built using Java-Spring Boot Framework and we have used Maven build tool. 

Run Instructions: 

Install Maven in the local system. Verify by running "mvn" in the terminal. 
The system should have Java installed with atleast 1.8 version. Verify the java version by running *java -version* command in the terminal.

**Run all the below Commands in tracker/ folder**
+
* Run *mvn clean install*  command in the terminal to pull all the required dependencies.
* Run *mvn compile* to compiel the changes.
* Run *mvn javadoc:javadoc* to generate Javadoc. The generated javadoc will be in *tracker/target/site/apidocs* folder. Open index.html file in this folder.
* Run *mvn test* to run Unit test cases. 
* Run *mvn spring-boot:run* command in the terminal to start the application. 
The Microservice runs on 8080 port and hence make sure that no other application is running in this port.

**Alternatively**
You can import the application into an IDE like Eclipse(I personally use this). Import tracker/ as an existing Maven Project. 
you can run the application by rightClicking on the tracker/src/main/java/TrackerApplication.java  and click on Run As -> Java Application.

You can Also run the Unit Test Cases by Right Clicking on the tracker/src/test/java package and Run As-> JUnit Test. 
You can Also see the Coverage Report by Right Clicking on the tracker/src/test/java package and Coverage As -> JUnit test. This should show detailed analysis of lines and branches covered


**DataBase**  The Microservice requires connection to PostgreSQL Database. We have fixed the credential of the database to be : username: postgres and password: lokesh123. In case you need to change these credentials, change the following properties in tracker/src/main/resources/application.properties file:

*The Current values are:*
* spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
* spring.datasource.username=postgres
* spring.datasource.password=lokesh123 

*The values shoudl be in the below format if you want to update the credentials:*
* spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
* spring.datasource.username=<username>
* spring.datasource.password=<password> 

