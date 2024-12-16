package com.animesh.employee.service.service;


import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.EmployeeControllerApi;
import com.animesh.generated.employee.database.model.Employee;
import lombok.AllArgsConstructor;
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
        employeeApi.findEmployeeById(employeeId);
    }

    public Employee findEmployeeById(String employeeId) throws ApiException {
        return employeeApi.findEmployeeById(employeeId);

    }

    public Employee findEmployeeAuth(String employeeId) throws ApiException {
        return employeeApi.retrieveEmployeeIdAndPassword(employeeId);

    }


    public Employee updateEmployee(String employeeId, Employee employee) throws ApiException {
        Employee employee1 = findEmployeeById(employeeId);
        employee.setId(employee1.getId());
        employee.setRoleId(employee.getRoleId());
        employee.setPassword(employee1.getPassword());
        return employeeApi.updateEmployee(employeeId, employee);
    }

}
