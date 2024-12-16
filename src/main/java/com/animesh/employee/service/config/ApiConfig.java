package com.animesh.employee.service.config;

import com.animesh.generated.employee.ApiClient;
import com.animesh.generated.employee.database.EmployeeControllerApi;
import com.animesh.generated.employee.database.ProjectControllerApi;
import com.animesh.generated.employee.database.RoleControllerApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApiConfig {
    @Value("${employeeDatabase.basePath}")
    private String employeeDatabaseBasePath;

    @Bean
    public RoleControllerApi roleControllerApi() {
        return new RoleControllerApi();
    }

    @Bean
    public EmployeeControllerApi employeeControllerApi() {

        EmployeeControllerApi employeeApi = new EmployeeControllerApi();
        ApiClient client = new ApiClient();
        client.setBasePath(employeeDatabaseBasePath);
        employeeApi.setApiClient(client);
        return employeeApi;
    }

    @Bean
    public ProjectControllerApi projectControllerApi() {
        return new ProjectControllerApi();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
