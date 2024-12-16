package com.animesh.employee.service.controller;


import com.animesh.employee.service.config.Message;
import com.animesh.employee.service.exception.handling.ErrorDetails;
import com.animesh.employee.service.mapper.EmployeeApiModelToResourceMapper;
import com.animesh.employee.service.mapper.EmployeeResourceToApiModelMapper;
import com.animesh.employee.service.resource.Employee;
import com.animesh.employee.service.service.EmployeeService;
import com.animesh.generated.employee.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController()
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Operation(summary = "Create New Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))})})
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Employee> createNewEmployee(@RequestHeader("Role") String role, @Valid @RequestBody Employee employee) throws ApiException {
        employee.setRoleId(role);
        return new ResponseEntity<>(EmployeeApiModelToResourceMapper.INSTANCE.map(service.createEmployee(EmployeeResourceToApiModelMapper.INSTANCE.map(employee))), HttpStatus.CREATED);

    }

    @Operation(summary = "Delete Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))})})
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> deleteEmployee(@PathVariable String employeeId) throws ApiException {
        service.deleteEmployee(employeeId);
        return new ResponseEntity<>(new Message("Employee Deleted Successfully"), HttpStatus.OK);

    }

    @Operation(summary = "Retrieve Employee By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))})})
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public Employee findEmployeeById(@PathVariable String employeeId) throws ApiException {
        return EmployeeApiModelToResourceMapper.INSTANCE.map(service.findEmployeeById(employeeId));

    }

    @Operation(summary = "Update Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))})})
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public Employee updateEmployee(@RequestHeader("Role") String role, @PathVariable String employeeId, @Valid @RequestBody Employee employee) throws ApiException {
        employee.setRoleId(role);
        return EmployeeApiModelToResourceMapper.INSTANCE.map(service.updateEmployee(employeeId, EmployeeResourceToApiModelMapper.INSTANCE.map(employee)));

    }
}
