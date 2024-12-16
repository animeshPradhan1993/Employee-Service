package com.animesh.employee.service.controller;


import com.animesh.employee.service.config.Message;
import com.animesh.employee.service.exception.handling.ErrorDetails;
import com.animesh.employee.service.service.RoleService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController()
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private final RoleService service;

    @Operation(summary = "Delete Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorDetails.class))})})
    @DeleteMapping("{role}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> deleteRole(@PathVariable String role) throws ApiException {
        service.deleteRole(role);
        return new ResponseEntity<>(new Message("role deleted successfully"), HttpStatus.OK);
    }

}
