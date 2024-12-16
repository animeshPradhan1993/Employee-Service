# locally compiling the application

navigate to the project root directory and run ./mvnw clean install
# running the app locally

navigate to the project root directory and run ./mvnw spring-boot:run

The endpoints can be accessed at localhost:8081 this setting can be  modified by changing the application port
in application.yaml

All the endpoints are secured with basic authentication.
A user with username: '1' and password 'admin' is already created with role as ADMIN please use this user to start with.
supplying basic auth is mandatory to access any api in this application.

The default password of any employee is the generated id unless password is provided in the post request.

To start following roles are created
Role ID, Role name
1,        ADMIN
2,        USER
3,        MANAGER

The swagger can be found at src/main/resources/swagger/Employee-Service.yaml

Jacoco is used to generate the coverage reports.
Coverage reports(index.html) can be accessed in the site folder inside the generated folder

To connect to the Employee database application basepath can be configured using the property employeeDatabase:
basePath:application.yaml
