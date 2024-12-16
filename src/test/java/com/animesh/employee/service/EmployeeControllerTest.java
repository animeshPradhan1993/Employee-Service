package com.animesh.employee.service;

import com.animesh.employee.service.controller.EmployeeController;
import com.animesh.employee.service.exception.handling.ErrorDetails;
import com.animesh.employee.service.resource.Employee;
import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.EmployeeControllerApi;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.charset.Charset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private EmployeeController controller;
    @MockitoBean
    private EmployeeControllerApi employeeApi;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Test
    public void testEmployeeCreation() throws ApiException {

        ResponseEntity<Employee> resultCreatedEmployee = testRestTemplate.exchange("/employees", HttpMethod.POST, createEmployeeEntity(), Employee.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    public void testEmployeeCreationWithoutRole() throws ApiException {

        HttpHeaders header = createHeaders();
        Employee employee = createEmployee();
        HttpHeaders headers = createHeaders();
        ResponseEntity<Employee> resultCreatedEmployee = testRestTemplate.exchange("/employees", HttpMethod.POST, new HttpEntity<>(createEmployee(), headers), Employee.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testEmployeeCreationWithoutFirstName() throws ApiException {

        HttpEntity<Employee> entity = createEmployeeEntity();
        entity.getBody().setFirstName(null);
        ResponseEntity<Employee> resultCreatedEmployee = testRestTemplate.exchange("/employees", HttpMethod.POST, entity, Employee.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testEmployeeCreationWithoutLastName() throws ApiException {

        HttpEntity<Employee> entity = createEmployeeEntity();
        entity.getBody().setSurname(null);
        ResponseEntity<Employee> resultCreatedEmployee = testRestTemplate.exchange("/employees", HttpMethod.POST, entity, Employee.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testPostWithUser() throws ApiException {
        when(employeeApi.retrieveEmployeeIdAndPassword(Mockito.any())).thenReturn(createUser());
        ResponseEntity<Employee> resultCreatedEmployee = testRestTemplate.exchange("/employees", HttpMethod.POST, createEmployeeEntity(), Employee.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testDeleteWithUser() throws ApiException {
        when(employeeApi.retrieveEmployeeIdAndPassword(Mockito.any())).thenReturn(createUser());
        ResponseEntity<String> resultCreatedEmployee = testRestTemplate.exchange("/employees/RandomID_1", HttpMethod.DELETE, createEmployeeEntity(), String.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void getEmployeeById() throws ApiException {
        ResponseEntity<String> resultCreatedEmployee = testRestTemplate.exchange("/employees/RandomID_1", HttpMethod.GET, createEmployeeEntity(), String.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getEmployeeNotFound() throws ApiException {
        ResponseEntity<ErrorDetails> response = new ResponseEntity<>(new ErrorDetails(new Date(), "Not Found", ""), HttpStatus.NOT_FOUND);
        when(employeeApi.findEmployeeById(Mockito.any())).thenThrow(new ApiException(404, "Not Found"));
        ResponseEntity<String> resultCreatedEmployee = testRestTemplate.exchange("/employees/RandomID_1", HttpMethod.GET, createEmployeeEntity(), String.class);
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.NOT_FOUND);
    }


    private HttpHeaders createHeaders() {
        return new HttpHeaders() {{
            String auth = "Animesh Pradhan" + ":" + "admin";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    private com.animesh.generated.employee.database.model.Employee createUser(String role) {
        com.animesh.generated.employee.database.model.Employee authenticationObject = new com.animesh.generated.employee.database.model.Employee();
        authenticationObject.setName("Animesh Pradhan");
        authenticationObject.setRoleId("1");
        authenticationObject.setRoleName(role);
        authenticationObject.setPassword(java.util.Base64.getEncoder().encodeToString("admin".getBytes()));
        authenticationObject.setId("RandomID_1");
        return authenticationObject;
    }

    private com.animesh.generated.employee.database.model.Employee createAdmin() {
        return createUser("ADMIN");
    }

    private com.animesh.generated.employee.database.model.Employee createUser() {
        return createUser("USER");
    }

    private com.animesh.generated.employee.database.model.Employee createEmployeeExternalModel() {
        com.animesh.generated.employee.database.model.Employee employeeExternal = new com.animesh.generated.employee.database.model.Employee();
        employeeExternal.setName("Animesh Pradhan");
        employeeExternal.setRoleId("1");
        employeeExternal.setId("RandomID_1");
        return employeeExternal;
    }

    private HttpEntity<Employee> createEmployeeEntity() {
        HttpHeaders headers = createHeaders();
        headers.add("Role", "1");
        HttpEntity<Employee> entity = new HttpEntity<>(createEmployee(), headers);
        return entity;
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Animesh");
        employee.setSurname("Pradhan");
        return employee;
    }

    @BeforeEach
    public void setup() throws ApiException {
        when(employeeApi.retrieveEmployeeIdAndPassword(Mockito.any())).thenReturn(createAdmin());
        when(employeeApi.findEmployeeById(Mockito.any())).thenReturn(createAdmin());
        when(employeeApi.createNewEmployee(Mockito.any())).thenReturn(createEmployeeExternalModel());
        when(employeeApi.updateEmployee(Mockito.any(), Mockito.any())).thenReturn(createEmployeeExternalModel());
    }
}


