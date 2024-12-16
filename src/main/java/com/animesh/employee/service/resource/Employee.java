package com.animesh.employee.service.resource;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class Employee {

    private String id;
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "FirstName should be alpha numeric")
    private String firstName;
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Surname should be alpha numeric")
    private String surname;
    private String roleId;
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Password should be alpha numeric")
    private String password;
    private Set<String> projectIds;
}
