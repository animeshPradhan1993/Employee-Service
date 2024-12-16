package com.animesh.employee.service.mapper;


import com.animesh.employee.service.resource.Role;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface RoleResourceToApiModelMapper {
    RoleResourceToApiModelMapper INSTANCE = Mappers.getMapper(RoleResourceToApiModelMapper.class);

    @Mapping(source = "resource.id", target = "id")
    @Mapping(source = "resource.name", target = "name")
    com.animesh.generated.employee.database.model.Role map(Role resource);
}
