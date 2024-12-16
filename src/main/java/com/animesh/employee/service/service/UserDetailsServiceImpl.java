/*
package com.animesh.employee.service.service;

import lombok.AllArgsConstructor;
import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

*/
/*@Service
@AllArgsConstructor*//*

public class UserDetailsServiceImpl {

    */
/*@Autowired
    private final EmployeeService employeeService;*//*


 */
/* @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee;
        try {
             employee = employeeService.findEmployeeById(username);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }*//*



        return User.builder()
                .username(employee.getId())
                .password(employee.getPassword())
                .roles(employee.getRoleName())
                .build();
    }

}
*/
