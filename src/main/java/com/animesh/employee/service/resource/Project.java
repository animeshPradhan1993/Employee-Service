package com.animesh.employee.service.resource;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class Project {

    private String id;
    @NotEmpty
    private String name;
}
