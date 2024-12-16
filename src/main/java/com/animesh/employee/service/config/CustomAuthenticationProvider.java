package com.animesh.employee.service.config;

import com.animesh.employee.service.exception.handling.ServiceUnavailableException;
import com.animesh.employee.service.service.EmployeeService;
import com.animesh.generated.employee.ApiException;
import com.animesh.generated.employee.database.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    protected Employee retrieveUser(String username) throws ApiException {
        return this.employeeService.findEmployeeAuth(username);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        if (authentication.getCredentials() == null) {
            //this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials");
        }
        Employee employee;
        String presentedPassword = authentication.getCredentials().toString();


        try {
            employee = retrieveUser(authentication.getName());
            String retrievedPassword = employee.getPassword();
            employee.setPassword(passwordEncoder.encode(new String(Base64.getDecoder().decode(retrievedPassword))));
        } catch (ApiException e) {
            throw new ServiceUnavailableException(e.getMessage());
        }
        if (!this.passwordEncoder.matches(presentedPassword, employee.getPassword())) {
            //  this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException("AbstractUserDetailsAuthenticationProvider.badCredentials");
        }
        return createSuccessAuthentication(authentication.getPrincipal(), authentication, employee);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, Employee employee) {
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(principal, authentication.getCredentials(), Collections.singletonList(new SimpleGrantedAuthority(employee.getRoleName())));
        result.setDetails(authentication.getDetails());
        return result;
    }
}
