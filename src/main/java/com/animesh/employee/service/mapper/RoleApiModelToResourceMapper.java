package com.animesh.employee.service.mapper;


import com.animesh.employee.service.resource.Role;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface RoleApiModelToResourceMapper {
    RoleApiModelToResourceMapper INSTANCE = Mappers.getMapper(RoleApiModelToResourceMapper.class);

    @Mapping(source = "role.id", target = "id")
    @Mapping(source = "role.name", target = "name")
    Role map(com.animesh.generated.employee.database.model.Role role);
}
