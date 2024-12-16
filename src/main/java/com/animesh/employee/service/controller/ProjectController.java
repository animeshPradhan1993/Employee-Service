package com.animesh.employee.service.controller;

import com.animesh.employee.service.config.Message;
import com.animesh.employee.service.exception.handling.ErrorDetails;
import com.animesh.employee.service.service.EmployeeService;
import com.animesh.employee.service.service.ProjectService;
import com.animesh.generated.employee.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@AllArgsConstructor
public class ProjectController {
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final ProjectService projectService;

    @Operation(summary = "Assign project To Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found/ Project Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))})})
    @PutMapping("/{employeeId}/projects/{projectId}")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<Message> assignProjectToEmployee(@PathVariable String employeeId, @PathVariable String projectId) throws ApiException {

        return new ResponseEntity<>(new Message(projectService.assignprojectToEmployee(employeeId, projectId)), HttpStatus.OK);
    }


}

