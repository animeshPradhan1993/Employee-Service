package com.animesh.employee.service.mapper;

import com.animesh.employee.service.resource.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeResourceToApiModelMapper {
    EmployeeResourceToApiModelMapper INSTANCE = Mappers.getMapper(EmployeeResourceToApiModelMapper.class);

    @Mapping(source = "roleId", target = "roleId")
    @Mapping(expression = "java(resource.getFirstName() + \" \" + resource.getSurname())", target = "name")
    @Mapping(source = "projectIds", target = "projectIds")
    @Mapping(source = "password", target = "password")
    com.animesh.generated.employee.database.model.Employee map(Employee resource);


}
