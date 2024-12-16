package com.animesh.employee.service.service;

import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.RoleControllerApi;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    @Autowired
    private final RoleControllerApi roleControllerApi;

    public void deleteRole(String roleId) throws ApiException {
        roleControllerApi.deleteRole(roleId);
    }


}
