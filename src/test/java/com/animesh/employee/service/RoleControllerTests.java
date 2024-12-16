package com.animesh.employee.service;

import com.animesh.employee.service.controller.RoleController;
import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.EmployeeControllerApi;
import com.animesh.generated.employee.database.RoleControllerApi;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class RoleControllerTests {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RoleController controller;
    @MockitoBean
    private RoleControllerApi roleControllerApi;
    @MockitoBean
    private EmployeeControllerApi employeeApi;

    @Test
    public void roleControllerTest() throws ApiException {
        when(employeeApi.retrieveEmployeeIdAndPassword(Mockito.any())).thenReturn(createAdmin());
        when(roleControllerApi.deleteRole(Mockito.any())).thenReturn(String.valueOf(new ResponseEntity<String>("Role Deleted Successfully", HttpStatus.OK)));
        ResponseEntity<String> resultCreatedEmployee = testRestTemplate.exchange("/roles/RandomRole", HttpMethod.DELETE, createRoleEntity(), String.class);
        assertEquals(resultCreatedEmployee.getBody().trim(), " {\"message\":\"role deleted successfully\"}".trim());
        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testRoleDoesNotExist() throws ApiException {
        when(employeeApi.retrieveEmployeeIdAndPassword(Mockito.any())).thenReturn(createAdmin());
        when(roleControllerApi.deleteRole(Mockito.any())).thenThrow(new ApiException(404, "Role Not Found"));
        ResponseEntity<String> resultCreatedEmployee = testRestTemplate.exchange("/roles/RandomRole", HttpMethod.DELETE, createRoleEntity(), String.class);

        assertEquals(resultCreatedEmployee.getStatusCode(), HttpStatus.NOT_FOUND);
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

    private HttpEntity<String> createRoleEntity() {
        HttpHeaders headers = createHeaders();
        headers.add("Role", "1");
        HttpEntity<String> entity = new HttpEntity<>(new String("RANDOM_ROLE"), headers);
        return entity;
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


}
