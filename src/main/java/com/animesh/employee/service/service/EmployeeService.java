package com.animesh.employee.service.service;


import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.EmployeeControllerApi;
import com.animesh.generated.employee.database.model.Employee;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {


    @Autowired
    private final EmployeeControllerApi employeeApi;

    public Employee createEmployee(Employee employee) throws ApiException {


        return employeeApi.createNewEmployee(employee);
    }

    public void deleteEmployee(String employeeId) throws ApiException {
        employeeApi.deleteEmployee(employeeId);
    }

    public Employee findEmployeeById(String employeeId) throws ApiException {
        return employeeApi.findEmployeeById(employeeId);

    }

    public Employee findEmployeeAuth(String employeeId) throws ApiException {
        return employeeApi.retrieveEmployeeIdAndPassword(employeeId);

    }


    public Employee updateEmployee(String employeeId, Employee employee) throws ApiException {
        Employee employee1 = findEmployeeById(employeeId);
        if (!employee1.getRoleId().isEmpty()) {
            employee1.setRoleId(employee.getRoleId());
        }
        if (!StringUtils.isEmpty(employee.getName())) {
            employee1.setName(employee.getName());
        }
        return employeeApi.updateEmployee(employeeId, employee);
    }

}
