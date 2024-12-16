package com.animesh.employee.service.mapper;


import com.animesh.employee.service.resource.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper
public interface EmployeeApiModelToResourceMapper {

    EmployeeApiModelToResourceMapper INSTANCE = Mappers.getMapper(EmployeeApiModelToResourceMapper.class);

    @Mapping(source = "employeeModel.roleId", target = "roleId")
    @Mapping(source = "employeeModel.name", target = "firstName", qualifiedByName = "mapFirstName")
    @Mapping(source = "employeeModel.name", target = "surname", qualifiedByName = "mapSurname")
    @Mapping(source = "employeeModel.projectIds", target = "projectIds")
    Employee map(com.animesh.generated.employee.database.model.Employee employeeModel);


    @Named("mapFirstName")
    default String mapFirstName(String name) {
        return name.split(" ")[0];
    }

    @Named("mapSurname")
    default String mapSurname(String name) {
        return name.split(" ")[1];
    }
}

