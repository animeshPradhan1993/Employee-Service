package com.animesh.employee.service.service;

import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.ProjectControllerApi;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {
    @Autowired
    private final ProjectControllerApi projectApi;


    public String assignprojectToEmployee(String employeeId, String projectId) throws ApiException {
        return projectApi.assignProjectToEmployee(employeeId, projectId);
    }

}
